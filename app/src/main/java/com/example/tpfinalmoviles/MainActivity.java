package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // HACER ESTO https://www.youtube.com/watch?v=d6CfaWW7G5Q JE
        /*
        Button loadApi = (Button) findViewById(R.id.loadApi);
        Button postReq = (Button) findViewById(R.id.postReq);
        final ConfigOkHttp config = new ConfigOkHttp(new OkHttpClient());
        final String url = "https://10.0.2.2:8080/api/herd/1";
        //config.POST(url,postdata);

        loadApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "https://10.0.2.2:8080/api/herd/1";

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " + response);
                            System.out.println(responseBody.string());
                        }
                    }
                });

            }
        });

        postReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject myObject = new JSONObject();
                    myObject.put("location", "Carlos");
                    myObject.put("id", new Integer(21));
                    config.POST(url, myObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/


    }

}

