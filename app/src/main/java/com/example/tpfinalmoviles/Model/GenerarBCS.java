package com.example.tpfinalmoviles.Model;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.Sesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerarBCS extends AppCompatActivity {

    private Button bConsultarVaca;
    private Button bRegresa;
    private Switch simpleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_bcs);
        bRegresa = (Button) findViewById(R.id.bBack);
        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean b = simpleSwitch.isChecked();
                generarBCS(simpleSwitch.isChecked());
            }
        });
//set the current state of a Switch

/*        bConsultarVaca = (Button) findViewById(R.id.bConsultar);


        bConsultarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarea = new Tarea();
                tarea.execute(true);
            }
        });

        bRegresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarea = new Tarea();
                tarea.execute(false);
            }
        });*/
    }

    private void generarBCS(boolean checked) {
        Sesion sesion = new Sesion(checked);

        Call<Sesion> call = CowApiAdapter.getApiService().generarSesion(sesion);
        call.enqueue(new Callback<Sesion>() {
            @Override
            public void onResponse(Call<Sesion> call, Response<Sesion> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Codigo " + response.code());
                    return;
                }
                System.out.println("Codigo " + response.code());
            }

            @Override
            public void onFailure(Call<Sesion> call, Throwable t) {

            }
        });
    }
}
