package com.example.administrator.meet.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.meet.R;
import com.example.administrator.meet.view.activity.HomePageActivity;


public class my_achievements extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor defaultSensor;
    private CollisionView collisionView;
    int set_of_Images[] = {
            R.mipmap.five_kilometers,
            R.mipmap.trophy,
            R.mipmap.ten_kilometers,
            R.mipmap.android,
            R.mipmap.qq,
            R.mipmap.weibo,
            R.mipmap.facebook,
            R.mipmap.apple,
    };

    private TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievements);
//        textView=(TextView)findViewById(R.id.sensor_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x =event.values[0];
            float y =event.values[1]*2.3f;
            collisionView.onSensorChanged(-x,y);
//            StringBuilder sb = new StringBuilder();
//            sb.append("X轴重力加速度：");
//            sb.append(event.values[0]);
//            sb.append("\nY轴重力加速度：");
//            sb.append(event.values[1]);
//            sb.append("\nZ轴重力加速度：");
//            sb.append(event.values[2]);
//            textView.setText(sb.toString());
        }
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onContentChanged(){
        Log.e("MainActivity","onContentChanged begin");
        collisionView = (CollisionView)findViewById(R.id.jBoxView);
        collisionView.setWillNotDraw(false);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        initView();
    }

    private void initView() {
        Log.e("MainActivity","Init view");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        for(int i = 0; i < set_of_Images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(set_of_Images[i]);
            imageView.setTag(R.id.dn_view_circle_tag,true);
            collisionView.addView(imageView,layoutParams);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,defaultSensor,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

