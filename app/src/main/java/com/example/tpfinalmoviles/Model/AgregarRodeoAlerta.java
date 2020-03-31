package com.example.tpfinalmoviles.Model;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.ToastHandler;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.RodeoAlerta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarRodeoAlerta extends AppCompatActivity {

    private static  String ERROR_POST = "Error al cargar alerta.";
    private static  String ERROR_CONECTION = "Error de conexión.";
    private static  String CORRECT_POST = "Alerta rodeo cargada con exito.";

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

                if (esValido(etIdHerd) && esValido(etBCSmax) && esValido(etBCSmin)){
                    bCargar.setText("Enviando Datos");
                    bCargar.setEnabled(false);
                    agregarRodeoAlerta();
                }else
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
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
                    bCargar.setText("Cargar Alerta");
                    bCargar.setEnabled(true);
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                    return;
                }
                System.out.println("Codigo " + response.code());
                RodeoAlerta rodeoAlertaResponse = response.body();
                etInfo.setText("Id Rodeo Alerta: " + String.valueOf(rodeoAlertaResponse.getId()));
                bCargar.setText("Cargar Alerta");
                bCargar.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<RodeoAlerta> call, Throwable t) {
                bCargar.setText("Cargar Alerta");
                bCargar.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), ERROR_CONECTION, Toast.LENGTH_SHORT);
            }
        });
    }


    private boolean esValido(EditText editText) {
        if (editText.getText().toString().length()>0)
            return true;
        return false;
    }

}
