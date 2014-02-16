package com.example.lbsapitest;


import java.io.Serializable;

public class Location implements Serializable {

    private double latitude;
    private double longitude;
    private int id;
    private int category_id;
    private String name;
    //private ArrayList<Task> tasks;
    private LocationCategory category;

    private int area_id;
    private point intialp;
    private point range;


    public Location(int id, int category_id, String name) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        //this.tasks = tasks;
    }

    public Location(int id, String name, int category_id, int area_id, point intialp, point range) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
        this.area_id = area_id;
        this.intialp = intialp;
        this.range = range;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public point getIntialp() {
        return intialp;
    }

    public void setIntialp(point intialp) {
        this.intialp = intialp;
    }

    public point getRange() {
        return range;
    }

    public void setRange(point range) {
        this.range = range;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public ArrayList<Task> getTasks() {
        if( this.tasks == null ) this.tasks = new ArrayList<Task>();
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
*/
    public LocationCategory getCategory() {
        return category;
    }

    public void setCategory(LocationCategory category) {
        this.category = category;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }


    public class point{
        float x;
        float y;
    }

}

