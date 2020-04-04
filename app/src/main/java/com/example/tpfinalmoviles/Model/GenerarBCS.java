package com.example.tpfinalmoviles.Model;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.ToastHandler;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.Sesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerarBCS extends AppCompatActivity {
    private final String CORRECT_POST = "Generando indice masa corporal";
    private final String ERROR_POST = "Ups, algo anda mal";
    private final String ERROR_CONECTION = "Fallo la conexion con el servidor";

    private Switch simpleSwitch;
    private Button bRegrasar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_bcs);
        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
        bRegrasar = (Button) findViewById(R.id.bBack);

        bRegrasar.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                generarBCS(simpleSwitch.isChecked());
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("VALOR RESTORE:  " + simpleSwitch.isChecked());
        outState.putBoolean("sesion", simpleSwitch.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("VALOR RESTORE:  " + savedInstanceState.getBoolean("sesion"));
        generarBCS(savedInstanceState.getBoolean("sesion"));
    }

    private void generarBCS(boolean checked) {
        Sesion sesion = new Sesion(checked);

        Call<Sesion> call = CowApiAdapter.getApiService().generarSesion(sesion);
        call.enqueue(new Callback<Sesion>() {
            @Override
            public void onResponse(Call<Sesion> call, Response<Sesion> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Codigo " + response.code());
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                    return;
                }
                ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                System.out.println("Codigo " + response.code());
            }

            @Override
            public void onFailure(Call<Sesion> call, Throwable t) {
                System.out.println("ON FAILUREEE " + t.getMessage());
                System.out.println("BODY " + sesion.isEnable());
                ToastHandler.get().showToast(getApplicationContext(), ERROR_CONECTION, Toast.LENGTH_SHORT);
            }
        });
    }
}
