package com.example.administrator.meet.model;

import com.example.administrator.meet.utils.OkHttpCallBack;
import com.example.administrator.meet.utils.OkHttpUtil;

import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Response;

public class RegisterModel implements RegisterModelImpl{

    public List<String> cookies;

    @Override
    public void RegisterModelVerifyUserInfo(Map<String, String> map, final RegisterModelCallBack registerModelCallBack) {
        OkHttpUtil.post("","https://sf.bitzo.cn/api/login", map, new OkHttpCallBack() {
            @Override
            public void onSuccess(Response response, Headers headers) {

                final String sessionid = Json_getSessionId(response,headers);
                registerModelCallBack.onSuccess(sessionid);
            }

            @Override
            public void onFailed(String msg) {
                registerModelCallBack.onFailed(msg);
            }
        });

    }

    private String Json_getSessionId(Response response, Headers headers) {
        cookies = headers.values("Set-Cookie");
        String session = cookies.get(0);
        String sessionid = session.substring(0, session.indexOf(";"));
        return sessionid;
    }
}
