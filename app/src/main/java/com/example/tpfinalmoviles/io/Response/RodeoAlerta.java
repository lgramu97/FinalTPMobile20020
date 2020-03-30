package com.example.tpfinalmoviles.io.Response;

public class RodeoAlerta {
    private int id;
    private int herdId;
    private double bcsThresholdMax;
    private double bcsThresholdMin;

    public RodeoAlerta(int herdId, double bcsThresholdMax, double bcsThresholdMin) {
        this.herdId = herdId;
        this.bcsThresholdMax = bcsThresholdMax;
        this.bcsThresholdMin = bcsThresholdMin;
    }

    public int getId() {
        return id;
    }

    public int getHerdId() {
        return herdId;
    }

    public double getBcsThresholdMax() {
        return bcsThresholdMax;
    }

    public double getBcsThresholdMin() {
        return bcsThresholdMin;
    }
}
