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
import com.example.tpfinalmoviles.io.Response.Rodeo;
import com.example.tpfinalmoviles.io.Response.RodeoAlertaFired;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapterAlertaRodeo extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listAlertas;
    private HashMap<String, RodeoAlertaFired> expandableListDetalles;

    public CustomExpandableListAdapterAlertaRodeo(Context context,
                                                 List<String> listTitulo,
                                                 HashMap<String, RodeoAlertaFired> expandableListDetalles) {
        this.context = context;
        this.listAlertas = listTitulo;
        this.expandableListDetalles = expandableListDetalles;
    }


    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final RodeoAlertaFired alertaFired = (RodeoAlertaFired) getChild(groupPosition, childPosition);
        Rodeo rodeo = alertaFired.getHerd();

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_item, null);

        }


        TextView etHerdId = convertView.findViewById(R.id.etIdRodeo);
        TextView etBcsFired = convertView.findViewById(R.id.etBcsFired);
        TextView etFechaAlerta = convertView.findViewById(R.id.etFechaAlerta);
        TextView etLocalidad = convertView.findViewById(R.id.etIdLocation);

        LinearLayout lVacaInfo = convertView.findViewById(R.id.lVacaInfo);
        LinearLayout lRodeoInfo = convertView.findViewById(R.id.lRodeoInfo);
        LinearLayout lAlertasFired = convertView.findViewById(R.id.lAlertasFired);
        LinearLayout lBCSPromedio = convertView.findViewById(R.id.lBCSPromedio);
        LinearLayout lCowId = convertView.findViewById(R.id.lCowId);

        lVacaInfo.setVisibility(View.GONE);
        lBCSPromedio.setVisibility(View.GONE);
        lCowId.setVisibility(View.GONE);
        lRodeoInfo.setVisibility(View.VISIBLE);
        lAlertasFired.setVisibility(View.VISIBLE);

        etHerdId.setText(String.valueOf(rodeo.getId()));
        etBcsFired.setText(String.valueOf(alertaFired.getBcs_fired()));
        etFechaAlerta.setText(alertaFired.getFecha().substring(0,10));
        etLocalidad.setText(rodeo.getLocation());
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