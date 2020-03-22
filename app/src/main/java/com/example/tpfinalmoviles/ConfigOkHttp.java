package com.example.tpfinalmoviles;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;
import static android.content.ContentValues.TAG;

public class ConfigOkHttp {
    private OkHttpClient client;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public ConfigOkHttp(OkHttpClient client) {
        this.client = client;
    }

    public void POST(String url, JSONObject json){
        client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(json.toString(), JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                Log.e(TAG, mMessage);
            }
        });

    };

    public void GET(String url){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                Log.e(TAG, mMessage);
            }
        });
    }

    public Call GET(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }
}
