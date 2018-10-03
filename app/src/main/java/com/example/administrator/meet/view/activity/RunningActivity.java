package com.example.administrator.meet.view.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceLocation;
import com.amap.api.trace.TraceOverlay;
import com.example.administrator.meet.R;
import com.example.administrator.meet.bean.PathRecord;
import com.example.administrator.meet.utils.TraceUtil;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class RunningActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    private final static int CALLTRACE = 0;
    private MapView mMapView;
    private AMap mAMap;

    private OnLocationChangedListener mListener;

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private PolylineOptions mPolyoptions, tracePolytion;
    //定义地图线段覆盖物。一个线段是多个连贯点的集合线段
    private Polyline mpolyline;

    private PathRecord record;
    private long mStartTime;
    private long mEndTime;
    private ToggleButton btn;
    private DbAdapter DbHepler;
    private List<TraceLocation> mTracelocationlist = new ArrayList<TraceLocation>();
//    private List<TraceOverlay> mOverlayList = new ArrayList<TraceOverlay>();
    private List<AMapLocation> recordList = new ArrayList<AMapLocation>();
    private int tracesize = 30;
    private int mDistance = 0;
    private TraceOverlay mTraceoverlay;
    private TextView mResultShow;
    private Marker mlocMarker;
    public class CountDownTime implements Runnable {
        @Override
        public void run() {
            //倒计时开始，循环
            while (T<4000) {

                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T=T+4.5;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {



                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        mResultShow.setText(
                                decimalFormat.format(T / 1000d) );
                    }
                });
            }


            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            T = 10; //最后再恢复倒计时时长
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        mResultShow =(TextView) findViewById(R.id.km_text);
        Button meet =(Button) findViewById(R.id.meet);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            setUpMap();
        }


        meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(RunningActivity.this,ChatActivity.class);
                startActivity(intent1);
            }
        });
       // mTraceoverlay = new TraceOverlay(mAMap);
        btn = (ToggleButton) findViewById(R.id.locationbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn.isChecked()) {
                    mAMap.clear(true);
                    if (record != null) {
                        record = null;
                    }
                    new Thread(new CountDownTime()).start();//开始执行
                    record = new PathRecord();
                    mStartTime = System.currentTimeMillis();
                    record.setDate(getcueDate(mStartTime));
//                    mResultShow.setText("0.0");

                } else {
                    mEndTime = System.currentTimeMillis();
                   // mOverlayList.add(mTraceoverlay);
                    DecimalFormat decimalFormat = new DecimalFormat("0.0");

                    LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
                    //mTraceClient.queryProcessedTrace(2, TraceUtil.parseTraceLocationList(record.getPathline()) , LBSTraceClient.TYPE_AMAP, RunningActivity.this);
                    saveRecord(record.getPathline(), record.getDate());
                }
            }
        });



        initpolyline();
    }

    public double T = 0.0f; //倒计时时长
    private Handler mHandler = new Handler();

    /**
     * 初始化AMap对象
     */


    protected void saveRecord(List<AMapLocation> list, String time) {
        if (list != null && list.size() > 0) {
            DbHepler = new DbAdapter(this);
            DbHepler.open();
            String duration = getDuration();
            float distance = getDistance(list);
            String average = getAverage(distance);
            String pathlineSring = getPathLineString(list);
            AMapLocation firstLocaiton = list.get(0);
            AMapLocation lastLocaiton = list.get(list.size() - 1);
            String stratpoint = amapLocationToString(firstLocaiton);
            String endpoint = amapLocationToString(lastLocaiton);
            DbHepler.createrecord(String.valueOf(distance), duration, average,
                    pathlineSring, stratpoint, endpoint, time);
            DbHepler.close();
        } else {
            Toast.makeText(RunningActivity.this, "没有记录到路径", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private String getDuration() {
        return String.valueOf((mEndTime - mStartTime) / 1000f);
    }

    private String getAverage(float distance) {
        return String.valueOf(distance / (float) (mEndTime - mStartTime));
    }


    private float getDistance(List<AMapLocation> list) {
        float distance = 0;
        if (list == null || list.size() == 0) {
            return distance;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            AMapLocation firstpoint = list.get(i);
            AMapLocation secondpoint = list.get(i + 1);
            LatLng firstLatLng = new LatLng(firstpoint.getLatitude(),
                    firstpoint.getLongitude());
            LatLng secondLatLng = new LatLng(secondpoint.getLatitude(),
                    secondpoint.getLongitude());
            double betweenDis = AMapUtils.calculateLineDistance(firstLatLng,
                    secondLatLng);
            distance = (float) (distance + betweenDis);
        }
        return distance;
    }

    private String getPathLineString(List<AMapLocation> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer pathline = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            AMapLocation location = list.get(i);
            String locString = amapLocationToString(location);
            pathline.append(locString).append(";");
        }
        String pathLineString = pathline.toString();
        pathLineString = pathLineString.substring(0,
                pathLineString.length() - 1);
        return pathLineString;
    }

    private String amapLocationToString(AMapLocation location) {
        StringBuffer locString = new StringBuffer();
        locString.append(location.getLatitude()).append(",");
        locString.append(location.getLongitude()).append(",");
        locString.append(location.getProvider()).append(",");
        locString.append(location.getTime()).append(",");
        locString.append(location.getSpeed()).append(",");
        locString.append(location.getBearing());
        return locString.toString();
    }

    private void initpolyline() {
        mPolyoptions = new PolylineOptions();
        mPolyoptions.width(10f);
        mPolyoptions.color(Color.GRAY);
//        tracePolytion = new PolylineOptions();
//        tracePolytion.width(0);
//        tracePolytion.setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.grasp_trace_line));
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        //隐藏Logo
        UiSettings uiSettings =  mAMap.getUiSettings();
        uiSettings.setLogoBottomMargin(-50);//隐藏logo
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色

        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        mAMap.setMapLanguage(AMap.ENGLISH);
        mAMap.setMyLocationStyle(myLocationStyle);
        CameraUpdate newfloat = CameraUpdateFactory.zoomTo(14.5f);
        mAMap.animateCamera(newfloat);

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        startlocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();

        }
        mLocationClient = null;
    }

    /**
     * 定位结果回调
     * @param amapLocation 位置信息类
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                LatLng mylocation = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mylocation));
                if (btn.isChecked()) {
                    record.addpoint(amapLocation);
                    mPolyoptions.add(mylocation);
                    mTracelocationlist.add(TraceUtil.parseTraceLocation(amapLocation));
                    redrawline();
                    if (mTracelocationlist.size() > tracesize - 1) {
                        trace();
                    }
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": "
                        + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 开始定位。
     */
    private void startlocation() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            mLocationOption.setInterval(2000);

            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();

        }
    }

    /**
     * 实时轨迹画线
     */
    private void redrawline() {
        if (mPolyoptions.getPoints().size() > 1) {
            if (mpolyline != null) {
                mpolyline.setPoints(mPolyoptions.getPoints());
            } else {
                mpolyline = mAMap.addPolyline(mPolyoptions);
            }
        }
//		if (mpolyline != null) {
//			mpolyline.remove();
//		}
//		mPolyoptions.visible(true);
//		mpolyline = mAMap.addPolyline(mPolyoptions);
//			PolylineOptions newpoly = new PolylineOptions();
//			mpolyline = mAMap.addPolyline(newpoly.addAll(mPolyoptions.getPoints()));
//		}
    }

    @SuppressLint("SimpleDateFormat")
    private String getcueDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd  HH:mm:ss ");
        Date curDate = new Date(time);
        String date = formatter.format(curDate);
        return date;
    }


    private void trace() {
        List<TraceLocation> locationList = new ArrayList<>(mTracelocationlist);
        LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
       // mTraceClient.queryProcessedTrace(1, locationList, LBSTraceClient.TYPE_AMAP, this);
        TraceLocation lastlocation = mTracelocationlist.get(mTracelocationlist.size()-1);
        mTracelocationlist.clear();
        mTracelocationlist.add(lastlocation);
    }

    /**
     * 轨迹纠偏失败回调。
     * @param i
     * @param s
     */
//    @Override
//    public void onRequestFailed(int i, String s) {
//        mOverlayList.add(mTraceoverlay);
//        mTraceoverlay = new TraceOverlay(mAMap);
//    }
//
//    @Override
//    public void onTraceProcessing(int i, int i1, List<LatLng> list) {
//
//    }
//
//    /**
//     * 轨迹纠偏成功回调。
//     * @param lineID 纠偏的线路ID
//     * @param linepoints 纠偏结果
//     * @param distance 总距离
//     * @param waitingtime 等待时间

//    @Override
//    public void onFinished(int lineID, List<LatLng> linepoints, int distance, int waitingtime) {
//        if (lineID == 1) {
//            if (linepoints != null && linepoints.size()>0) {
//                mTraceoverlay.add(linepoints);
//                mDistance += distance;
//                mTraceoverlay.setDistance(mTraceoverlay.getDistance()+distance);
//                if (mlocMarker == null) {
//                    mlocMarker = mAMap.addMarker(new MarkerOptions().position(linepoints.get(linepoints.size() - 1))
//                            .icon(BitmapDescriptorFactory
//                                    .fromResource(R.drawable.point))
//                            .title("距离：" + mDistance+"米"));
//                    //mlocMarker.showInfoWindow();
//
////                    final Timer timer = new Timer();
////                    TimerTask task;
////                    final Handler handler = new Handler() {
////                        @Override
////                        public void handleMessage(Message msg) {
////                            // TODO Auto-generated method stub
////                            mResultShow.setText(mDistance);
////                            super.handleMessage(msg);
////                        }
////                    };
////
////                    task = new TimerTask() {
////                        @Override
////                        public void run() {
////                            // TODO Auto-generated method stub
////                            Message message = new Message();
////                            message.what = 1;
////                            handler.sendMessage(message);
////                        }
////                    };
//
//
////                    timer.schedule(task, 1000, 1000);
//
//
//                } else {
////                    mlocMarker.setTitle("距离：" + mDistance+"米");
//                   // Toast.makeText(RunningActivity.this, "距离"+mDistance, Toast.LENGTH_SHORT).show();
//                    mlocMarker.setPosition(linepoints.get(linepoints.size() - 1));
//                    //mlocMarker.showInfoWindow();
//
////                    final Timer timer = new Timer();
////                    TimerTask task;
////                    final Handler handler = new Handler() {
////                        @Override
////                        public void handleMessage(Message msg) {
////                            // TODO Auto-generated method stub
////                            mResultShow.setText(mDistance);
////                            super.handleMessage(msg);
////                        }
////                    };
////
////                    task = new TimerTask() {
////                        @Override
////                        public void run() {
////                            // TODO Auto-generated method stub
////                            Message message = new Message();
////                            message.what = 1;
////                            handler.sendMessage(message);
////                        }
////                    };
//
//
////                    timer.schedule(task, 1000, 1000);
//
//                }
//            }
//        } else if (lineID == 2) {
////            if (linepoints != null && linepoints.size()>0) {
////                mAMap.addPolyline(new PolylineOptions()
////                        .color(Color.RED)
////                        .width(40).addAll(linepoints));
////            }
//        }
//
//    }

    /**
     * 最后获取总距离
     * @return
     */
//    private int getTotalDistance() {
//        int distance = 0;
//        for (TraceOverlay to : mOverlayList) {
//            distance = distance + to.getDistance();
//        }
//        return distance;
//    }

    private class DbAdapter {
        public static final String KEY_ROWID = "id";
        public static final String KEY_DISTANCE = "distance";
        public static final String KEY_DURATION = "duration";
        public static final String KEY_SPEED = "averagespeed";
        public static final String KEY_LINE = "pathline";
        public static final String KEY_STRAT = "stratpoint";
        public static final String KEY_END = "endpoint";
        public static final String KEY_DATE = "date";
        private final String DATABASE_PATH = android.os.Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/recordPath";
         final String DATABASE_NAME = DATABASE_PATH + "/" + "record.db";
        private static final int DATABASE_VERSION = 1;
        private static final String RECORD_TABLE = "record";
        private static final String RECORD_CREATE = "create table if not exists record("
                + KEY_ROWID
                + " integer primary key autoincrement,"
                + "stratpoint STRING,"
                + "endpoint STRING,"
                + "pathline STRING,"
                + "distance STRING,"
                + "duration STRING,"
                + "averagespeed STRING,"
                + "date STRING" + ");";

        public  class DatabaseHelper extends SQLiteOpenHelper {
            public DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(RECORD_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        }

        private Context mCtx = null;
        private DatabaseHelper dbHelper;
        private SQLiteDatabase db;

        // constructor
        public DbAdapter(Context ctx) {
            this.mCtx = ctx;
            dbHelper = new DatabaseHelper(mCtx);
        }

        public DbAdapter open() throws SQLException {

            db = dbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            dbHelper.close();
        }

        public Cursor getall() {
            return db.rawQuery("SELECT * FROM record", null);
        }

        // remove an entry
        public boolean delete(long rowId) {

            return db.delete(RECORD_TABLE, "id=" + rowId, null) > 0;
        }

        /**
         * 数据库存入一条轨迹
         *
         * @param distance
         * @param duration
         * @param averagespeed
         * @param pathline
         * @param stratpoint
         * @param endpoint
         * @param date
         * @return
         */
        public long createrecord(String distance, String duration,
                                 String averagespeed, String pathline, String stratpoint,
                                 String endpoint, String date) {
            ContentValues args = new ContentValues();
            args.put("distance", distance);
            args.put("duration", duration);
            args.put("averagespeed", averagespeed);
            args.put("pathline", pathline);
            args.put("stratpoint", stratpoint);
            args.put("endpoint", endpoint);
            args.put("date", date);
            return db.insert(RECORD_TABLE, null, args);
        }

        /**
         * 查询所有轨迹记录
         *
         * @return
         */
        public List<PathRecord> queryRecordAll() {
            List<PathRecord> allRecord = new ArrayList<PathRecord>();
            Cursor allRecordCursor = db.query(RECORD_TABLE, getColumns(), null,
                    null, null, null, null);
            while (allRecordCursor.moveToNext()) {
                PathRecord record = new PathRecord();
                record.setId(allRecordCursor.getInt(allRecordCursor
                        .getColumnIndex(DbAdapter.KEY_ROWID)));
                record.setDistance(allRecordCursor.getString(allRecordCursor
                        .getColumnIndex(DbAdapter.KEY_DISTANCE)));
                record.setDuration(allRecordCursor.getString(allRecordCursor
                        .getColumnIndex(DbAdapter.KEY_DURATION)));
                record.setDate(allRecordCursor.getString(allRecordCursor
                        .getColumnIndex(DbAdapter.KEY_DATE)));
                String lines = allRecordCursor.getString(allRecordCursor
                        .getColumnIndex(DbAdapter.KEY_LINE));
                record.setPathline(TraceUtil.parseLocations(lines));
                record.setStartpoint(TraceUtil.parseLocation(allRecordCursor
                        .getString(allRecordCursor
                                .getColumnIndex(DbAdapter.KEY_STRAT))));
                record.setEndpoint(TraceUtil.parseLocation(allRecordCursor
                        .getString(allRecordCursor
                                .getColumnIndex(DbAdapter.KEY_END))));
                allRecord.add(record);
            }
            Collections.reverse(allRecord);
            return allRecord;
        }

        /**
         * 按照id查询
         *
         * @param mRecordItemId
         * @return
         */
        public PathRecord queryRecordById(int mRecordItemId) {
            String where = KEY_ROWID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(mRecordItemId) };
            Cursor cursor = db.query(RECORD_TABLE, getColumns(), where,
                    selectionArgs, null, null, null);
            PathRecord record = new PathRecord();
            if (cursor.moveToNext()) {
                record.setId(cursor.getInt(cursor
                        .getColumnIndex(DbAdapter.KEY_ROWID)));
                record.setDistance(cursor.getString(cursor
                        .getColumnIndex(DbAdapter.KEY_DISTANCE)));
                record.setDuration(cursor.getString(cursor
                        .getColumnIndex(DbAdapter.KEY_DURATION)));
                record.setDate(cursor.getString(cursor
                        .getColumnIndex(DbAdapter.KEY_DATE)));
                String lines = cursor.getString(cursor
                        .getColumnIndex(DbAdapter.KEY_LINE));
                record.setPathline(TraceUtil.parseLocations(lines));
                record.setStartpoint(TraceUtil.parseLocation(cursor.getString(cursor
                        .getColumnIndex(DbAdapter.KEY_STRAT))));
                record.setEndpoint(TraceUtil.parseLocation(cursor.getString(cursor
                        .getColumnIndex(DbAdapter.KEY_END))));
            }
            return record;
        }

        private String[] getColumns() {
            return new String[] { KEY_ROWID, KEY_DISTANCE, KEY_DURATION, KEY_SPEED,
                    KEY_LINE, KEY_STRAT, KEY_END, KEY_DATE };
        }


    }


}
