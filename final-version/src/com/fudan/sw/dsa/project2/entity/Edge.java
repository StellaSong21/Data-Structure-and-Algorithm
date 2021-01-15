package com.fudan.sw.dsa.project2.entity;

public class Edge {
    private String start;
    private String end;
    private String line;
    private double weight;

    public Edge(String line, String start, String end, double weight) {
        this.line = line;
        this.start = start;
        this.end = end;
        this.weight = weight;
    }


    public double getWeight() {
        return weight;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getLine() {
        return line;
    }
}
