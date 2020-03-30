package com.example.tpfinalmoviles.Model;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.PlaceHolder;
import com.example.tpfinalmoviles.R;

import com.example.tpfinalmoviles.Utils.ConfigServer;

import com.example.tpfinalmoviles.VacaAlerta;



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
}
