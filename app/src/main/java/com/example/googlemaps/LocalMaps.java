package com.example.googlemaps;

public class LocalMaps {
    private String local;
    private Double latitude;
    private Double longitude;

    public LocalMaps(String local, Double latitude, Double longitude) {
        this.local = local;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocalMaps{" +
                "local='" + local + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
