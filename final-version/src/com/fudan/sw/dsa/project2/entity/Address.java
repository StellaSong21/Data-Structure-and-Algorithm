package com.fudan.sw.dsa.project2.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * For each station of subway
 * If you need other attribute, add it
 *
 * @author zjiehang
 */
@JsonAutoDetect
public class Address {
    @JsonProperty
    private String address;
    private double longitude;//经度
    private double latitude;//纬度
    private double time;
    private int times;
    public int color = 1;//-1到0  1-->2 test
    public Address next;
    public boolean reached = false;
    public String line;

    @JsonIgnore
    private Address pi;
    @JsonIgnore
    private ArrayList<Address> predecessor;

    /**
     * constructor for Address
     *
     * @param address:   name
     * @param longitude: longitude
     * @param latitude:  latitude
     */
    public Address(String address, String longitude, String latitude) {
        this.address = address;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public ArrayList<Address> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(ArrayList<Address> predecessor) {
        this.predecessor = predecessor;
    }

    public Address getPi() {
        return pi;
    }

    public void setPi(Address pi) {
        this.pi = pi;
    }
}
