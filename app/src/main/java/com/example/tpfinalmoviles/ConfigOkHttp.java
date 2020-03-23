package com.example.tpfinalmoviles;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.*;
import static android.content.ContentValues.TAG;

public class ConfigOkHttp {
    private OkHttpClient client;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public ConfigOkHttp(OkHttpClient client) {
        this.client = client;
    }

    public void post(String url, JSONObject json,Callback callback){
        client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(json.toString(), JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);

    };

    public void get(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

}