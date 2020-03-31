package com.example.tpfinalmoviles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tpfinalmoviles.Model.AgregarRodeo;
import com.example.tpfinalmoviles.Model.AgregarRodeoAlerta;
import com.example.tpfinalmoviles.Model.AgregarVaca;
import com.example.tpfinalmoviles.Model.AgregarVacaAlerta;
import com.example.tpfinalmoviles.Model.ConsultarRodeo;
import com.example.tpfinalmoviles.Model.ConsultarVaca;
import com.example.tpfinalmoviles.Model.GenerarBCS;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private TextView mTextViewResult;
    private  OkHttpClient client; //= new OkHttpClient();
    private Button botonTest;
    private CardView idAgregarVaca, idAgregarRodeo, idAgregarAlertaVaca, idAgregarAlertaRodeo
                    ,idConsultarVaca, idConsultarRodeo, idGenerarBCS;

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

        idAgregarVaca.setOnClickListener(this);
        idAgregarRodeo.setOnClickListener(this);
        idAgregarAlertaRodeo.setOnClickListener(this);
        idAgregarAlertaVaca.setOnClickListener(this);
        idConsultarVaca.setOnClickListener(this);
        idConsultarRodeo.setOnClickListener(this);
        idGenerarBCS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        System.out.println("aaa");
        switch (v.getId()){
            case R.id.idAgregarVaca:
                i = new Intent(this, AgregarVaca.class);
                startActivity(i);
                System.out.println("aaa");
                break;
            case R.id.idAgregarRodeo:
                i = new Intent(this, AgregarRodeo.class);
                startActivity(i);
                System.out.println("boca amargo");
                break;
            case R.id.idAgregarAlertaRodeo:
                i = new Intent(this, AgregarRodeoAlerta.class);
                startActivity(i);
                System.out.println("te cogimos en europa ");
                break;
            case R.id.idAgregarAlertaVaca:
                i = new Intent(this, AgregarVacaAlerta.class);
                startActivity(i);
                System.out.println("en mendoza tmb");
                break;
            case R.id.idConsultarVaca:
                i = new Intent(this, ConsultarVaca.class);
                startActivity(i);
                System.out.println("maaaiaaaaameeeeee");
                break;
            case R.id.idConsultarRodeo:
                i = new Intent(this, ConsultarRodeo.class);
                startActivity(i);
                System.out.println("en todos lados");
                break;
            case R.id.idGenerarBCS:
                i = new Intent(this, GenerarBCS.class);
                startActivity(i);
                System.out.println("en todos lados");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}

