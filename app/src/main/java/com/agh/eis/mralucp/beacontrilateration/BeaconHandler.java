package com.agh.eis.mralucp.beacontrilateration;

import android.util.Pair;

import java.util.LinkedList;

/**
 * Created by Lucp on 2015-01-28.
 */
public class BeaconHandler {

    public static Pair<Beacon, CSVEntry> getClosestTimestampBeacon(CSVEntry referenceEntry, Beacon compareBeacon) {
        return new Pair<Beacon, CSVEntry>(compareBeacon, CSVEntryHandler.getClosestTimestampCSVEntry(referenceEntry, compareBeacon.getSignalHistory()));
    }

    public static LinkedList<Pair<Beacon, CSVEntry>> getThreeBestRSSIBeacons(LinkedList<Pair<Beacon, CSVEntry>> beaconEntries) {
        LinkedList<CSVEntry> entries = new LinkedList<CSVEntry>();
        for (Pair<Beacon, CSVEntry> pair : beaconEntries) {
            entries.add(pair.second);
        }
        LinkedList<CSVEntry> bestThreeEntries = CSVEntryHandler.getThreeBestRSSI(entries);
        LinkedList<Pair<Beacon, CSVEntry>> bestThreeBeacons = new LinkedList<Pair<Beacon, CSVEntry>>();
        for (CSVEntry entry : bestThreeEntries) {
            for (Pair<Beacon, CSVEntry> beaconPair : beaconEntries) {
                if (beaconPair.second == entry) {
                    bestThreeBeacons.add(beaconPair);
                }
            }
        }
        return bestThreeBeacons;
        }

}
