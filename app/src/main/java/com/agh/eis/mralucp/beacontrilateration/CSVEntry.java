package com.agh.eis.mralucp.beacontrilateration;

/**
 * Created by Lucp on 2015-01-27.
 */

//komentarz

public class CSVEntry {

    private long timestamp;
    private int rssi;

    public CSVEntry(long timestamp, int rssi) {
        this.timestamp = timestamp;
        this.rssi = rssi;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

}
