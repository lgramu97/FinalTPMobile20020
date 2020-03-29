package com.example.tpfinalmoviles.io.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rodeo {

    @SerializedName("id")
    private String id;

    @SerializedName("location")
    private String location;

    @SerializedName("bcsPromedio")
    private String bcsPromedio = null;

    @SerializedName("cows")
    private ArrayList<Vaca> cows = null;

    public ArrayList<Vaca> getCows() {
        return cows;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getBcsPromedio() {
        return bcsPromedio;
    }

    public void setCows(ArrayList<Vaca> cows) {
        this.cows = cows;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBcsPromedio(String bcsPromedio) {
        this.bcsPromedio = bcsPromedio;
    }
}
