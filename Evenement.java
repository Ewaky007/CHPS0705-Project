package com.kapture.sdk.model;

public class Evenement {
    private String type;
    private long horodatage;
    private Object data;

    public Evenement(String type, Object data) {
        this.type = type;
        this.horodatage = System.currentTimeMillis();
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public long getHorodatage() {
        return horodatage;
    }

    public Object getData() {
        return data;
    }
}
