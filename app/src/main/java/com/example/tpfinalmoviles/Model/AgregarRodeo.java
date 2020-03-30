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
import com.example.tpfinalmoviles.io.Response.Vaca;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AgregarRodeo extends AppCompatActivity {
    private static final String ERROR_POST = "Error al cargar los datos del rodeo.";
    private static final String CORRECT_POST = "Rodeo cargado con exito.";

    private EditText etLocalidad;
    private TextView etInfo;
    private Button bCargar, bBack;
    private String sLocalidad;
    private String mMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rodeo);
        bCargar = (Button) findViewById(R.id.bCargar);
       // bCargar.setEnabled(false);
        bBack = (Button) findViewById(R.id.bBack);
        etLocalidad = (EditText)findViewById(R.id.etLocalidad);
        etInfo = findViewById(R.id.etInfo);
        //Hay que controlar que cargue algo en localidad, porque permite realizar post sin caracteres.
        bCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bCargar.setText("Enviando Datos");
               // bCargar.setEnabled(false);
                System.out.println("entro");
                if (esValido(etLocalidad))
                    agregarRodeo();

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


    private void agregarRodeo() {
        String localidad = etLocalidad.getText().toString();

        Rodeo rodeo = new Rodeo(localidad);

        Call<Rodeo> call = CowApiAdapter.getApiService().agregarRodeo(rodeo);
        call.enqueue(new Callback<Rodeo>() {
            @Override
            public void onResponse(Call<Rodeo> call, Response<Rodeo> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Codigo " + response.code());
                    return;
                }
                System.out.println("Codigo " + response.code());
                Rodeo rodeoResponse = response.body();
                etInfo.setText("Id Rodeo: " + String.valueOf(rodeoResponse.getId()));
            }

            @Override
            public void onFailure(Call<Rodeo> call, Throwable t) {

            }
        });
    }
}