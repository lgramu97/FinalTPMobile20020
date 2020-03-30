package com.example.tpfinalmoviles.io.Response;

public class VacaAlerta {
    private int cowId;
    private double bcsThresholdMax;
    private double bcsThresholdMin;

    public VacaAlerta(int cowId, double bcsThresholdMax, double bcsThresholdMin) {
        this.cowId = cowId;
        this.bcsThresholdMax = bcsThresholdMax;
        this.bcsThresholdMin = bcsThresholdMin;
    }

    public int getCowId() {
        return cowId;
    }

    public double getBcsThresholdMax() {
        return bcsThresholdMax;
    }

    public double getBcsThresholdMin() {
        return bcsThresholdMin;
    }
}
