package com.fudan.sw.dsa.project2.entity;

import java.util.List;

public class ReturnValue {
    Address startPoint;
    List<Address> subwayList;
    Address endPoint;
    double minutes;
    double lengths = 0;
    long queryTime;

    public Address getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Address startPoint) {
        this.startPoint = startPoint;
    }

    public List<Address> getSubwayList() {
        return subwayList;
    }

    public void setSubwayList(List<Address> subwayList) {
        this.subwayList = subwayList;
    }

    public Address getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Address endPoint) {
        this.endPoint = endPoint;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    public double getLengths() {
        return lengths;
    }

    public void setLengths(double length) {
        this.lengths = length;
    }

    public long getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(long queryTime) {
        this.queryTime = queryTime;
    }
}
