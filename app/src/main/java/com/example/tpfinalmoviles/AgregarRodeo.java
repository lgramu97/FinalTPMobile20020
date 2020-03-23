package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONObject;

import okhttp3.OkHttpClient;

public class AgregarRodeo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rodeo);
    }

    private class Inner extends AsyncTask<Void, Void, Void> { //parametros, progresos, resultados

        @Override
        protected Void doInBackground(Void... voids) {
            ConfigOkHttp peticion = new ConfigOkHttp(new OkHttpClient());

            JSONObject json = new JSONObject();
           // json.put("location", loc.text.toString());

            //val conexion = "http://"+getSharedPreferences(ConfiguracionUrlActivity.PREFS_FILENAME, Context.MODE_PRIVATE).getString("address", "")+"/api/herd"
            return null;
        };



    };
}
