/*
 * ISCG7424 – Mobile Software Development
 * Assignment 3: Garage Sales Finder
 * Parts: All
 * Student: Paul Shalley		(ID:1402195)
 * Student: Renato De Mendonca	(ID:1422497)
 * Student: Sergey Seriakov 	(ID:1405156)
 * Teacher: Dr. John Casey
 * 2014.
 */

package com.brunz.garagesalefinder;

import com.google.android.gms.maps.model.LatLng;

public class DistanceTool {
    // For calculating distance
    private static final double PIx = 3.141592653589793;
    private static final double RADIO = 6378.16; // Radius of the earth, in km


    private static double radians(double x) {
        return x * PIx / 180;
    }

    /**
     * Returns distance in kilometers between point1 and point2.
     * As seen here: http://stackoverflow.com/questions/27928/how-do-i-calculate-distance-between-two-latitude-longitude-points
     */
    public static Double distanceBetweenPlaces(LatLng point1, LatLng point2) {
        double lon1 = point1.longitude;//getLongitudeE6() / 1E6;
        double lat1 = point1.latitude;//getLatitudeE6() / 1E6;
        double lon2 = point2.longitude;//getLongitudeE6() / 1E6;
        double lat2 = point2.latitude;//getLatitudeE6() / 1E6;
        double dlon = radians(lon2 - lon1);
        double dlat = radians(lat2 - lat1);

        double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2))
                + Math.cos(radians(lat1)) * Math.cos(radians(lat2))
                * (Math.sin(dlon / 2) * Math.sin(dlon / 2));
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (Double) angle * RADIO;
    }

}
