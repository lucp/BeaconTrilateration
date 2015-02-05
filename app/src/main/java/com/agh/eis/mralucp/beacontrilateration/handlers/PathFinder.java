package com.agh.eis.mralucp.beacontrilateration.handlers;

import com.agh.eis.mralucp.beacontrilateration.Trilateration;
import com.agh.eis.mralucp.beacontrilateration.model.Beacon;
import com.agh.eis.mralucp.beacontrilateration.model.CSVEntry;
import com.agh.eis.mralucp.beacontrilateration.model.Point;
import com.agh.eis.mralucp.beacontrilateration.model.Signal;

import java.util.LinkedList;

/**
 * Created by Lucp on 2015-01-29.
 */
public class PathFinder {

    public static Point findPoint(LinkedList<Beacon> beacons) {
        if (beacons != null && beacons.size() >= 3) {
            LinkedList<Signal> signals = new LinkedList<Signal>();
            for (Beacon beacon : beacons) {
                signals.add(new Signal(beacon, beacon.getCurrentSignal()));
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
            return Trilateration.evaluateCoordinates(xa, ya, xb, yb, xc, yc, ra, rb, rc);
        }
        else {
            return null;
        }
    }

    public static Point findPointFromThreeBeacons(LinkedList<Beacon> beacons) {
        if (beacons.size() == 3) {
            double xa = beacons.get(0).getPositionX();
            double ya = beacons.get(0).getPositionY();
            double ra = DistanceRSSIConverter.convertDistance(beacons.get(0).getCurrentSignal().getRssi());
            double xb = beacons.get(1).getPositionX();
            double yb = beacons.get(1).getPositionY();
            double rb = DistanceRSSIConverter.convertDistance(beacons.get(1).getCurrentSignal().getRssi());
            double xc = beacons.get(2).getPositionX();
            double yc = beacons.get(2).getPositionY();
            double rc = DistanceRSSIConverter.convertDistance(beacons.get(2).getCurrentSignal().getRssi());
            return Trilateration.evaluateCoordinates(xa, ya, xb, yb, xc, yc, ra, rb, rc);
        }
        else {
            return null;
        }
    }

    public static LinkedList<Point> findPath(LinkedList<Beacon> beacons, Beacon referenceBeacon) {
        LinkedList<Point> points = new LinkedList<Point>();
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
