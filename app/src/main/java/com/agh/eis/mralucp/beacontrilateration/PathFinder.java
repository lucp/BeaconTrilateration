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
            LinkedList<Pair<Beacon, CSVEntry>> signals = new LinkedList<Pair<Beacon, CSVEntry>>();
            signals.add(new Pair<Beacon, CSVEntry>(referenceBeacon, csvEntry));
            for (Beacon beacon : beacons) {
                signals.add(BeaconHandler.getClosestTimestampBeacon(csvEntry, beacon));
            }
            signals = BeaconHandler.getThreeBestRSSIBeacons(signals);
            double xa = signals.get(0).first.getPositionX();
            double ya = signals.get(0).first.getPositionY();
            double ra = DistanceRSSIConverter.convertDistance(signals.get(0).second.getRssi());
            double xb = signals.get(1).first.getPositionX();
            double yb = signals.get(1).first.getPositionY();
            double rb = DistanceRSSIConverter.convertDistance(signals.get(1).second.getRssi());
            double xc = signals.get(2).first.getPositionX();
            double yc = signals.get(2).first.getPositionY();
            double rc = DistanceRSSIConverter.convertDistance(signals.get(2).second.getRssi());
            points.add(Trilateration.evaluateCoordinates(xa, ya, xb, yb, xc, yc, ra, rb, rc));
        }
        return points;
    }

}
