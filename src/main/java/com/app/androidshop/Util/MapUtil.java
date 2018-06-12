package com.app.androidshop.Util;

import com.app.androidshop.model.LatLng;

import java.text.NumberFormat;

public class MapUtil {

    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double getLatLngDistance(LatLng latLng1, LatLng latLng2) {

        double radLat1 = rad(latLng1.getLat().doubleValue());
        double radLat2 = rad(latLng2.getLat().doubleValue());
        double a = radLat1 - radLat2;
        double b = rad(latLng1.getLng().doubleValue()) - rad(latLng2.getLng().doubleValue());
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
