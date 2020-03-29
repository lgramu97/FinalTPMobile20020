package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AgregarVacaAlerta extends AppCompatActivity {
    private static final String ERROR_POST = "Error al cargar alerta.";
    private static final String CORRECT_POST = "Alerta Vaca cargada con exito.";

    private EditText etIdVaca, etBCSmax,etBCSmin;
    private TextView etInfo;
    private Button bCargar, bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vaca_alerta);
        bCargar = (Button) findViewById(R.id.bCargar);
        bBack = (Button) findViewById(R.id.bBack);
        etInfo = findViewById(R.id.etInfo);
        etIdVaca = (EditText)findViewById(R.id.etIdVaca);
        etBCSmax = (EditText)findViewById(R.id.etBCSmax);
        etBCSmin = (EditText)findViewById(R.id.etBCSmin);

        //No es necesario controlar que cargue algo en los campos, restricciones ya sobre base de datos.
        bCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bCargar.setText("Enviando Datos");
                bCargar.setEnabled(false);
                if(esValido(etIdVaca) && esValido (etBCSmin) && esValido(etBCSmin))
                    agregarVacaAlerta();

            }
        });

        bBack.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private boolean esValido(EditText editText) {
        if (editText.getText().toString().length()>0)
            return true;
        return false;
    }

    private void agregarVacaAlerta() {
        String url = getSharedPreferences(ConfigServer.URL_DETAILS,MODE_PRIVATE).getString("url","")+"api/";
        int idVaca = Integer.parseInt(etIdVaca.getText().toString());
        double maxBCS = Double.parseDouble(etBCSmax.getText().toString());
        double minBCS = Double.parseDouble((etBCSmin.getText().toString()));


        VacaAlerta vacaAlerta = new VacaAlerta(idVaca,maxBCS,minBCS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlaceHolder placeholder = retrofit.create(PlaceHolder.class);
        Call<VacaAlerta> call = placeholder.agregarVacaAlerta(vacaAlerta);
        call.enqueue(new Callback<VacaAlerta>() {
            @Override
            public void onResponse(Call<VacaAlerta> call, Response<VacaAlerta> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Codigo " + response.code());
                    return;
                }
                System.out.println("Codigo " + response.code());
                VacaAlerta vacaResponseAlerta = response.body();
                etInfo.setText("Id Vaca Alerta: " + String.valueOf(vacaResponseAlerta.getCowId()));
            }
            @Override
            public void onFailure(Call<VacaAlerta> call, Throwable t) {
            }
        });
    }




/*


    private class Tarea extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String url = getSharedPreferences(ConfigServer.URL_DETAILS,MODE_PRIVATE).getString("url","")+"api/cowAlert/";
            ConfigOkHttp peticion = new ConfigOkHttp();
            final JSONObject jsonRodeo = new JSONObject();
            final Long cowId = (etIdVaca.getText().toString().length() != 0) ? Long.parseLong((etIdVaca.getText().toString())) : 0;
            final Double max = (etBCSmax.getText().toString().length() != 0) ? Double.valueOf((etBCSmax.getText().toString())) : 0.0;
            final Double min = (etBCSmin.getText().toString().length() != 0) ? Double.valueOf((etBCSmin.getText().toString())) : 0.0;
            try {
                jsonRodeo.put("cowId", cowId);
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
                                etInfo.setText("ID Alerta Vaca: " + json.getString("id"));
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
