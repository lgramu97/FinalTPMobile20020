package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import okhttp3.OkHttpClient;

public class AgregarRodeo extends AppCompatActivity {
    private TextView etLocalidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rodeo);
    }

    private class Inner extends AsyncTask<Void, Void, Void> { //parametros, progresos, resultados

        @Override
        protected Void doInBackground(Void... voids) {
            ConfigOkHttp peticion = new ConfigOkHttp(new OkHttpClient());
            etLocalidad = findViewById(R.id.etLocalidad);
            JSONObject json = new JSONObject();
            try {
                json.put("location", etLocalidad.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

           // String conexion = "http://"+getSharedPreferences(ConfiguracionUrlActivity.PREFS_FILENAME, Context.MODE_PRIVATE).getString("address", "")+"/api/herd";
            return null;
        };



    };
}
