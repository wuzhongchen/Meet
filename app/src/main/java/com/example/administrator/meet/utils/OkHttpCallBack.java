package com.example.administrator.meet.utils;

import okhttp3.Headers;
import okhttp3.Response;

public interface OkHttpCallBack {
    void onSuccess(Response msg, Headers headers);
    void onFailed(String msg);
}
