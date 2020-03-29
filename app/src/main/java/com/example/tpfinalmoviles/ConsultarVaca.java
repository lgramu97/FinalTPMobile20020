package com.example.tpfinalmoviles;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultarVaca extends AppCompatActivity {
    private Button bConsultarVaca;
    private Button bRegresar;
    private EditText idVaca;
    private ScrollView scrollView;
    private TextView electronicoView,manadaView,fechaNacimientoView,pesoView,cantPartosView,fechaUltParto,bscView,fechaBscView,ccView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_vaca);
        bConsultarVaca = (Button) findViewById(R.id.bConsultar);
        bRegresar = (Button) findViewById(R.id.bBack);
        scrollView = findViewById(R.id.idScrollView);
        idVaca = (EditText) findViewById(R.id.idVaca);
        electronicoView = (TextView) findViewById(R.id.etIdElectronico);
        manadaView = (TextView) findViewById(R.id.etIdManada);
        fechaNacimientoView = (TextView) findViewById(R.id.etFechaNacimiento);
        pesoView = (TextView) findViewById(R.id.etPeso);
        cantPartosView = (TextView) findViewById(R.id.etCantPartos);
        fechaUltParto = (TextView) findViewById(R.id.etFechaParto);
        bscView = (TextView) findViewById(R.id.etIdBSC);
        fechaBscView = (TextView) findViewById(R.id.etFechaBSC);
        ccView = (TextView) findViewById(R.id.etCC);
        bConsultarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVaca();
                scrollView.setVisibility(View.VISIBLE);
            }
        });
        bRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getVaca(){
        final int idV = Integer.parseInt(idVaca.getText().toString());
        String url = getSharedPreferences(ConfigServer.URL_DETAILS, MODE_PRIVATE).getString("url", "") + "/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlaceHolder placeHolder = retrofit.create(PlaceHolder.class);
        Call<Vaca> call = placeHolder.getCowID(idV);

        call.enqueue(new Callback<Vaca>() {
            @Override
            public void onResponse(Call<Vaca> call, Response<Vaca> response) {
                if (!response.isSuccessful()){
                    System.out.println("Codigo: " + response.code());
                    return;
                }
                Vaca vaca = response.body();
                electronicoView.setText(String.valueOf(vaca.getElectronicId()));
                manadaView.setText(String.valueOf(vaca.getHerdId()));
                fechaNacimientoView.setText(vaca.getFechaNacimiento().substring(0,10));
                pesoView.setText(String.valueOf(vaca.getPeso()));
                cantPartosView.setText(String.valueOf(vaca.getCantidadPartos()));
                if (vaca.getUltimaFechaParto() == null)
                    fechaUltParto.setText("--");
                else
                    fechaUltParto.setText(vaca.getUltimaFechaParto().substring(0,10));
                if (vaca.getFechaBcs() == null)
                    fechaBscView.setText("--");
                else
                    fechaBscView.setText(vaca.getFechaBcs().substring(0,10));
                bscView.setText(String.valueOf(vaca.getCowBcsId()));
                ccView.setText(String.valueOf(vaca.getCc()));
            }
            @Override
            public void onFailure(Call<Vaca> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

 /*   private class Tarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            final Long idV = Long.parseLong(idVaca.getText().toString());
            System.out.println("errssor" + idV );
            String url = getSharedPreferences(ConfigServer.URL_DETAILS, MODE_PRIVATE).getString("url", "") + "api/cow/" + idV + "/";
            System.out.println("err: " + url );
            ConfigOkHttp peticion = new ConfigOkHttp();
            System.out.println("error");
            final Callback callback = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error");

                    //ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                    //runOnUiThread(new Runnable() {
                    //   @Override
                    //   public void run() {
                    //        bCargar.setText("Cargar Rodeo");
                    //        bCargar.setEnabled(true);
                    //   }
                    //});
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    final String mMessage = response.body().string();
                    // mMessage = response.body().string();
                    System.out.println("a " + mMessage);

                    //   ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject json = null;
                            try {
                                json = new JSONObject(mMessage);
                                electronicoView.setText(json.getString("electronicId"));
                                manadaView.setText(json.getString("herdId"));
                                fechaNacimientoView.setText(json.getString("fechaNacimiento"));
                                pesoView.setText(json.getString("peso"));
                                cantPartosView.setText(json.getString("cantidadPartos"));
                                if (json.getString("ultimaFechaParto").equals("null"))
                                    fechaUltParto.setText("--");
                                else
                                    fechaUltParto.setText(json.getString("ultimaFechaParto"));
                                if (json.getString("fechaBcs").equals("null"))
                                    fechaBscView.setText("--");
                                else
                                    fechaBscView.setText(json.getString("fechaBcs"));
                                bscView.setText(json.getString("cowBcsId"));
                                ccView.setText(json.getString("cc"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                        }

            };
            System.out.println("Paso1");
            peticion.get(url, callback);
            return null;
        }
    }*/
}
