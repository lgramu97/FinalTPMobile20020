package com.example.tpfinalmoviles.Model;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;

import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.Rodeo;
import com.example.tpfinalmoviles.io.Response.RodeoAlerta;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarRodeoAlerta extends AppCompatActivity {

    private static final String ERROR_POST = "Error al cargar alerta.";
    private static final String CORRECT_POST = "Alerta rodeo cargada con exito.";

    private EditText etIdHerd, etBCSmax,etBCSmin;
    private TextView etInfo;
    private Button bCargar, bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rodeo_alerta);
        bCargar = (Button) findViewById(R.id.bCargar);
        bBack = (Button) findViewById(R.id.bBack);
        etInfo = (TextView)findViewById(R.id.etInfo);
        etIdHerd = (EditText)findViewById(R.id.etIdHerd);
        etBCSmax = (EditText)findViewById(R.id.etBCSmax);
        etBCSmin = (EditText)findViewById(R.id.etBCSmin);

        //No es necesario controlar que cargue algo en los campos, restricciones ya sobre base de datos.
        bCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      bCargar.setText("Enviando Datos");
          //      bCargar.setEnabled(false);
                if (esValido(etIdHerd) && esValido(etBCSmax) && esValido(etBCSmin))
                    agregarRodeoAlerta();
            }
        });

        bBack.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void agregarRodeoAlerta() {
        int herdId = Integer.parseInt(etIdHerd.getText().toString());
        double max = Double.valueOf(etBCSmax.getText().toString());
        double min =  Double.valueOf(etBCSmin.getText().toString());

        RodeoAlerta rodeoAlerta = new RodeoAlerta(herdId,max,min);

        retrofit2.Call<RodeoAlerta> call = CowApiAdapter.getApiService().agregarRodeoAlerta(rodeoAlerta);
        call.enqueue(new Callback<RodeoAlerta>() {
            @Override
            public void onResponse(retrofit2.Call<RodeoAlerta> call, Response<RodeoAlerta> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Codigo " + response.code());
                    return;
                }
                System.out.println("Codigo " + response.code());
                RodeoAlerta rodeoAlertaResponse = response.body();
                etInfo.setText("Id Rodeo Alerta: " + String.valueOf(rodeoAlertaResponse.getId()));
            }

            @Override
            public void onFailure(Call<RodeoAlerta> call, Throwable t) {

            }
        });
    }


    private boolean esValido(EditText editText) {
        if (editText.getText().toString().length()>0)
            return true;
        return false;
    }

    /*
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


    }*/
}
