package com.agh.eis.mralucp.beacontrilateration.model;

/**
 * Created by Lucp on 2015-02-04.
 */
public class Point {

    public double x;
    public double y;

    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return new String("[" + this.x + "," + this.y + "]");
    }
}

