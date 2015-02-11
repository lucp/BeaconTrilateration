package com.agh.eis.mralucp.beacontrilateration.handlers;

/**
 * Created by Lucp on 2015-01-27.
 */

/**
 * Converts RSSI to distance
 */
public class DistanceRSSIConverter {

    public static final double A = -70;
    public static final double n = 2.2;

    public static double convertDistance(int rssi){
        double dis = Math.pow(10, ((A-(double)rssi)/(10*n)));
        return dis;
    }

    public static double convertDistance(double rssi){
        double dis = Math.pow(10, ((A-(double)rssi)/(10*n)));
        return dis;
    }
}
