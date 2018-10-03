package com.example.administrator.meet.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.administrator.meet.R;
import com.example.administrator.meet.utils.CustomVideoView;

import java.io.File;

public class WelcomeActivity extends AppCompatActivity {

    private VideoView WelcomeVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        //播放
//        WelcomeVideo.start();
        handler.sendEmptyMessageDelayed(0,10);
    }

    private void initView() {
//        WelcomeVideo = (VideoView) findViewById(R.id.WelcomeVideo);
//        //设置播放加载路径
//        File file = new File(Environment.getExternalStorageDirectory(),"WelcomeVideo.mp4");
//        WelcomeVideo.setVideoPath(file.getPath());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
