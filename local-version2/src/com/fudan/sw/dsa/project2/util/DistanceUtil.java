package com.fudan.sw.dsa.project2.util;

import com.fudan.sw.dsa.project2.entity.Address;

public class DistanceUtil {
    private static double EARTH_RADIUS = 6378.137;

    /**
     * @param start：Address start
     * @param end:          Address end
     * @return distance (m)
     */
    public static double getDistance(Address start, Address end) {
        double radLat1 = rad(start.getLatitude());
        double radLat2 = rad(end.getLatitude());
        double a = radLat1 - radLat2;//纬度差
        double b = rad(start.getLongitude()) - rad(end.getLongitude());//经度差
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000d;
        s = s * 1000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static void main(String ar[]) {
        System.out.println(getDistance(new Address("合肥", "121.444", "31.2337"), new Address("杭州", "115.4521", "30.48602")));
    }
}
