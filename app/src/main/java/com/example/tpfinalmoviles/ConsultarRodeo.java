package com.example.tpfinalmoviles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ConsultarRodeo extends AppCompatActivity {
    private Button bConsultarRodeo;
    private Button bRegresar;
    private ScrollView scrollView;
    private EditText idRodeo;
    private TextView idLocation,promedioBCS;
    private Tarea tarea;
    private String sms;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListNombres;
    private HashMap<String, Vaca> listaVacas;
    private int lastExpandedPosition = -1;
    private JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_rodeo);
        bConsultarRodeo = (Button) findViewById(R.id.bConsultar);
        bRegresar = (Button) findViewById(R.id.bBack);
        scrollView = findViewById(R.id.idScrollView);
        idRodeo = findViewById(R.id.idRodeoB);
        idLocation = findViewById(R.id.etIdLocation);
        promedioBCS = findViewById(R.id.etBCSPromedio);
        bConsultarRodeo.setOnClickListener(new View.OnClickListener() {
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
            final Long idR = Long.parseLong(idRodeo.getText().toString());
            System.out.println("errssor" + idR );
            String url = getSharedPreferences(ConfigServer.URL_DETAILS, MODE_PRIVATE).getString("url", "") + "api/herd/" + idR + "/";
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
                                idLocation.setText(json.getString("location"));
                                promedioBCS.setText(json.getString("bcsPromedio"));
                                array = json.getJSONArray("cows");
                                if ( array != null) {
                                    System.out.println("IMPRIMIENDO ARRAY " + array.toString());
                                    final Gson gson = new Gson();
                                    final Type tipoListaVaca = new TypeToken<List<Vaca>>(){}.getType();
                                    final List<Vaca> vacas = gson.fromJson(array.toString(), tipoListaVaca);
                                    init(vacas);
                                    expandableListView.setAdapter(expandableListAdapter);
                                    expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                                        @Override
                                        public void onGroupExpand(int groupPosition) {
                                            System.out.println("ENTROOO");
                                            if(lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
                                                expandableListView.collapseGroup(lastExpandedPosition);
                                            }
                                            lastExpandedPosition = groupPosition;
                                        }
                                    });

                                }else
                                    System.out.println("ES NULL");
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

    private void init(List<Vaca> vacas) {
        this.expandableListView = findViewById(R.id.elvList);
        this.listaVacas = getVacas(vacas);
        this.expandableListNombres = new ArrayList<>(listaVacas.keySet());
        this.expandableListAdapter = new CustomExpandableListAdapter(this,
                expandableListNombres, listaVacas);

    }

    private HashMap<String, Vaca> getVacas(List<Vaca> vacas) {
        HashMap<String, Vaca> listaV = new HashMap<>();
        System.out.println("SIZE " + vacas.size());
        for (Vaca v:vacas){
            listaV.put("Vaca "+ v.getId(), v);
        }
        System.out.println("SIZE2 " + listaV.size());
        return listaV;
    }
}
