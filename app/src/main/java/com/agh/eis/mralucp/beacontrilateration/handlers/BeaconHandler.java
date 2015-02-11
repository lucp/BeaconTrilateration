package com.agh.eis.mralucp.beacontrilateration.handlers;

import com.agh.eis.mralucp.beacontrilateration.model.Beacon;
import com.agh.eis.mralucp.beacontrilateration.model.CSVEntry;
import com.agh.eis.mralucp.beacontrilateration.model.Signal;

import java.util.LinkedList;

/**
 * Created by Lucp on 2015-01-28.
 */

/**
 * Class computing 3 best beacon signals
 */
public class BeaconHandler {

    public static Signal getClosestTimestampBeacon(CSVEntry referenceEntry, Beacon compareBeacon) {
        return new Signal(compareBeacon, CSVEntryHandler.getClosestTimestampCSVEntry(referenceEntry, compareBeacon.getSignalHistory()));
    }

    public static LinkedList<Signal> getThreeBestRSSIBeacons(LinkedList<Signal> beaconEntries) {
        LinkedList<CSVEntry> entries = new LinkedList<CSVEntry>();
        for (Signal signal : beaconEntries) {
            entries.add(signal.getEntry());
        }
        LinkedList<CSVEntry> bestThreeEntries = CSVEntryHandler.getThreeBestRSSI(entries);
        LinkedList<Signal> bestThreeBeacons = new LinkedList<Signal>();
        for (CSVEntry entry : bestThreeEntries) {
            for (Signal beaconSignal : beaconEntries) {
                if (beaconSignal.getEntry() == entry) {
                    bestThreeBeacons.add(beaconSignal);
                }
            }
        }
        return bestThreeBeacons;
    }

    public static int getActiveBeaconsNumber(LinkedList<Beacon> beacons) {
        int number = 0;
        for (Beacon beacon : beacons) {
            if (beacon.getCurrentSignal() != null) number++;
        }
        return number;
    }

}
