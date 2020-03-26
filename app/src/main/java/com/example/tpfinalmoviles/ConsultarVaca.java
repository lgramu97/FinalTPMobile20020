package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class ConsultarVaca extends AppCompatActivity {
    private Button bConsultarVaca;
    private Button bRegresar;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_vaca);
        bConsultarVaca = (Button) findViewById(R.id.bConsultar);
        bRegresar = (Button) findViewById(R.id.bBack);
        scrollView = findViewById(R.id.idScrollView);
        bConsultarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
