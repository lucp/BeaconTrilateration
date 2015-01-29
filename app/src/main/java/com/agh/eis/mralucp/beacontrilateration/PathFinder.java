package com.agh.eis.mralucp.beacontrilateration;

import android.util.Pair;

import java.util.LinkedList;

/**
 * Created by Lucp on 2015-01-29.
 */
public class PathFinder {

    public static LinkedList<Trilateration.Point> findPath(LinkedList<Beacon> beacons, Beacon referenceBeacon) {
        LinkedList<Trilateration.Point> points = new LinkedList<Trilateration.Point>();
        for (CSVEntry csvEntry : referenceBeacon.getSignalHistory()) {
            LinkedList<Signal> signals = new LinkedList<Signal>();
            signals.add(new Signal(referenceBeacon, csvEntry));
            for (Beacon beacon : beacons) {
                signals.add(BeaconHandler.getClosestTimestampBeacon(csvEntry, beacon));
            }
            signals = BeaconHandler.getThreeBestRSSIBeacons(signals);
            double xa = signals.get(0).getBeacon().getPositionX();
            double ya = signals.get(0).getBeacon().getPositionY();
            double ra = DistanceRSSIConverter.convertDistance(signals.get(0).getEntry().getRssi());
            double xb = signals.get(1).getBeacon().getPositionX();
            double yb = signals.get(1).getBeacon().getPositionY();
            double rb = DistanceRSSIConverter.convertDistance(signals.get(1).getEntry().getRssi());
            double xc = signals.get(2).getBeacon().getPositionX();
            double yc = signals.get(2).getBeacon().getPositionY();
            double rc = DistanceRSSIConverter.convertDistance(signals.get(2).getEntry().getRssi());
            points.add(Trilateration.evaluateCoordinates(xa, ya, xb, yb, xc, yc, ra, rb, rc));
        }
        return points;
    }

}
