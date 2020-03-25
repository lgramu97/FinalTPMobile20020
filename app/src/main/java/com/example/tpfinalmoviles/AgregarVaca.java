package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class AgregarVaca extends AppCompatActivity {
    private Tarea tareaVaca;
    private Button btnAgregarVaca;
    private Button btnRegresarVaca;
    private EditText etCantidadPartos;
    private EditText etFechaNacimiento;
    private EditText etFechaUltParto;
    private Long idCantPartos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vaca);
        btnAgregarVaca = (Button) findViewById(R.id.idCargarVaca);
        btnRegresarVaca = (Button) findViewById(R.id.idbBackVaca);
        etFechaNacimiento = (EditText) findViewById(R.id.idFechaNacimiento);
        etFechaUltParto = (EditText) findViewById(R.id.idUltFechaParto);
        etCantidadPartos = (EditText) findViewById(R.id.idCantidadDePartos);

        etFechaUltParto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(2);
            }
        });
        etFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(1);

            }
        });
        btnAgregarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Dale river");
                System.out.println("aaas" + etCantidadPartos.getText().toString());
                tareaVaca = new Tarea();
                tareaVaca.execute(etCantidadPartos.getText().toString());
            }
        });
        btnRegresarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showDatePickerDialog(int p ) {
        if (p == 1 ){

            DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    // +1 because January is zero
                    final String selectedDate = day + " / " + (month + 1) + " / " + year;
                    etFechaNacimiento.setText(selectedDate);

                }
            });

            newFragment.show(getSupportFragmentManager(), "datePicker");
        }
        else{
            DatePickerFragment nf = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    // +1 because January is zero
                    final String selectedDate = day + " / " + (month + 1) + " / " + year;
                    etFechaUltParto.setText(selectedDate);
                }
            });

            nf.show(getSupportFragmentManager(), "datePicker");
        }
    }

    private class Tarea extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings){
            System.out.println("jejejeje" + strings[0]);
            String url = getSharedPreferences(ConfigServer.URL_DETAILS,MODE_PRIVATE).getString("url","")+"api/cow/";
            ConfigOkHttp peticion = new ConfigOkHttp();
            JSONObject jsonVaca = new JSONObject();
            try {
                jsonVaca.put("cantidadPartos",Long.valueOf(strings[0]));
                jsonVaca.put("electronicId",7);
                jsonVaca.put("fechaNacimiento","2018-10-09");
                jsonVaca.put("herdId",1);
                jsonVaca.put("peso",1.3);
                jsonVaca.put("ultimaFechaParto",null);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Callback callback = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error " + call.toString());
                    System.out.println(e.toString());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    String mMessage = response.body().string();
                    System.out.println(mMessage);
                }
            };
            peticion.post(url,jsonVaca,callback);
            return null;
        }
    }
}
