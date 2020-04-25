package com.example.tpfinalmoviles.Model;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.ToastHandler;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.Vaca;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConsultarVaca extends AppCompatActivity {
    private static String ERROR_POST = "Error al consultar el animal";
    private static String CORRECT_POST = "Animal cargado con exito";
    private static String ERROR_CONECTION = "Error de conexión";

    private Button bConsultarVaca;
    private Button bRegresar;
    private EditText idVaca;
    private ScrollView scrollView;
    private TextView electronicoView,manadaView,fechaNacimientoView,pesoView,cantPartosView,fechaUltParto,bcsView,fechaBcsView,ccView;

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
        bcsView = (TextView) findViewById(R.id.etIdBCS);
        fechaBcsView = (TextView) findViewById(R.id.etFechaBCS);
        ccView = (TextView) findViewById(R.id.etCC);
        bConsultarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (esValido(idVaca)){
                    bConsultarVaca.setText("Enviando Datos");
                    bConsultarVaca.setEnabled(false);
                    getVaca();
                }else
                    ToastHandler.get().showToast(getApplicationContext(), "IdVaca Invalido", Toast.LENGTH_SHORT);
            }
        });
        bRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("");
        outState.putInt("visibilidad", scrollView.getVisibility());
        outState.putString("cantPartos",cantPartosView.getText().toString());
        outState.putString("idElectronic",electronicoView.getText().toString());
        outState.putString("idRodeo",manadaView.getText().toString());
        outState.putString("fechaNac",fechaNacimientoView.getText().toString());
        outState.putString("pesoView",pesoView.getText().toString());
        outState.putString("fechaUltPartos",fechaUltParto.getText().toString());
        outState.putString("bcsView",bcsView.getText().toString());
        outState.putString("fechaBcs",fechaBcsView.getText().toString());
        outState.putString("cc",ccView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int visible = savedInstanceState.getInt("visibilidad");
        scrollView.setVisibility(visible);
        String cantPartos = savedInstanceState.getString("cantPartos");
        String idElectronic = savedInstanceState.getString("idElectronic");
        String idRodeo = savedInstanceState.getString("idRodeo");
        String fechaNac = savedInstanceState.getString("fechaNac");
        String peso = savedInstanceState.getString("pesoView");
        String fechaUltPart = savedInstanceState.getString("fechaUltPartos");
        String bcsId = savedInstanceState.getString("bcsView");
        String fechaBcs = savedInstanceState.getString("fechaBcs");
        String cc = savedInstanceState.getString("cc");

        cantPartosView.setText(cantPartos);
        ccView.setText(cc);
        fechaBcsView.setText(fechaBcs);
        bcsView.setText(bcsId);
        fechaUltParto.setText(fechaUltPart);
        pesoView.setText(peso);
        fechaNacimientoView.setText(fechaNac);
        manadaView.setText(idRodeo);
        electronicoView.setText(idElectronic);
    }

    private boolean esValido(EditText editText) {
        if (editText.getText().toString().length()>0)
            return true;
        return false;
    }

    private void getVaca(){
        final int idV = Integer.parseInt(idVaca.getText().toString());
        Call<Vaca> call = CowApiAdapter.getApiService().getCowID(idV);

        call.enqueue(new Callback<Vaca>() {
            @Override
            public void onResponse(Call<Vaca> call, Response<Vaca> response) {
                if (!response.isSuccessful()){
                    bConsultarVaca.setText("Consultar Vaca");
                    bConsultarVaca.setEnabled(true);
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
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
                    fechaBcsView.setText("--");
                else
                    fechaBcsView.setText(vaca.getFechaBcs().substring(0,10));
                bcsView.setText(String.valueOf(vaca.getCowBcsId()));
                ccView.setText(String.valueOf(vaca.getCc()));
                bConsultarVaca.setText("Consultar Vaca");
                bConsultarVaca.setEnabled(true);
                scrollView.setVisibility(View.VISIBLE);
                ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
            }
            @Override
            public void onFailure(Call<Vaca> call, Throwable t) {
                bConsultarVaca.setText("Consultar Vaca");
                bConsultarVaca.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), ERROR_CONECTION, Toast.LENGTH_SHORT);
            }
        });

    }

}
