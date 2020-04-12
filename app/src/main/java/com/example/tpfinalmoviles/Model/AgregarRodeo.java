package com.example.tpfinalmoviles.Model;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.ToastHandler;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.Rodeo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AgregarRodeo extends AppCompatActivity {
    private static String ERROR_POST = "Error al cargar los datos del rodeo";
    private static String CORRECT_POST = "Rodeo cargado con exito";
    private static String ERROR_CONECTION = "Error de conexión";

    private EditText etLocalidad;
    private TextView etInfo;
    private Button bCargar, bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rodeo);
        bCargar = (Button) findViewById(R.id.bCargar);
        bBack = (Button) findViewById(R.id.bBack);
        etLocalidad = (EditText)findViewById(R.id.etLocalidad);
        etInfo = findViewById(R.id.etInfo);
        //Hay que controlar que cargue algo en localidad, porque permite realizar post sin caracteres.
        bCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (esValido(etLocalidad)) {
                    bCargar.setText("Enviando Datos");
                    bCargar.setEnabled(false);
                    agregarRodeo();
                }else
                    ToastHandler.get().showToast(getApplicationContext(), "Invalid Location", Toast.LENGTH_SHORT);
            }
        });
        bBack.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("info",etInfo.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        etInfo.setText(savedInstanceState.getString("info"));
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
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                    bCargar.setText("CARGAR RODEO");
                    bCargar.setEnabled(true);
                    return;
                }
                Rodeo rodeoResponse = response.body();
                etInfo.setText("Id Rodeo: " + String.valueOf(rodeoResponse.getId()));
                bCargar.setText("CARGAR RODEO");
                bCargar.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<Rodeo> call, Throwable t) {
                bCargar.setText("CARGAR RODEO");
                bCargar.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), ERROR_CONECTION, Toast.LENGTH_SHORT);
            }
        });
    }
}