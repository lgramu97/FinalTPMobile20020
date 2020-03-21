package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult = findViewById(R.id.text_view_result);

        ConfigOkHttp config = new ConfigOkHttp();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("username", "aneh1234");
            postdata.put("password", "12345");
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = "https://cakeapi.trinitytuts.com/api/add";

        config.POST(url,postdata);
    }
}
