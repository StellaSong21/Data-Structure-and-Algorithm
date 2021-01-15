package com.fudan.sw.dsa.project2.entity;

import java.util.ArrayList;

public class Vertex {
    private String name;//站名
    private double longitude;//经度
    private double latitude;//纬度
    private ArrayList<String> line;//地铁线
    //int length;//到达该点的长度，步行长度
    public double time;//到达该点花费的时间
    public int times;//到达该点所需的换乘次数
    public Vertex pi;
    public ArrayList<Vertex> pre;

    public Vertex(String name, double time) {
        this.name = name;
        this.time = time;
    }

    public Vertex(String name, double longitude, double latitude, String line) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.line = new ArrayList<>();
        this.line.add(line);
    }

    public Vertex(String name, double longitude, double latitude, ArrayList<String> line, ArrayList<Vertex> pre) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.line = line;
        this.pre = pre;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public ArrayList<String> getLine() {
        return line;
    }

    void addLine(String line) {
        if (!this.line.contains(line))
            this.line.add(line);
    }
}
