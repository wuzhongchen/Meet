package com.example.administrator.meet.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.meet.R;
import com.example.administrator.meet.utils.GvoiceManager;

public class ChatActivity extends AppCompatActivity {

    // 布局相关
    private Button teamBtn;
    private Button nationalBtn;
    private Button offlineBtn;
    private Button sttBtn;

    private GvoiceManager gvoiceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();     // 初始化视图
        initGvoice();   // 初始化 Gvoice
    }

    /**
     * 初始化 Gvoice 引擎
     */
    private void initGvoice() {
        gvoiceManager = GvoiceManager.getInstance();
        gvoiceManager.initGvoice(this.getApplicationContext(), this);
    }

    private void initView() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(getString(R.string.app_name));
//        setSupportActionBar(toolbar);

        teamBtn = (Button) findViewById(R.id.btn_team_room);
        teamBtn.setVisibility(View.GONE);
        nationalBtn = (Button) findViewById(R.id.btn_national_room);
        nationalBtn.setVisibility(View.GONE);
        offlineBtn = (Button) findViewById(R.id.btn_offline);
        offlineBtn.setVisibility(View.GONE);
        sttBtn = (Button) findViewById(R.id.btn_stt);
        sttBtn.setVisibility(View.GONE);

        teamBtn.setOnClickListener(mClickListener);
        nationalBtn.setOnClickListener(mClickListener);
        offlineBtn.setOnClickListener(mClickListener);
        sttBtn.setOnClickListener(mClickListener);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        startTargetActivity(TeamRoomActivity.class);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_team_room:

                    break;
                case R.id.btn_national_room:
                    //startTargetActivity(NationalRoomActivity.class);
                    break;
                case R.id.btn_offline:
                    //startTargetActivity(OfflineActivity.class);
                    break;
                case R.id.btn_stt:
                    //startTargetActivity(SpeechToTextActivity.class);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 进入指定 Activity
     *
     * @param activityClass:目标 Activity
     */
    private void startTargetActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
