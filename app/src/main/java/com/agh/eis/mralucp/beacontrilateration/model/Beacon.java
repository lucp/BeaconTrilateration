package com.agh.eis.mralucp.beacontrilateration.model;

import com.agh.eis.mralucp.beacontrilateration.handlers.CSVReader;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Lucp on 2015-01-28.
 */
public class Beacon {

    private UUID uuid;

    private double positionX;
    private double positionY;

    private CSVEntry currentSignal;
    private LinkedList<CSVEntry> signalHistory;
    private static int bufferSize = 10000;

    public Beacon(UUID uuid, double positionX, double positionY) {
        this.uuid= uuid;
        this.positionX = positionX;
        this.positionY = positionY;
        this.currentSignal = null;
        this.signalHistory = new LinkedList<CSVEntry>();
    }

    public Beacon(UUID uuid, double positionX, double positionY, String filePath) {
        this.uuid = uuid;
        this.positionX = positionX;
        this.positionY = positionY;
        this.currentSignal = null;
        this.signalHistory = CSVReader.readCSV(filePath);
    }

    public LinkedList<CSVEntry> addSignalToHistory(CSVEntry entry) {
        if (this.signalHistory.size() > Beacon.bufferSize) {
            this.signalHistory.removeFirst();
        }
        this.signalHistory.add(entry);
        return this.signalHistory;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
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

    public void setCurrentSignalAndAddToHistory(CSVEntry currentSignal) {
        this.currentSignal = currentSignal;
        this.addSignalToHistory(currentSignal);
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
