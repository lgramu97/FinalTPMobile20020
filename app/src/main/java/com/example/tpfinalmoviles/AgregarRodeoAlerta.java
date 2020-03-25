package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AgregarRodeoAlerta extends AppCompatActivity {

    private static final String ERROR_POST = "Error al cargar alerta.";
    private static final String CORRECT_POST = "Alerta rodeo cargada con exito.";

    private TextView etIdHerd, etBCSmax,etBCSmin, etInfo;
    private Button bCargar, bBack;
    private Tarea tareaRodeoAlerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rodeo_alerta);
        bCargar = (Button) findViewById(R.id.bCargar);
        bBack = (Button) findViewById(R.id.bBack);
        etInfo = findViewById(R.id.etInfo);
        etIdHerd = findViewById(R.id.etIdHerd);
        etBCSmax = findViewById(R.id.etBCSmax);
        etBCSmin = findViewById(R.id.etBCSmin);

        //No es necesario controlar que cargue algo en los campos, restricciones ya sobre base de datos.
        bCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("River 3 boca 1");
                bCargar.setText("Enviando Datos");
                bCargar.setEnabled(false);
                tareaRodeoAlerta = new Tarea();
                tareaRodeoAlerta.execute();
            }
        });

        bBack.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class Tarea extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String url = getSharedPreferences(ConfigServer.URL_DETAILS,MODE_PRIVATE).getString("url","")+"api/herdAlert/";
            ConfigOkHttp peticion = new ConfigOkHttp();
            final JSONObject jsonRodeo = new JSONObject();
            final Long herdId = (etIdHerd.getText().toString().length() != 0) ? Long.parseLong((etIdHerd.getText().toString())) : 0;
            final Double max = (etBCSmax.getText().toString().length() != 0) ? Double.valueOf((etBCSmax.getText().toString())) : 0.0;
            final Double min = (etBCSmin.getText().toString().length() != 0) ? Double.valueOf((etBCSmin.getText().toString())) : 0.0;
            try {
                jsonRodeo.put("herdId", herdId);
                jsonRodeo.put("bcsThresholdMax", max);
                jsonRodeo.put("bcsThresholdMin", min);
            } catch (JSONException e) {
                System.out.println("ERROR AL CARGAR JSON");
                e.printStackTrace();
            }
            final Callback callback = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error");
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bCargar.setText("Cargar Alerta");
                            bCargar.setEnabled(true);
                        }
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    final String mMessage = response.body().string();
                    System.out.println("a " + mMessage);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bCargar.setText("Cargar Alerta");
                            bCargar.setEnabled(true);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(mMessage);
                                etInfo.setText("ID Alerta Rodeo: " + json.getString("id"));
                                ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                            } catch (JSONException e) {
                                ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
            peticion.post(url,jsonRodeo,callback);
            System.out.println("FIN " + jsonRodeo.toString());
            return null;
        }


    }
}
