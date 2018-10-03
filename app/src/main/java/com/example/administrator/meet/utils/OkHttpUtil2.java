package com.example.administrator.meet.utils;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil2 {
    public static void post(String cookie,String address, okhttp3.Callback callback, Map<String,String> map)
    {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (map!=null)
        {
            for (Map.Entry<String,String> entry:map.entrySet())
            {
                builder.add(entry.getKey(),entry.getValue());
//                Log.e("MainActivity",entry.getKey());
//                Log.e("MainActivity",entry.getValue());
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .addHeader("cookie",cookie)
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
