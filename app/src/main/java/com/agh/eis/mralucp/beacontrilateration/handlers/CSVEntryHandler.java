package com.agh.eis.mralucp.beacontrilateration.handlers;

import com.agh.eis.mralucp.beacontrilateration.model.CSVEntry;

import java.util.LinkedList;
//komentarz

/**
 * Created by Lucp on 2015-01-27.
 */
public class CSVEntryHandler {

    public static CSVEntry getClosestTimestampCSVEntry(CSVEntry referenceEntry, LinkedList<CSVEntry> entries) {
        CSVEntry previousEntry = entries.getFirst();
        long previousDifference = Math.abs(previousEntry.getTimestamp() - referenceEntry.getTimestamp());
        for (CSVEntry compare : entries) {
            long difference = Math.abs(compare.getTimestamp() - referenceEntry.getTimestamp());
            if (previousDifference < difference) {
                return previousEntry;
            }
            else {
                previousDifference = difference;
                previousEntry = compare;
            }
        }
        return entries.getLast();
    }

    public static LinkedList<CSVEntry> getThreeBestRSSI(LinkedList<CSVEntry> entries) {
        int entriesSize = entries.size();
        CSVEntry[] bestThreeTable = new CSVEntry[3];
        LinkedList<CSVEntry> bestThree = new LinkedList<CSVEntry>();
        for (int i = 0; i < 3; i++) {
            bestThreeTable[i] = null;
        }
        if (entriesSize > 3) {
            for (int i = 0; i < entriesSize; i++) {
                CSVEntry entry = entries.get(i);
                if (bestThreeTable[2] == null || entry.getRssi() > bestThreeTable[2].getRssi()) {
                    if (bestThreeTable[1] == null || entry.getRssi() > bestThreeTable[1].getRssi()) {
                        if (bestThreeTable[0] == null || entry.getRssi() > bestThreeTable[0].getRssi()) {
                            bestThreeTable[2] = bestThreeTable[1];
                            bestThreeTable[1] = bestThreeTable[0];
                            bestThreeTable[0] = entry;
                        }
                        else {
                            bestThreeTable[2] = bestThreeTable[1];
                            bestThreeTable[1] = entry;
                        }
                    }
                    else {
                        bestThreeTable[2] = entry;
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                bestThree.add(bestThreeTable[i]);
            }
        }
        else {
            for (CSVEntry entry : entries) {
                bestThree.add(entry);
            }
        }
        return bestThree;
    }

}
