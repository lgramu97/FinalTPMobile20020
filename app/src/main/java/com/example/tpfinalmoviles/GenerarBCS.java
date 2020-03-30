package com.example.tpfinalmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tpfinalmoviles.Utils.ConfigOkHttp;
import com.example.tpfinalmoviles.Utils.ConfigServer;
import com.example.tpfinalmoviles.Utils.ToastHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GenerarBCS extends AppCompatActivity {

    private Button bConsultarVaca;
    private Button bRegresa;
    private Tarea tarea;
    private Switch simpleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_bcs);
        bRegresa = (Button) findViewById(R.id.bBack);
        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);

        tarea = new Tarea();
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean b = simpleSwitch.isChecked();
                tarea.execute(simpleSwitch.isChecked());
                System.out.println(b);
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

    private class Tarea extends AsyncTask<Boolean,Void,Void> {

        @Override
        protected Void doInBackground(Boolean... activo) {
            String url = getSharedPreferences(ConfigServer.URL_DETAILS,MODE_PRIVATE).getString("url","")+"api/session/";
            ConfigOkHttp peticion = new ConfigOkHttp();
            final JSONObject jsonRodeo = new JSONObject();
            try {
                    jsonRodeo.put("enable", activo[0]);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final Callback callback = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error");
                    ToastHandler.get().showToast(getApplicationContext(), "error", Toast.LENGTH_SHORT);

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    final String mMessage = response.body().string();
                    System.out.println("a " + mMessage);
                    ToastHandler.get().showToast(getApplicationContext(), "PERFECT", Toast.LENGTH_SHORT);
                }
            };
            peticion.post(url,jsonRodeo,callback);
            return null;
        }
    }

}
