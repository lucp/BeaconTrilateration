package com.agh.eis.mralucp.beacontrilateration;

/**
 * Created by Lucp on 2015-01-27.
 */
//komentarz

public class DistanceRSSIConverter {

    private static double A = -60;
    private static double n = 2.2;

    public static double convertDistance(int rssi){
        double dis = Math.pow(10, ((A-(double)rssi)/(double)(10*n)));
        return dis;
    }


}
