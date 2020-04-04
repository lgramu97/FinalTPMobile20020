package com.example.tpfinalmoviles.io.Response;

import com.google.gson.annotations.SerializedName;

public class Sesion {

    @SerializedName("enable")
    private boolean enable;

    public Sesion(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }
}
