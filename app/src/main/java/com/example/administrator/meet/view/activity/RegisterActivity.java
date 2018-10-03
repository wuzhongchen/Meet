package com.example.administrator.meet.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.meet.R;
import com.example.administrator.meet.utils.OkHttpUtil2;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

import static android.widget.RelativeLayout.TRUE;
import static com.example.administrator.meet.R.id.get_code2;

public class RegisterActivity extends AppCompatActivity{

    private EditText nickname;

    private EditText password;
    private EditText Input_phoneNumber;
    private EditText Input_Code;
    private Button get_Code;
    private Button get_Code2;
    private Button submit_Code;
    public String sessionid;
    public List<String> cookies;

    public int T = 30; //倒计时时长
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nickname = (EditText) findViewById(R.id.nickname_editText);
        password = (EditText)findViewById(R.id.password_editText);
        Input_phoneNumber = (EditText) findViewById(R.id.edi);
        Input_Code = (EditText) findViewById(R.id.edi2);
        get_Code = (Button)findViewById(R.id.get_code);
        get_Code2 = (Button)findViewById(R.id.get_code2);
        get_Code2.setVisibility(View.GONE);
        submit_Code =(Button) findViewById(R.id.submit_code);
        get_Code.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                get_phoneNumber_Code();
                get_Code.setVisibility(View.GONE);
                get_Code2.setVisibility(View.VISIBLE);
                new Thread(new MyCountDownTimer()).start();//开始执行
            }
        });
        submit_Code.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        String url = "https://sf.bitzo.cn/api/register/phone";
        Map<String,String > map3 = new HashMap<>();
        map3.put("nickname",nickname.getText().toString());
        map3.put("password",password.getText().toString());
        map3.put("phoneNumber",Input_phoneNumber.getText().toString());
        map3.put("code",Input_Code.getText().toString());
        String session = cookies.get(0);
        Log.e("info_cookies", "onResponse-size: " + cookies);

        sessionid = session.substring(0, session.indexOf(";"));
        Log.e("info_s", "session is  :" + sessionid);

        OkHttpUtil2.post(sessionid,url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("RegisterActivity", "onFailure: ",e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Intent intent = new Intent(RegisterActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        },map3);
    }

    private  void get_phoneNumber_Code() {
        String url = "https://sf.bitzo.cn/api/register/phoneCode";
        Map<String,String> map2 = new HashMap<>();

        map2.put("phoneNumber",Input_phoneNumber.getText().toString());

        OkHttpUtil2.post("",url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("RegisterActivity", "onFailure: ",e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Headers headers = response.headers();
                Log.e("RegisterActivity", "header " + headers);
                cookies = headers.values("Set-Cookie");
            }
        },map2);
    }

    private class MyCountDownTimer implements Runnable {
        @Override
        public void run() {
            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        get_Code2.setClickable(false);
                        get_Code2.setText("After "+T+" seconds");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    get_Code2.setClickable(true);
                    get_Code2.setText("倒计时开始");
                }
            });
            T = 10; //最后再恢复倒计时时长
        }
    }

}
