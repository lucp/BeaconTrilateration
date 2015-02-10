package com.agh.eis.mralucp.beacontrilateration;

import com.agh.eis.mralucp.beacontrilateration.model.Point;

/**
 * Created by Lucp on 2015-01-27.
 */

//komentarz

public class Trilateration {

    /***
     *
     * @param xa - x coordinates of A
     * @param ya - y coordinates of A
     * @param xb - x coordinates of B
     * @param yb - y coordinates of B
     * @param xc - x coordinates of C
     * @param yc - y coordinates of C
     * @param ra - radius of A
     * @param rb - radius of B
     * @param rc - radius of C
     * @return coordinates of point
     */
    public static Point evaluateCoordinates(double xa,double ya, double xb, double yb, double xc, double yc, double ra, double rb, double rc){

        double va = (double)((rb * rb - rc * rc) - (xb * xb - xc * xc) - (yb * yb - yc * yc)) / 2.0;
        double vb = (double)((rb * rb - ra * ra) - (xb * xb - xa * xa) - (yb * yb - ya * ya)) / 2.0;

        double y = (vb * (xc - xb) - va * (xa - xb)) / ((ya - yb) * (xc - xb) - (yc - yb) * (xa - xb));
        double x;
        if((xc - xb)!=0){
            x = (va - y * (yc - yb)) / (xc - xb);
        }
        else{
            x = (vb - y * (ya - yb)) / (xa - xb);
        }

        return new Point(x,y);
    }

}
