package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class AgregarVaca extends AppCompatActivity {
    private Tarea tareaVaca;
    private Button btnAgregarVaca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vaca);
        btnAgregarVaca = (Button) findViewById(R.id.idCargarVaca);
        btnAgregarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Dale river");
                tareaVaca = new Tarea();
                tareaVaca.execute();
            }
        });

    }
    private class Tarea extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String url =  "http://10.0.2.2:8080/api/cow/";
            ConfigOkHttp peticion = new ConfigOkHttp();
            JSONObject jsonVaca = new JSONObject();
            try {
                jsonVaca.put("cantidadPartos",0);
                jsonVaca.put("electronicId",7);
                jsonVaca.put("fechaNacimiento","2018-10-09");
                jsonVaca.put("herdId",1);
                jsonVaca.put("peso",1.3);
                jsonVaca.put("ultimaFechaParto",null);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Callback callback = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error " + call.toString());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    String mMessage = response.body().string();
                    System.out.println("a " + mMessage);
                }
            };
            peticion.post(url,jsonVaca,callback);
            return null;
        }
    }
}
