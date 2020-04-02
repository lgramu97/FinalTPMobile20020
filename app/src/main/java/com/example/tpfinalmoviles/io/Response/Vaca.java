package com.example.tpfinalmoviles.io.Response;

import android.os.Parcel;
import android.os.Parcelable;

public class Vaca implements Parcelable {
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

    protected Vaca(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        cantidadPartos = in.readInt();
        electronicId = in.readInt();
        fechaNacimiento = in.readString();
        herdId = in.readInt();
        peso = in.readDouble();
        ultimaFechaParto = in.readString();
        fechaBcs = in.readString();
        cowBcsId = in.readInt();
        if (in.readByte() == 0) {
            cc = null;
        } else {
            cc = in.readDouble();
        }
    }

    public static final Creator<Vaca> CREATOR = new Creator<Vaca>() {
        @Override
        public Vaca createFromParcel(Parcel in) {
            return new Vaca(in);
        }

        @Override
        public Vaca[] newArray(int size) {
            return new Vaca[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeInt(cantidadPartos);
        dest.writeInt(electronicId);
        dest.writeString(fechaNacimiento);
        dest.writeInt(herdId);
        dest.writeDouble(peso);
        dest.writeString(ultimaFechaParto);
        dest.writeString(fechaBcs);
        dest.writeInt(cowBcsId);
        if (cc == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(cc);
        }
    }
}
