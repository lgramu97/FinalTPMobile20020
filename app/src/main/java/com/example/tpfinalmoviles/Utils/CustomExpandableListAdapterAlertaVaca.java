package com.example.tpfinalmoviles.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tpfinalmoviles.R;
import com.example.tpfinalmoviles.io.Response.Vaca;
import com.example.tpfinalmoviles.io.Response.VacaAlertaFired;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapterAlertaVaca extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listAlertas;
    private HashMap<String, VacaAlertaFired> expandableListDetalles;

    public CustomExpandableListAdapterAlertaVaca(Context context,
                                            List<String> listTitulo,
                                            HashMap<String, VacaAlertaFired> expandableListDetalles) {
        this.context = context;
        this.listAlertas = listTitulo;
        this.expandableListDetalles = expandableListDetalles;
    }


    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final VacaAlertaFired alertaFired = (VacaAlertaFired) getChild(groupPosition, childPosition);
        Vaca vaca = alertaFired.getCow();
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_item, null);

        }

        TextView etFechaNacimiento = convertView.findViewById(R.id.etFechaNacimiento);
        TextView etPeso = convertView.findViewById(R.id.etPeso);
        TextView etCantPartos = convertView.findViewById(R.id.etCantPartos);
        TextView etFechaParto = convertView.findViewById(R.id.etFechaParto);
        TextView etIdBSC = convertView.findViewById(R.id.etIdBCS);
        TextView etFechaBSC = convertView.findViewById(R.id.etFechaBCS);
        TextView etIDElectronico = convertView.findViewById(R.id.etIdElectronico);
        TextView etCC = convertView.findViewById(R.id.etCC);
        TextView etCowId = convertView.findViewById(R.id.etCowId);
        TextView etBcsFired = convertView.findViewById(R.id.etBcsFired);
        TextView etFechaAlerta = convertView.findViewById(R.id.etFechaAlerta);

        LinearLayout lAlertasFired = convertView.findViewById(R.id.lAlertasFired);
        LinearLayout lIdBSC = convertView.findViewById(R.id.lIdBSC);
        LinearLayout lFechaBSC = convertView.findViewById(R.id.lFechaBSC);
        LinearLayout lCC = convertView.findViewById(R.id.lCC);
        lAlertasFired.setVisibility(View.VISIBLE);
        lIdBSC.setVisibility(View.GONE);
        lFechaBSC.setVisibility(View.GONE);
        lCC.setVisibility(View.GONE);
        etFechaNacimiento.setText((vaca.getFechaNacimiento()).substring(0,10));
        etPeso.setText(String.valueOf(vaca.getPeso()));
        etCantPartos.setText(String.valueOf(vaca.getCantidadPartos()));
        System.out.println("CATIDAD PARTOS" + (vaca.getCantidadPartos()));
        System.out.println("COWID " + (vaca.getCowBcsId()));
        //  System.out.println("VERDAD : " + vaca.getCantidadPartos().equals(0));
        if (vaca.getCantidadPartos() == 0)
            etFechaParto.setText("--");
        else{
            etFechaParto.setText(vaca.getUltimaFechaParto().substring(0,10));
        }
        if( vaca.getCowBcsId() == 0 )
            etFechaBSC.setText("--");
        else
            etFechaBSC.setText(vaca.getFechaBcs().substring(0,10));
        etIDElectronico.setText(String.valueOf(vaca.getElectronicId()));
        etIdBSC.setText(String.valueOf(vaca.getCowBcsId()));
        etCC.setText(String.valueOf(vaca.getCc()));
        etCowId.setText(String.valueOf(vaca.getId()));
        etBcsFired.setText(String.valueOf(alertaFired.getBcs_fired()));
        etFechaAlerta.setText(alertaFired.getFecha().substring(0,10));
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        convertView.startAnimation(animation);
        return convertView;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        String nombre = (String) getGroup(groupPosition);

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_group, null);

        }

        TextView etNombre = convertView.findViewById(R.id.etVaca);

        etNombre.setText(nombre);
        return convertView;
    }


    @Override
    public int getGroupCount() {
        return this.listAlertas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listAlertas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetalles.get(this.listAlertas.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}