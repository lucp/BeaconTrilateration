package com.agh.eis.mralucp.beacontrilateration;

import android.util.Pair;

import java.util.LinkedList;

/**
 * Created by Lucp on 2015-01-28.
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

}
