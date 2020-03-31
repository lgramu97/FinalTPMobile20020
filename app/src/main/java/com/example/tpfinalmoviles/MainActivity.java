package com.example.tpfinalmoviles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tpfinalmoviles.Model.AgregarRodeo;
import com.example.tpfinalmoviles.Model.AgregarRodeoAlerta;
import com.example.tpfinalmoviles.Model.AgregarVaca;
import com.example.tpfinalmoviles.Model.AgregarVacaAlerta;
import com.example.tpfinalmoviles.Model.ConsultarRodeo;
import com.example.tpfinalmoviles.Model.ConsultarVaca;
import com.example.tpfinalmoviles.Model.GenerarBCS;
import com.example.tpfinalmoviles.Utils.ToastHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private CardView idAgregarVaca, idAgregarRodeo, idAgregarAlertaVaca, idAgregarAlertaRodeo
                    ,idConsultarVaca, idConsultarRodeo, idGenerarBCS, idInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idAgregarVaca = (CardView) findViewById(R.id.idAgregarVaca);
        idAgregarRodeo = (CardView) findViewById(R.id.idAgregarRodeo);
        idAgregarAlertaRodeo = (CardView) findViewById(R.id.idAgregarAlertaRodeo);
        idAgregarAlertaVaca = (CardView) findViewById(R.id.idAgregarAlertaVaca);
        idConsultarVaca = (CardView) findViewById(R.id.idConsultarVaca);
        idConsultarRodeo = (CardView) findViewById(R.id.idConsultarRodeo);
        idGenerarBCS = (CardView) findViewById(R.id.idGenerarBCS);
        idInfo = (CardView) findViewById(R.id.idInfo);

        idAgregarVaca.setOnClickListener(this);
        idAgregarRodeo.setOnClickListener(this);
        idAgregarAlertaRodeo.setOnClickListener(this);
        idAgregarAlertaVaca.setOnClickListener(this);
        idConsultarVaca.setOnClickListener(this);
        idConsultarRodeo.setOnClickListener(this);
        idGenerarBCS.setOnClickListener(this);
        idInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.idAgregarVaca:
                i = new Intent(this, AgregarVaca.class);
                startActivity(i);
                break;
            case R.id.idAgregarRodeo:
                i = new Intent(this, AgregarRodeo.class);
                startActivity(i);
                break;
            case R.id.idAgregarAlertaRodeo:
                i = new Intent(this, AgregarRodeoAlerta.class);
                startActivity(i);
                break;
            case R.id.idAgregarAlertaVaca:
                i = new Intent(this, AgregarVacaAlerta.class);
                startActivity(i);
                break;
            case R.id.idConsultarVaca:
                i = new Intent(this, ConsultarVaca.class);
                startActivity(i);
                break;
            case R.id.idConsultarRodeo:
                i = new Intent(this, ConsultarRodeo.class);
                startActivity(i);
                break;
            case R.id.idGenerarBCS:
                i = new Intent(this, GenerarBCS.class);
                startActivity(i);
                break;
            case R.id.idInfo:
                ToastHandler.get().showToast(getApplicationContext(), "APP Desarrollado por: Gramuglia Lautaro - Stampone Juan Manuel", Toast.LENGTH_SHORT);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}

