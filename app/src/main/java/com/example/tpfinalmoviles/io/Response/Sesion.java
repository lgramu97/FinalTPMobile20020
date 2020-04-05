package com.example.tpfinalmoviles.io.Response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sesion implements Serializable {

    @SerializedName("enable")
    private String enable;

    public Sesion(String enable) {
        this.enable = enable;
    }

    public String isEnable() {
        return enable;
    }
}
