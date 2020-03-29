package com.example.tpfinalmoviles.Model;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.ConfigOkHttp;
import com.example.tpfinalmoviles.Utils.ConfigServer;
import com.example.tpfinalmoviles.Utils.DatePickerFragment;
import com.example.tpfinalmoviles.Utils.ToastHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class AgregarVaca extends AppCompatActivity {
    private static final String ERROR_POST = "Error al cargar los datos del animal.";
    private static final String CORRECT_POST = "Animal cargado con exito.";

    private Tarea tareaVaca;
    private Button btnAgregarVaca,btnRegresarVaca,btnResetVaca;
    private EditText etCantidadPartos,etIdElectronico, etFechaNacimiento, etIdPeso, etFechaUltParto,etIdRodeo;
    private TextView etInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vaca);
        btnAgregarVaca = (Button) findViewById(R.id.idCargarVaca);
        btnRegresarVaca = (Button) findViewById(R.id.idbBackVaca);
        etFechaNacimiento = (EditText) findViewById(R.id.idFechaNacimiento);
        etFechaUltParto = (EditText) findViewById(R.id.idUltFechaParto);
        etCantidadPartos = (EditText) findViewById(R.id.idCantidadDePartos);
        etIdElectronico = (EditText) findViewById(R.id.idElectronico);
        etIdPeso = (EditText) findViewById(R.id.idPeso);
        etIdRodeo = (EditText) findViewById(R.id.idHerd);
        etInfo = (TextView) findViewById(R.id.etInfo);
        btnResetVaca = (Button) findViewById(R.id.idbtResetVaca) ;
        btnResetVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etFechaNacimiento.setText("");
                etFechaUltParto.setText("");
                etCantidadPartos.setText("");
                etIdElectronico.setText("");
                etIdPeso.setText("");
                etIdRodeo.setText("");
                etInfo.setText("");

            }
        });

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
                System.out.println("aaas" + etCantidadPartos.getText().toString());
                btnAgregarVaca.setText("Enviando Datos");
                btnAgregarVaca.setEnabled(false);
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
                    final String selectedDate = day + "-" + (month + 1) + "-" + year;
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
                    final String selectedDate = day + "-" + (month + 1) + "-" + year;
                    etFechaUltParto.setText(selectedDate);
                }
            });

            nf.show(getSupportFragmentManager(), "datePicker");
        }
    }

    private class Tarea extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings){
            String url = getSharedPreferences(ConfigServer.URL_DETAILS,MODE_PRIVATE).getString("url","")+"api/cow/";
            final Long cantPartos = (etCantidadPartos.getText().toString().length() != 0) ? Long.parseLong((etCantidadPartos.getText().toString())) : 0;
            final Long electronico = (etIdElectronico.getText().toString().length() != 0) ? Long.parseLong((etIdElectronico.getText().toString())) : 0;
            String fechaNacimiento = null;
            String fechaUltParto = null;
            try {
                fechaNacimiento = formatoFecha(etFechaNacimiento.getText().toString());
                fechaUltParto = formatoFecha(etFechaUltParto.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final Double peso = (etIdPeso.getText().toString().length() != 0) ? Double.valueOf((etIdPeso.getText().toString())) : 0.0;
            final Long rodeo = (etIdRodeo.getText().toString().length() != 0) ? Long.valueOf((etIdRodeo.getText().toString())) : 0;
            if (cantPartos == 0 || cantPartos == null)
                fechaUltParto=null;
            ConfigOkHttp peticion = new ConfigOkHttp();
            JSONObject jsonVaca = new JSONObject();
            try {
                jsonVaca.put("cantidadPartos",cantPartos);
                jsonVaca.put("electronicId",electronico);
                jsonVaca.put("fechaNacimiento",fechaNacimiento);
                jsonVaca.put("herdId",rodeo);
                jsonVaca.put("peso",peso);
                jsonVaca.put("ultimaFechaParto",fechaUltParto);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Callback callback = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error " + call.toString());
                    System.out.println(e.toString());
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnAgregarVaca.setText("Cargar Rodeo");
                            btnAgregarVaca.setEnabled(true);
                        }
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    final String mMessage = response.body().string();
                    System.out.println(mMessage);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnAgregarVaca.setText("Cargar Rodeo");
                            btnAgregarVaca.setEnabled(true);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(mMessage);
                                etInfo.setText("ID Vaca: " + json.getString("id"));
                                ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                            } catch (JSONException e) {
                                ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
            peticion.post(url,jsonVaca,callback);
            return null;
        }

        private String formatoFecha(String fecha) throws ParseException {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
            String formatoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(date);
            return formatoDate;
        }


    }
}
