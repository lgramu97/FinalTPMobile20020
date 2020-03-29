package com.example.tpfinalmoviles.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.Model.MainActivity;
import com.example.tpfinalmoviles.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ConfigServer extends AppCompatActivity {

    public static final String NOMBRE_ARCHIVO = "configServer.txt";
    public static final String EDITAR ="EDITAR IP/PORT";
    public static final  String CARGAR = "CARGAR DATOS";
    public static final String CARGA_EXITO ="Datos correctamente cargados";
    public static final String CARGA_ERROR ="Archivo no encontrado";
    public static final String GUARDADO_EXITO ="Datos correctamente almacenados";
    public static final String GUARDADO_ERROR ="No fue posible almacenar los datos";
    public static final String URL_DETAILS = "direccion url";
    public static String url;

    private EditText etIP;
    private EditText etPort;
    private TextView tModel;
    private Button bConect;
    private Button bEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_server);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        tModel = findViewById(R.id.tModel);
        bConect = findViewById(R.id.bConect);
        bEdit = findViewById(R.id.bEdit);
        tModel.setText(getDeviceName());

        loadConfiguration();

        //bind button actions
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bEdit.getText().equals(EDITAR)) {
                    bEdit.setText(CARGAR);
                    etIP.setEnabled(true);
                    etPort.setEnabled(true);
                    bConect.setEnabled(false);
                } else {
                    cargarData();
                    saveConfiguration();
                }
                System.out.println("URL " + url);
            }
        });

        bConect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferece();
                Intent i;
                i = new Intent(ConfigServer.this, MainActivity.class);
                startActivity(i);;
                System.out.println("URL " + url);
            }
        });

        System.out.println("URL " + url);
    }

    private void loadConfiguration() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            InputStreamReader input = new InputStreamReader(openFileInput(NOMBRE_ARCHIVO));
            BufferedReader buffer = new BufferedReader(input);
            String line = null;
            if ( (line = buffer.readLine()) != null) {
                etIP.setText(line);
                etPort.setText(buffer.readLine());
                cargarData();
            }
            buffer.close();
            input.close();
            Toast.makeText(this,CARGA_EXITO,Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            bConect.setEnabled(false);
            Toast.makeText(this,CARGA_ERROR,Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveConfiguration(){
        String ip =  etIP.getText().toString();
        String port = etPort.getText().toString();
        try {
            File sd = Environment.getExternalStorageDirectory();
            OutputStreamWriter create = new OutputStreamWriter((openFileOutput(NOMBRE_ARCHIVO, Activity.MODE_PRIVATE)));
            String content = ip + "\n" + port + "\n";
            create.write(content);
            create.close();
            Toast.makeText(this,GUARDADO_EXITO + sd.getPath(),Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,GUARDADO_ERROR,Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSharedPreferece(){
        SharedPreferences urlDetails = getSharedPreferences(URL_DETAILS, MODE_PRIVATE);
        SharedPreferences.Editor edit = urlDetails.edit();
        edit.putString("url", url.trim());
        edit.apply();
    }

    private void cargarData(){
        url = String.format("http://%s:%s/", etIP.getText(), etPort.getText());
        bEdit.setText(EDITAR);
        etIP.setEnabled(false);
        etPort.setEnabled(false);
        bConect.setEnabled(true);
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

}
