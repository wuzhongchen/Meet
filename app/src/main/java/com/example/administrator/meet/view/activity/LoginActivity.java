package com.example.administrator.meet.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.administrator.meet.R;
import com.example.administrator.meet.presenter.LoginPresenter;
import com.example.administrator.meet.view.LoginView;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginView,View.OnClickListener{

    private EditText mUserName;
    private EditText mPassword;
    private Button registerBt;
    private Button loginBt;
    private ImageView down_list;//密码是否明文显示
    private Boolean showPassword = false;
    private LoginPresenter loginPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mUserName = (EditText) findViewById(R.id.login_id);
        mPassword = (EditText) findViewById(R.id.login_password);
        loginBt = (Button) findViewById(R.id.login_bt);
        registerBt = (Button) findViewById(R.id.register_bt);
        down_list = (ImageView) findViewById(R.id.down_arrayList);
        progressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        progressBar.setVisibility(View.GONE);
        loginPresenter = new LoginPresenter(LoginActivity.this);
        initView();
    }

    private void initView() {
        loginBt.setOnClickListener(this);
        registerBt.setOnClickListener(this);
        down_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_bt:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_bt:
//                if(TextUtils.isEmpty(mUserName.getText()) || TextUtils.isEmpty(mPassword.getText())) {
//                    Toast.makeText(getApplicationContext(), "账号或密码不能为空", Toast.LENGTH_SHORT).show();
//                }
                Map<String,String> map = new HashMap<>();
                map.put("account",mUserName.getText().toString());
                map.put("password",mPassword.getText().toString());
                //TODO
//                new LoginPresenter(this).fetch(map);
                turnToHomepage();
                break;
            case R.id.down_arrayList:
                if (showPassword) {// 显示密码
                    down_list.setImageDrawable(getResources().getDrawable(R.drawable.show));
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mPassword.setSelection(mPassword.getText().toString().length());
                    showPassword = false;
                } else {// 隐藏密码
                    down_list.setImageDrawable(getResources().getDrawable(R.drawable.invisible_revert));
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPassword.setSelection(mPassword.getText().toString().length());
                    showPassword = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void toastError() {
        Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void turnToHomepage() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    @Override
    public void getToken(String msg) {
        Log.e("LoginActivity",msg);
    }
}
