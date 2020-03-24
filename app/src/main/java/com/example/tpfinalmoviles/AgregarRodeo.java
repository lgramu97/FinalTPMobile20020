package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class AgregarRodeo extends AppCompatActivity {
    private static final String ERROR_POST = "Error al cargar los datos.";
    private static final String CORRECT_POST = "Datos almacenados correctamete.:";

    private TextView etLocalidad;
    private Button bCargar, bBack;
    private Tarea tareaRodeo;
    private String sLocalidad;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rodeo);
        bCargar = (Button) findViewById(R.id.bCargar);
        bCargar.setEnabled(false);
        bBack = (Button) findViewById(R.id.bBack);
        etLocalidad = findViewById(R.id.etLocalidad);

       etLocalidad.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
           @Override
           public void afterTextChanged(Editable s) {}

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int before, int count) {
               if (count>0){ //count es cantidad de caracteres que tiene
                   bCargar.setEnabled(true);
                   sLocalidad = etLocalidad.getText().toString();
               }else{
                   bCargar.setEnabled(false);
               }
           }
       });

        bCargar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               System.out.println("Riber gay");
               bCargar.setEnabled(true);
               tareaRodeo = new Tarea();
               tareaRodeo.execute();
           }
       });
        bBack.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

   }

    private class Tarea extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String url = getSharedPreferences(ConfigServer.URL_DETAILS,MODE_PRIVATE).getString("url","")+"api/herd/";
            ConfigOkHttp peticion = new ConfigOkHttp();
            JSONObject jsonRodeo = new JSONObject();
            try {
                jsonRodeo.put("location", sLocalidad);
//                Toast.makeText(AgregarRodeo.this,CORRECT_POST,Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
  //              Toast.makeText(AgregarRodeo.this,ERROR_POST,Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            Callback callback = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error");
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    String mMessage = response.body().string();
                    System.out.println("a " + mMessage);
                    ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                }

            };
            peticion.post(url,jsonRodeo,callback);
            return null;
        }


    }
}
