package com.example.tpfinalmoviles.io.Response;

import com.google.gson.annotations.SerializedName;

public class VacaAlertaFired {

    @SerializedName("id")
    private Integer id;

    @SerializedName("cow")
    private Vaca cow;

    @SerializedName("bcs_fired")
    private Double bcs_fired;

    @SerializedName("fecha")
    private String fecha;

    public Integer getId() {
        return id;
    }

    public Vaca getCow() {
        return cow;
    }

    public Double getBcs_fired() {
        return bcs_fired;
    }

    public String getFecha() {
        return fecha;
    }
}
