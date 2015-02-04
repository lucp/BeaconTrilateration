package com.agh.eis.mralucp.beacontrilateration.model;

/**
 * Created by Lucp on 2015-01-29.
 */
public class Signal {

    private CSVEntry entry;
    private Beacon beacon;

    public Signal(Beacon beacon, CSVEntry entry) {
        this.entry = entry;
        this.beacon = beacon;
    }

    public CSVEntry getEntry() {
        return entry;
    }

    public void setEntry(CSVEntry entry) {
        this.entry = entry;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}
