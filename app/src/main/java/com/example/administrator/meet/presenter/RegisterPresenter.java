package com.example.administrator.meet.presenter;

import com.example.administrator.meet.model.RegisterModel;
import com.example.administrator.meet.model.RegisterModelCallBack;
import com.example.administrator.meet.view.RegisterViewImpl;

import java.util.Map;

import okhttp3.FormBody;

public class RegisterPresenter {

    private RegisterViewImpl registerViewImpl;
    private final RegisterModel registerModel;


    public RegisterPresenter(RegisterViewImpl registerViewImpl){
        this.registerViewImpl = registerViewImpl;
        this.registerModel = new RegisterModel();
    }

    //接受验证码
    public void GetCode(Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map!=null)
        {
            for (Map.Entry<String,String> entry:map.entrySet())
            {
                builder.add(entry.getKey(),entry.getValue());
            }
        }

        //向Model层发送数据
        registerModel.RegisterModelVerifyUserInfo(map, new RegisterModelCallBack(){
            @Override
            public void onSuccess(String msg) {
                registerViewImpl.getSessionId(msg);
            }

            @Override
            public void onFailed(String msg) {
                registerViewImpl.toastGetError();
            }
        });
    }

    //提交验证码
    public void SubmitCode(Map<String, String> map){
        FormBody.Builder builder = new FormBody.Builder();
        if (map!=null)
        {
            for (Map.Entry<String,String> entry:map.entrySet())
            {
                builder.add(entry.getKey(),entry.getValue());
            }
        }

        //向Model层发送数据
        registerModel.RegisterModelVerifyUserInfo(map, new RegisterModelCallBack(){
            @Override
            public void onSuccess(String msg) {
                registerViewImpl.turnToHomepage();
            }

            @Override
            public void onFailed(String msg) {
                registerViewImpl.toastSubmitError();
            }
        });
    }
}
