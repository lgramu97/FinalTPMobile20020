package com.example.tpfinalmoviles.Model;

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


import com.example.tpfinalmoviles.PlaceHolder;
import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.ConfigOkHttp;
import com.example.tpfinalmoviles.Utils.ConfigServer;
import com.example.tpfinalmoviles.Vaca;


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

}
