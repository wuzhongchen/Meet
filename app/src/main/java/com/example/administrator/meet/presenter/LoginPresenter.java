package com.example.administrator.meet.presenter;

import com.example.administrator.meet.model.LoginModel;
import com.example.administrator.meet.model.LoginModelImpl;
import com.example.administrator.meet.model.LoginModelCallBack;
import com.example.administrator.meet.view.LoginView;

import java.lang.ref.WeakReference;
import java.util.Map;

import okhttp3.FormBody;

public class LoginPresenter<T extends LoginView> {

    WeakReference<T> loginView;
    final LoginModel loginModel = new LoginModelImpl();

    public LoginPresenter(T view) {
        this.loginView = new WeakReference<T>(view);
    }

    public void fetch (Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (Map.Entry<String,String> entry:map.entrySet())
            {
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        if(loginView.get() != null && loginModel != null) {
            loginView.get().showProgress();
        }
        //向Model层发送数据
        loginModel.loginVerifyInfo(map, new LoginModelCallBack() {
            @Override
            public void onSuccess(String msg) {
                loginView.get().hindProgress();
                loginView.get().getToken(msg);
                loginView.get().turnToHomepage();
            }
            @Override
            public void onFailed(String msg) {
                loginView.get().hindProgress();
                loginView.get().toastError();
            }
        });
    }

}
