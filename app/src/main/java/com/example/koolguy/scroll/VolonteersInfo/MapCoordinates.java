package com.example.koolguy.scroll.VolonteersInfo;

public class MapCoordinates {
    String latlng;
    String name;
    String type;
    public MapCoordinates(String latlng, String name, String type) {
        super();
        this.latlng = latlng;
        this.name = name;
        this.type = type;
    }
    public String getLatlng() {
        return latlng;
    }
    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
