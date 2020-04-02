package com.example.tpfinalmoviles.Model;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.DatePickerFragment;
import com.example.tpfinalmoviles.Utils.ToastHandler;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.Vaca;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AgregarVaca extends AppCompatActivity {

    private Button btnAgregarVaca, btnRegresarVaca, btnResetVaca;
    private EditText etCantidadPartos, etIdElectronico, etFechaNacimiento, etIdPeso, etFechaUltParto, etIdRodeo;
    private TextView infoId;

    private static  String ERROR_POST = "Error al cargar los datos del animal.";
    private static  String CORRECT_POST = "Animal cargado con exito.";
    private static  String ERROR_CONEXION = "Fallo de conexion con el servidor";

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
        btnResetVaca = (Button) findViewById(R.id.idbtResetVaca);
        infoId = (TextView) findViewById(R.id.etInfo) ;

        btnResetVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etFechaNacimiento.setText("");
                etFechaUltParto.setText("");
                etCantidadPartos.setText("");
                etIdElectronico.setText("");
                etIdPeso.setText("");
                etIdRodeo.setText("");
                btnAgregarVaca.setText("Cargar Vaca");
                btnAgregarVaca.setEnabled(true);
                //Agregar el etinfo para reset el texto
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

                if (esValido(etCantidadPartos) && esValido(etIdElectronico) && esValido(etFechaNacimiento) && esValido(etIdPeso) &&
                    esValidoFechaParto(etCantidadPartos,etFechaUltParto) && esValido(etIdRodeo)){
                    btnAgregarVaca.setText("Enviando datos...");
                    btnAgregarVaca.setEnabled(false);
                    agregarVaca();
                }
                else
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
            }
        });

        btnRegresarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("info",infoId.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.get("info");
    }

    private boolean esValidoFechaParto(EditText cantPartos, EditText etFechaUltParto) {
        System.out.println(etFechaUltParto.getText().toString().length() + " SEO ");
        if (Integer.parseInt(cantPartos.getText().toString()) >= 0 && etFechaUltParto.getText().toString().length() >= 0) {
            if (Integer.parseInt(cantPartos.getText().toString()) > 0 && etFechaUltParto.getText().toString().length() == 0)
                return false;
            else if (Integer.parseInt(cantPartos.getText().toString()) == 0)
                etFechaUltParto.setText("");
            return true;
        }
        etFechaUltParto.setText("");
        return false;
    }

    private boolean esValido(EditText editText) {
        if (editText.getText().toString().length()>0)
            return true;
        return false;
    }

    private void showDatePickerDialog(int p) {
        if (p == 1) {

            DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    // +1 because January is zero
                    final String selectedDate = day + "-" + (month + 1) + "-" + year;
                    etFechaNacimiento.setText(selectedDate);
                }
            });

            newFragment.show(getSupportFragmentManager(), "datePicker");
        } else {
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

    public void agregarVaca(){
        int cantPartos = Integer.parseInt(etCantidadPartos.getText().toString());
        int electronico = Integer.parseInt(etIdElectronico.getText().toString());
        String fechaNacimiento = null;
        String fechaUltParto = null;
        try {
            System.out.println("FECHA PARTO" + etFechaUltParto.getText().toString());
            if (!etFechaUltParto.getText().toString().equals(""))
                fechaUltParto = formatoFecha(etFechaUltParto.getText().toString());
            fechaNacimiento = formatoFecha(etFechaNacimiento.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final double peso = Double.valueOf((etIdPeso.getText().toString()));
        final int rodeo = Integer.valueOf((etIdRodeo.getText().toString()));

        Vaca vaca = new Vaca(cantPartos,electronico,fechaNacimiento,rodeo,peso,fechaUltParto);

        Call<Vaca> call = CowApiAdapter.getApiService().agregarVaca(vaca);
        call.enqueue(new Callback<Vaca>() {
            @Override
            public void onResponse(Call<Vaca> call, Response<Vaca> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Codigo " + response.code());
                    btnAgregarVaca.setText("Cargar Vaca");
                    btnAgregarVaca.setEnabled(true);
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                    return;
                }
                System.out.println("Codigo " + response.code());
                Vaca vacaResponse = response.body();
                infoId.setText("Id Vaca: " + String.valueOf(vacaResponse.getId()));
                btnAgregarVaca.setText("Cargar Vaca");
                btnAgregarVaca.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<Vaca> call, Throwable t) {
                btnAgregarVaca.setText("Cargar Vaca");
                btnAgregarVaca.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), ERROR_CONEXION, Toast.LENGTH_SHORT);
            }
        });
    }
        private String formatoFecha(String fecha) throws ParseException {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
            String formatoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(date);
            return formatoDate;
        }




}
