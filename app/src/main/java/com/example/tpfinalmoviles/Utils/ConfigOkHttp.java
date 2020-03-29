package com.example.tpfinalmoviles.Utils;

import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ConfigOkHttp {
    private OkHttpClient client;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public ConfigOkHttp(OkHttpClient client) {
        this.client = client;
    }

    public ConfigOkHttp(){

    }

    public void post(String url, JSONObject json,Callback callback){
        client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(json.toString(), JSON);
        System.out.println("URL OKHTTP:    " + url);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        System.out.println(json.toString());
        System.out.println(request.url());
        System.out.println(request.body().toString());

        client.newCall(request).enqueue(callback);

    };

    public void get(String url, Callback callback){
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        System.out.println("paso2");
        client.newCall(request).enqueue(callback);
    }

}
