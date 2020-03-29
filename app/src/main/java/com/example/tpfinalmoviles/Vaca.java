package com.example.tpfinalmoviles;

public class Vaca {
    private Integer id;
    private int cantidadPartos;
    private int electronicId;
    private String fechaNacimiento;
    private int herdId;
    private double peso;
    private String ultimaFechaParto;
    private String fechaBcs;
    private int cowBcsId;
    private Double cc;

    public Vaca(int cantidadPartos, int electronicId, String fechaNacimiento, int herdId, double peso, String ultimaFechaParto) {
        this.cantidadPartos = cantidadPartos;
        this.electronicId = electronicId;
        this.fechaNacimiento = fechaNacimiento;
        this.herdId = herdId;
        this.peso = peso;
        this.ultimaFechaParto = ultimaFechaParto;
    }

    public String getFechaBcs() {
        return fechaBcs;
    }

    public int getCowBcsId() {
        return cowBcsId;
    }

    public Double getCc() {
        return cc;
    }

    public int getId() {
        return id;
    }

    public int getCantidadPartos() {
        return cantidadPartos;
    }

    public int getElectronicId() {
        return electronicId;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getHerdId() {
        return herdId;
    }

    public double getPeso() {
        return peso;
    }

    public String getUltimaFechaParto() {
        return ultimaFechaParto;
    }
}
