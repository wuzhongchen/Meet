package com.example.administrator.meet.model;

import com.example.administrator.meet.utils.OkHttpCallBack;
import com.example.administrator.meet.utils.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Response;

public class LoginModelImpl implements LoginModel {

    private String Json_getToken(String responseBody) throws JSONException {
        JSONObject jsonObject = new JSONObject(responseBody);
        String token = jsonObject.getString("token");
        return token;
    }

    @Override
    public void loginVerifyInfo(Map<String, String> map, final LoginModelCallBack loginModelCallBack) {
        OkHttpUtil.post("","https://sf.bitzo.cn/api/login", map, new OkHttpCallBack() {
            @Override
            public void onSuccess(Response response, Headers headers) {
                try {
                    String responseBody = response.body().string();
                    final String token = Json_getToken(responseBody);
                    loginModelCallBack.onSuccess(token);
                }catch (JSONException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String msg) {
                    loginModelCallBack.onFailed(msg);
            }
        });
    }
}
