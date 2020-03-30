package com.example.tpfinalmoviles.io.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rodeo {

    private int id;

    private String location;

    private double bcsPromedio;

    @SerializedName("cows")
    private ArrayList<Vaca> cows = null;

    public ArrayList<Vaca> getCows() {
        return cows;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Rodeo(String location) {
        this.location = location;
    }

    public double getBcsPromedio() {
        return bcsPromedio;
    }

    public void setCows(ArrayList<Vaca> cows) {
        this.cows = cows;
    }

}
