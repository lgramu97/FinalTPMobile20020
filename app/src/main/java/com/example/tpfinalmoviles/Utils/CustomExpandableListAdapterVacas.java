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

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapterVacas extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listVacas;
    private HashMap<String, Vaca> expandableListDetalles;

    public CustomExpandableListAdapterVacas(Context context,
                                            List<String> listTitulo,
                                            HashMap<String, Vaca> expandableListDetalles) {
        this.context = context;
        this.listVacas = listTitulo;
        this.expandableListDetalles = expandableListDetalles;
    }


    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Vaca vaca = (Vaca) getChild(groupPosition, childPosition);

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_item, null);

        }

        TextView etFechaNacimiento = convertView.findViewById(R.id.etFechaNacimiento);
        TextView etPeso = convertView.findViewById(R.id.etPeso);
        TextView etCantPartos = convertView.findViewById(R.id.etCantPartos);
        TextView etFechaParto = convertView.findViewById(R.id.etFechaParto);
        TextView etIdBCS = convertView.findViewById(R.id.etIdBCS);
        TextView etFechaBCS = convertView.findViewById(R.id.etFechaBCS);
        TextView etCC = convertView.findViewById(R.id.etCC);
        TextView etIDElectronico = convertView.findViewById(R.id.etIdElectronico);
        LinearLayout lCowId = convertView.findViewById(R.id.lCowId);
        LinearLayout lbcsFired = convertView.findViewById(R.id.lbcsFired);
        LinearLayout lFechaAlerta = convertView.findViewById(R.id.lFechaAlerta);

        lCowId.setVisibility(View.GONE);
        lbcsFired.setVisibility(View.GONE);
        lFechaAlerta.setVisibility(View.GONE);

        etFechaNacimiento.setText((vaca.getFechaNacimiento()).substring(0,10));
        etIDElectronico.setText(String.valueOf(vaca.getElectronicId()));
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
            etFechaBCS.setText("--");
        else
            etFechaBCS.setText(vaca.getFechaBcs().substring(0,10));
        etIdBCS.setText(String.valueOf(vaca.getCowBcsId()));
        etCC.setText(String.valueOf(vaca.getCc()));

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
        return this.listVacas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listVacas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetalles.get(this.listVacas.get(groupPosition));
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