package com.example.lbsapitest;

import java.util.ArrayList;

/**
 * Created by aa on 14-2-14.
 */
public class Area {

    private int id;
    private String name;
    private ArrayList<Location> locations;

    public Area(int id, String name, ArrayList<Location> locations) {
        this.id = id;
        this.name = name;
        this.locations = locations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }
}
