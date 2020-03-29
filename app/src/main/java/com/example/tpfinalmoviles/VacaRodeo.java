package com.example.tpfinalmoviles;

import com.google.gson.annotations.SerializedName;

public class VacaRodeo {

    @SerializedName("electronicId")
    private String electronicId;

    @SerializedName("id")
    private String id;

    @SerializedName("fechaNacimiento")
    private String fechaNacimiento;

    @SerializedName("ultimaFechaParto")
    private String ultimaFechaParto;

    @SerializedName("cantidadPartos")
    private String cantidadPartos;

    @SerializedName("peso")
    private String peso;

    @SerializedName("herdId")
    private String herdId;

    @SerializedName("cowBcsId")
    private String cowBcsId;

    @SerializedName("fechaBcs")
    private String fechaBcs;

    @SerializedName("cc")
    private String cc;

    public String getElectronicId() {
        return electronicId;
    }

    public void setElectronicId(String electronicId) {
        this.electronicId = electronicId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUltimaFechaParto() {
        return ultimaFechaParto;
    }

    public void setUltimaFechaParto(String ultimaFechaParto) {
        this.ultimaFechaParto = ultimaFechaParto;
    }

    public String getCantidadPartos() {
        return cantidadPartos;
    }

    public void setCantidadPartos(String cantidadPartos) {
        this.cantidadPartos = cantidadPartos;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getHerdId() {
        return herdId;
    }

    public void setHerdId(String herdId) {
        this.herdId = herdId;
    }

    public String getCowBcsId() {
        return cowBcsId;
    }

    public void setCowBcsId(String cowBcsId) {
        this.cowBcsId = cowBcsId;
    }

    public String getFechaBcs() {
        return fechaBcs;
    }

    public void setFechaBcs(String fechaBcs) {
        this.fechaBcs = fechaBcs;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}
