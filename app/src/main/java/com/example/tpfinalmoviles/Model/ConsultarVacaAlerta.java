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
import com.example.tpfinalmoviles.Utils.CustomExpandableListAdapterAlertaVaca;
import com.example.tpfinalmoviles.Utils.ToastHandler;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.VacaAlertaFired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarVacaAlerta extends AppCompatActivity {

    private static String ERROR_POST = "Error al consultar alertas de vacas";
    private static String CORRECT_POST = "Alertas de vacas obtenidas con exito";
    private static String ERROR_CONECTION = "Error de conexión";

    private Button btnAgregarVaca, btnRegresarVaca;
    private LinearLayout lExpandable;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_vaca_alerta);
        btnAgregarVaca = findViewById(R.id.idConsultarVaca);
        btnRegresarVaca = findViewById(R.id.idbBackVaca);
        lExpandable = findViewById(R.id.lExpandable);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(MainActivity.NOTIFICACION_ID);
        btnAgregarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAgregarVaca.setText("Recibiendo datos");
                btnAgregarVaca.setEnabled(false);
                consultarAlertaVacas();
            }
        });
        btnRegresarVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void consultarAlertaVacas(){
        Call<List<VacaAlertaFired>> call = CowApiAdapter.getApiService().getCowFiredAlert();

        call.enqueue(new Callback<List<VacaAlertaFired>>() {
            @Override
            public void onResponse(Call<List<VacaAlertaFired>> call, Response<List<VacaAlertaFired>> response) {
                if(response.isSuccessful()){
                    List<VacaAlertaFired> vacaAlerta = response.body();
                    if(vacaAlerta.size()>0){
                        init(vacaAlerta);
                        lExpandable.setVisibility(View.VISIBLE);
                        ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                    }else
                        ToastHandler.get().showToast(getApplicationContext(), "No hay alertas que mostrar", Toast.LENGTH_SHORT);
                }else {
                ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
            }
                btnAgregarVaca.setText("Consultar Alertas Vacas");
                btnAgregarVaca.setEnabled(true);
            }

            @Override
            public void onFailure(Call<List<VacaAlertaFired>> call, Throwable t) {
                btnAgregarVaca.setText("Consultar Alertas Vacas");
                btnAgregarVaca.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), ERROR_CONECTION, Toast.LENGTH_SHORT);
            }
        });
    }

    private void init(List<VacaAlertaFired> alertas) {
        this.expandableListView = findViewById(R.id.elvList);
        HashMap<String, VacaAlertaFired> listaAlertas = getVacas(alertas);
        List<String> expandableListNombres = new ArrayList<>(listaAlertas.keySet());
        this.expandableListAdapter = new CustomExpandableListAdapterAlertaVaca(this,
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

    private HashMap<String, VacaAlertaFired> getVacas(List<VacaAlertaFired> alertas) {
        HashMap<String, VacaAlertaFired> listaV = new HashMap<>();
        for (VacaAlertaFired v:alertas){
            listaV.put("Alerta "+ v.getId(), v);
        }
        return listaV;
    }

}
