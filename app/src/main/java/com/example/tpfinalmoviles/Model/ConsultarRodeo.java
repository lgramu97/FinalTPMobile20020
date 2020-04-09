package com.example.tpfinalmoviles.Model;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.Utils.CustomExpandableListAdapterVacas;
import com.example.tpfinalmoviles.Utils.ToastHandler;
import com.example.tpfinalmoviles.io.CowApiAdapter;
import com.example.tpfinalmoviles.io.Response.Rodeo;
import com.example.tpfinalmoviles.io.Response.Vaca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarRodeo extends AppCompatActivity {
    private static String ERROR_POST = "Error al consultar el rodeo";
    private static String CORRECT_POST = "Rodeo cargado con exito";
    private static String ERROR_CONECTION = "Error de conexión";

    private Button bConsultarRodeo;
    private Button bRegresar;
    private ScrollView scrollView;
    private EditText idRodeo;
    private TextView idLocation,promedioBCS;

    private ArrayList<Vaca> vacas;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private int lastExpandedPosition = -1;

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

        //Hay que controlar que cargue algo en idRodeo, para activar boton consultar.

        bConsultarRodeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (esValido(idRodeo)){
                    bConsultarRodeo.setText("Enviando Datos");
                    bConsultarRodeo.setEnabled(false);
                    consultarRodeo();
                }else
                    ToastHandler.get().showToast(getApplicationContext(), "IdRodeo Invalido", Toast.LENGTH_SHORT);

            }
        });
        bRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("idlocation",idLocation.getText().toString());
        outState.putString("promBSC",promedioBCS.getText().toString());
        outState.putInt("visivility",scrollView.getVisibility());
        System.out.println("VALOR VISIBLE" + scrollView.getVisibility());
        outState.putParcelableArrayList("vacaList",  (ArrayList<Vaca>) vacas);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        idLocation.setText(savedInstanceState.getString("idlocation"));
        promedioBCS.setText(savedInstanceState.getString("promBSC"));
        int estado = savedInstanceState.getInt("visivility");
        scrollView.setVisibility(estado);
        System.out.println("VALOR VISIBLE DESPUES " + estado);
        vacas = savedInstanceState.getParcelableArrayList("vacaList");
        if (vacas != null)
            init(vacas);
    }

    private boolean esValido(EditText editText) {
        if (editText.getText().toString().length()>0)
            return true;
        return false;
    }

    private void consultarRodeo() {
        final int idR = Integer.parseInt(idRodeo.getText().toString());
        Call<Rodeo> call = CowApiAdapter.getApiService().getRodeo(idR);
        call.enqueue(new Callback<Rodeo>() {
            @Override
            public void onResponse(Call<Rodeo> call, Response<Rodeo> response) {
                if (response.isSuccessful()) {
                    Rodeo rodeo = response.body();
                    idLocation.setText(String.valueOf(rodeo.getId()));
                    promedioBCS.setText(String.valueOf(rodeo.getBcsPromedio()));
                    vacas = rodeo.getCows();
                    System.out.println("VACAS aTRODEN " + vacas.size());
                    init(vacas);
                    scrollView.setVisibility(View.VISIBLE);
                    ToastHandler.get().showToast(getApplicationContext(), CORRECT_POST, Toast.LENGTH_SHORT);
                }else {
                    ToastHandler.get().showToast(getApplicationContext(), ERROR_POST, Toast.LENGTH_SHORT);
                }
                bConsultarRodeo.setText("Consultar Rodeo");
                bConsultarRodeo.setEnabled(true);
            }
            @Override
            public void onFailure(Call<Rodeo> call, Throwable t) {
                bConsultarRodeo.setText("Consultar Rodeo");
                bConsultarRodeo.setEnabled(true);
                ToastHandler.get().showToast(getApplicationContext(), ERROR_CONECTION, Toast.LENGTH_SHORT);
            }
        });
    }

    private void init(List<Vaca> vacas) {
        this.expandableListView = findViewById(R.id.elvList);
        HashMap<String, Vaca> listaVacas = getVacas(vacas);
        List<String> expandableListNombres = new ArrayList<>(listaVacas.keySet());
        this.expandableListAdapter = new CustomExpandableListAdapterVacas(this,
                expandableListNombres, listaVacas);
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
