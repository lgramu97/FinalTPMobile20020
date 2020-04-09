package com.example.tpfinalmoviles.io.Response;

import com.google.gson.annotations.SerializedName;

public class RodeoAlertaFired {
    @SerializedName("id")
    private Integer id;

    @SerializedName("herd")
    private Rodeo herd;

    @SerializedName("bcs_fired")
    private Double bcs_fired;

    @SerializedName("fecha")
    private String fecha;

    public Integer getId() {
        return id;
    }

    public Rodeo getHerd() {
        return herd;
    }

    public Double getBcs_fired() {
        return bcs_fired;
    }

    public String getFecha() {
        return fecha;
    }

}
