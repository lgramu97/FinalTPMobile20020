package com.example.tpfinalmoviles.Model;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.ConfigOkHttp;
import com.example.tpfinalmoviles.Utils.ConfigServer;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ConsultarVaca extends AppCompatActivity {
    private Button bConsultarVaca;
    private Button bRegresar;
    private EditText idVaca;
    private ScrollView scrollView;
    private TextView electronicoView,manadaView,fechaNacimientoView,pesoView,cantPartosView,fechaUltParto,bscView,fechaBscView,ccView;
    private Tarea tarea;
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
        bscView = (TextView) findViewById(R.id.etIdBSC);
        fechaBscView = (TextView) findViewById(R.id.etFechaBSC);
        ccView = (TextView) findViewById(R.id.etCC);
        bConsultarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.VISIBLE);
                tarea = new Tarea();
                tarea.execute();
            }
        });
        bRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class Tarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            final Long idV = Long.parseLong(idVaca.getText().toString());
            System.out.println("errssor" + idV );
            String url = getSharedPreferences(ConfigServer.URL_DETAILS, MODE_PRIVATE).getString("url", "") + "api/cow/" + idV + "/";
            System.out.println("err: " + url );
            ConfigOkHttp peticion = new ConfigOkHttp();
            System.out.println("error");
            final Callback callback = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error");

                    //ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                    //runOnUiThread(new Runnable() {
                    //   @Override
                    //   public void run() {
                    //        bCargar.setText("Cargar Rodeo");
                    //        bCargar.setEnabled(true);
                    //   }
                    //});
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    final String mMessage = response.body().string();
                    // mMessage = response.body().string();
                    System.out.println("a " + mMessage);

                    //   ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject json = null;
                            try {
                                json = new JSONObject(mMessage);
                                electronicoView.setText(json.getString("electronicId"));
                                manadaView.setText(json.getString("herdId"));
                                fechaNacimientoView.setText(json.getString("fechaNacimiento"));
                                pesoView.setText(json.getString("peso"));
                                cantPartosView.setText(json.getString("cantidadPartos"));
                                if (json.getString("ultimaFechaParto").equals("null"))
                                    fechaUltParto.setText("--");
                                else
                                    fechaUltParto.setText(json.getString("ultimaFechaParto"));
                                if (json.getString("fechaBcs").equals("null"))
                                    fechaBscView.setText("--");
                                else
                                    fechaBscView.setText(json.getString("fechaBcs"));
                                bscView.setText(json.getString("cowBcsId"));
                                ccView.setText(json.getString("cc"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                        }

            };
            System.out.println("Paso1");
            peticion.get(url, callback);
            return null;
        }
    }
}
