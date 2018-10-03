package com.example.administrator.meet.utils;

import android.os.Handler;
import android.util.Log;

import com.example.administrator.meet.model.LoginModelCallBack;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    static Handler handler = new Handler();
    static OkHttpClient client = new OkHttpClient();

    public static void post(String cookie, String address, Map<String, String> map, final OkHttpCallBack okHttpCallBack)
    {

        FormBody.Builder builder = new FormBody.Builder();
        if (map!=null)
        {
            for (Map.Entry<String,String> entry:map.entrySet())
            {
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .addHeader("cookie",cookie)
                .url(address)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Headers headers = response.headers();
                            okHttpCallBack.onSuccess(response,headers);
                        }
                    });
                }else{
                    final String result=response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okHttpCallBack.onFailed(result);
                        }
                    });
                }

            }
        });
    }

    public  void put (String url,okhttp3.Callback callback, Map<String,String> map){

        FormBody.Builder builder = new FormBody.Builder();
        if (map!=null)
        {
            for (Map.Entry<String,String> entry:map.entrySet())
            {
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public  void get (String url,okhttp3.Callback callback){

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public  void get_with_Params(String url,okhttp3.Callback callback,LinkedHashMap<String,String> params){

        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i=0;i<params.size();i++ ) {
            String value=null;
            try {
                value= URLEncoder.encode(values.next(),"utf-8");
            }catch (Exception e){
                e.printStackTrace();
            }

            stringBuffer.append(keys.next()+"="+value);
            if (i!=params.size()-1) {
                stringBuffer.append("&");
            }
        }


        Request request = new Request.Builder()
                .url(url + stringBuffer.toString())
                .build();
        client.newCall(request).enqueue(callback);
    }
}
