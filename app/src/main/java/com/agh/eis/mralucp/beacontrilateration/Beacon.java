package com.agh.eis.mralucp.beacontrilateration;

import java.util.LinkedList;

/**
 * Created by Lucp on 2015-01-28.
 */
public class Beacon {

    private String id;

    private double positionX;
    private double positionY;

    private CSVEntry currentSignal;
    private LinkedList<CSVEntry> signalHistory;
    private static int bufferSize = 10000;

    public Beacon(String id, double positionX, double positionY) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.currentSignal = null;
        this.signalHistory = new LinkedList<CSVEntry>();
    }

    public LinkedList<CSVEntry> addSignalToHistory(CSVEntry entry) {
        this.signalHistory.add(entry);
        return this.signalHistory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public CSVEntry getCurrentSignal() {
        return currentSignal;
    }

    public void setCurrentSignal(CSVEntry currentSignal) {
        this.currentSignal = currentSignal;
    }

    public LinkedList<CSVEntry> getSignalHistory() {
        return signalHistory;
    }

    public void setSignalHistory(LinkedList<CSVEntry> signalHistory) {
        this.signalHistory = signalHistory;
    }

    public static int getBufferSize() {
        return bufferSize;
    }

    public static void setBufferSize(int bufferSize) {
        Beacon.bufferSize = bufferSize;
    }
}
