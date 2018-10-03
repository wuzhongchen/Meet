package com.example.administrator.meet.view.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.meet.R;
import com.example.administrator.meet.utils.GvoiceManager;
import com.example.administrator.meet.utils.GvoiceNotify;
import com.tencent.gcloud.voice.GCloudVoiceEngine;
import com.tencent.gcloud.voice.GCloudVoiceErrno;

import java.util.Timer;
import java.util.TimerTask;

public class TeamRoomActivity extends AppCompatActivity {


        // 布局相关
        private Button joinRoomBtn;
        private Button openMicBtn;
        private Button closeMicBtn;
        private Button openSpeakerBtn;
        private Button closeSpeakerBtn;
        private Button quitRoomBtn;
        private TextView apiTv;
        private TextView callbackTv;

        // Gvoice 相关
        private GvoiceManager mGvoiceManager;
        private GCloudVoiceEngine mGvoiceEngine;
        private GvoiceNotify mGvoiceNotify;
        private String currentSpeed = "gvoice";    // 进房的房间名
        private int msTimeOut = 10000;              // 超时时长
        private static final int MSG_TASK = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_team_room);

            initView();     // 初始化视图
            initGvoice();   // 初始化 Gvoice
            poll();         // 定时任务，驱动 GvoiceEngine 定时调用 Poll() 函数来检查回调
        }

        @Override
        protected void onResume() {
            super.onResume();
            mGvoiceEngine.Resume(); // TODO 介绍内部主要逻辑
        }

        @Override
        protected void onPause() {
            super.onPause();
            mGvoiceEngine.Pause();  // TODO 介绍内部主要逻辑
        }

        /**
         * 定时任务，驱动 GvoiceEngine 定时调用 Poll() 函数来检查回调
         */
        private void poll() {
            TimerTask pollTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = handler.obtainMessage();
                    msg.what = MSG_TASK;
                    handler.sendMessage(msg);
                }
            };

            Timer timer = new Timer(true);
            timer.schedule(pollTask, 500, 500);
        }

        /**
         * 初始化 Gvoice 引擎
         * 完成引擎的初始化及回调设置
         */
        private void initGvoice() {
            mGvoiceManager = GvoiceManager.getInstance();
            if (!mGvoiceManager.isEngineInit()) {
                mGvoiceManager.initGvoice(this.getApplicationContext(), this);
            }
            mGvoiceEngine = mGvoiceManager.getVoiceEngine();
            mGvoiceNotify = new GvoiceNotify(callbackTv);
            mGvoiceEngine.SetNotify(mGvoiceNotify);
            mGvoiceEngine.SetMode(GCloudVoiceEngine.Mode.RealTime);     // 实时语音模式
        }

        private void initView() {

//            getSupportActionBar().setTitle(R.string.title_activity_team_room);  // 设置页面标题

//            joinRoomBtn = (Button) findViewById(R.id.btn_join_room);
//            openMicBtn = (Button) findViewById(R.id.btn_open_mic);
//            closeMicBtn = (Button) findViewById(R.id.btn_close_mic);
//            openSpeakerBtn = (Button) findViewById(R.id.btn_open_speaker);
//            closeSpeakerBtn = (Button) findViewById(R.id.btn_close_speaker);
//            quitRoomBtn = (Button) findViewById(R.id.btn_quit_room);
//            apiTv = (TextView) findViewById(R.id.tv_api);
//            callbackTv = (TextView) findViewById(R.id.tv_callback);
        Button jion= (Button)findViewById(R.id.join) ;
            Button openmic1 = (Button)findViewById(R.id.openmic) ;
            Button open_speaker = (Button)findViewById(R.id.speaker);
//            joinRoomBtn.setOnClickListener(mClickListener);
//            openMicBtn.setOnClickListener(mClickListener);
//            closeMicBtn.setOnClickListener(mClickListener);
//            openSpeakerBtn.setOnClickListener(mClickListener);
//            closeSpeakerBtn.setOnClickListener(mClickListener);
//            quitRoomBtn.setOnClickListener(mClickListener);
            jion.setOnClickListener(mClickListener);
            openmic1.setOnClickListener(mClickListener);
            open_speaker.setOnClickListener(mClickListener);
        }

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_TASK:
                        mGvoiceEngine.Poll();
                        break;
                    default:
                        break;
                }
            }
        };

        private View.OnClickListener mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.join:

                        joinTeamRoom();

                        // 进房，进入小队房间
                        break;
                    case R.id.openmic:
                        openMic();  // 开麦
                        break;
                    case R.id.speaker:
                        openSpeaker(); // 关麦
                        break;
//                    case R.id.btn_open_speaker:
//                        openSpeaker();  // 开扬声器
//                        break;
//                    case R.id.btn_close_speaker:
//                        closeSpeaker(); // 关扬声器
//                        break;
//                    case R.id.btn_quit_room:
//                        quitRoom();     // 退房
//                        break;
                    default:
                }
            }
        };

        /**
         * 加入小队房间
         * 注意！调用加入房间的接口前需先将引擎模式设置为实时语音模式
         */
        private void joinTeamRoom() {
            //clearTv();
            int ret = mGvoiceEngine.JoinTeamRoom(currentSpeed, msTimeOut);  // 进房
            if (ret == GCloudVoiceErrno.GCLOUD_VOICE_SUCC) {
               // apiTv.setText("Join team room: " + currentSpeed + " Success.\nThe result is: " + ret + ".");
            } else {
               // apiTv.setText("Join team room Failure.\n The error code is: " + ret + ".");
            }
        }

        /**
         * 开麦，必须进房成功才能开麦
         */
        private void openMic() {
           // clearTv();
            int ret = mGvoiceEngine.OpenMic();
            if (ret == GCloudVoiceErrno.GCLOUD_VOICE_SUCC) {
            //    apiTv.setText("Open mic Success.\nThe result is: " + ret + ".");
            } else {
             //   apiTv.setText("Open mic Failure.\nThe error code is: " + ret + ".");
            }
        }

        /**
         * 关麦
         */
        private void closeMic() {
            //clearTv();
            int ret = mGvoiceEngine.CloseMic();
            if (ret == GCloudVoiceErrno.GCLOUD_VOICE_SUCC) {
                apiTv.setText("Close mic Success.\nThe result is: " + ret + ".");
            } else {
                apiTv.setText("Close mic Failure.\nThe error code is: " + ret + ".");
            }
        }

        /**
         * 开扬声器
         */
        private void openSpeaker() {
            //clearTv();
            int ret = mGvoiceEngine.OpenSpeaker();
//            if (ret == GCloudVoiceErrno.GCLOUD_VOICE_SUCC) {
//                apiTv.setText("Open speaker Success.\nThe result is: " + ret + ".");
//            } else {
//                apiTv.setText("Open speaker Failure.\nThe error code is: " + ret + ".");
//            }
        }

        /**
         * 关扬声器
         */
        private void closeSpeaker() {
            clearTv();
            int ret = mGvoiceEngine.CloseSpeaker();
            if (ret == GCloudVoiceErrno.GCLOUD_VOICE_SUCC) {
                apiTv.setText("Close speaker Success.\nThe result is " + ret + ".");
            } else {
                apiTv.setText("Close speaker Failure.\nThe error code is: " + ret + ".");
            }
        }

        /**
         * 退房，只有退房成功才能重新进房
         */
        private void quitRoom() {
            clearTv();
            int ret = mGvoiceEngine.QuitRoom(currentSpeed, msTimeOut);
            if (ret == GCloudVoiceErrno.GCLOUD_VOICE_SUCC) {
                apiTv.setText("Quit room " + currentSpeed + " Success.\nThe result is: " + ret + ".");
            } else {
                apiTv.setText("Quit room Failure.\nThe error code is: " + ret + ".");
            }
        }

        // 清空日志输出框
        private void clearTv(){
            apiTv.setText("");
            callbackTv.setText("");
        }
    }

