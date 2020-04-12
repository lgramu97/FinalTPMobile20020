package com.example.tpfinalmoviles.Model;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.example.tpfinalmoviles.MainActivity;
import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.CustomExpandableListAdapterAlertaRodeo;
import com.example.tpfinalmoviles.Utils.ToastHandler;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.RodeoAlertaFired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarRodeoAlerta extends AppCompatActivity {

    private static String ERROR_POST = "Error al consultar alertas de rodeo";
    private static String CORRECT_POST = "Alertas de rodeo obtenidas con exito";
    private static String ERROR_CONECTION = "Error de conexión";

    private Button btnAgregarRodeo, btnRegresar;
    private LinearLayout lExpandable;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_rodeo_alerta);
        btnAgregarRodeo = findViewById(R.id.idConsultarRodeo);
        btnRegresar = findViewById(R.id.idbBackRodeo);
        lExpandable = findViewById(R.id.lExpandable);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(MainActivity.NOTIFICACION_ID);

        btnAgregarRodeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAgregarRodeo.setText("Recibiendo datos");
                btnAgregarRodeo.setEnabled(false);
                consultarAlertaRodeo();
                desplegarNotificacion();
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void consultarAlertaRodeo(){
        Call<List<RodeoAlertaFired>> call = CowApiAdapter.getApiService().getHerdFiredAlert();

        call.enqueue(new Callback<List<RodeoAlertaFired>>() {
            @Override
            public void onResponse(Call<List<RodeoAlertaFired>> call, Response<List<RodeoAlertaFired>> response) {
                if(response.isSuccessful()){
                    List<RodeoAlertaFired> rodeoAlerta = response.body();
                    if (rodeoAlerta.size()>0 ) {
                        init(rodeoAlerta);
                        lExpandable.setVisibility(View.VISIBLE);
                        ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                    }else
                        ToastHandler.get().showToast(getApplicationContext(), "No hay alertas que mostrar", Toast.LENGTH_SHORT);
                }else {
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                }
                btnAgregarRodeo.setText("Consultar Alertas Rodeo");
                btnAgregarRodeo.setEnabled(true);
            }

            @Override
            public void onFailure(Call<List<RodeoAlertaFired>> call, Throwable t) {
                btnAgregarRodeo.setText("Consultar Alertas Rodeo");
                btnAgregarRodeo.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), ERROR_CONECTION, Toast.LENGTH_SHORT);
            }
        });
    }

    private void init(List<RodeoAlertaFired> alertas) {
        this.expandableListView = findViewById(R.id.elvList);
        HashMap<String, RodeoAlertaFired> listaAlertas = getVacas(alertas);
        List<String> expandableListNombres = new ArrayList<>(listaAlertas.keySet());
        this.expandableListAdapter = new CustomExpandableListAdapterAlertaRodeo(this,
                expandableListNombres, listaAlertas);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    private HashMap<String, RodeoAlertaFired> getVacas(List<RodeoAlertaFired> alertas) {
        HashMap<String, RodeoAlertaFired> listaV = new HashMap<>();
        for (RodeoAlertaFired v:alertas){
            listaV.put("Alerta "+ v.getId(), v);
        }
        return listaV;
    }

    private void desplegarNotificacion(){

    }

}
