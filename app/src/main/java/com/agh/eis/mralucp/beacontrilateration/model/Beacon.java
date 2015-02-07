package com.agh.eis.mralucp.beacontrilateration.model;

import com.agh.eis.mralucp.beacontrilateration.handlers.CSVReader;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Lucp on 2015-01-28.
 */
public class Beacon {

    private UUID uuid;

    private int major;
    private int minor;

    private double positionX;
    private double positionY;

    private CSVEntry currentSignal;
    private LinkedList<CSVEntry> signalHistory;
    private static int BUFFER_SIZE = 10000;

    public Beacon(UUID uuid, int major, int minor, double positionX, double positionY) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.positionX = positionX;
        this.positionY = positionY;
        this.signalHistory = new LinkedList<CSVEntry>();
    }

    public Beacon(int major, int minor) {
        this.major = major;
        this.minor = minor;
        this.signalHistory = new LinkedList<CSVEntry>();
    }

    public Beacon(int major, int minor, double positionX, double positionY) {
        this.major = major;
        this.minor = minor;
        this.positionX = positionX;
        this.positionY = positionY;
        this.signalHistory = new LinkedList<CSVEntry>();
    }

    public Beacon(int major, int minor, double positionX, double positionY, String filePath) {
        this.major = major;
        this.minor = minor;
        this.positionX = positionX;
        this.positionY = positionY;
        this.signalHistory = CSVReader.readCSV(filePath);
    }

    public LinkedList<CSVEntry> addSignalToHistory(CSVEntry entry) {
        if (this.signalHistory.size() > Beacon.BUFFER_SIZE) {
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

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
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
        return BUFFER_SIZE;
    }

    public static void setBufferSize(int bufferSize) {
        Beacon.BUFFER_SIZE = bufferSize;
    }

    @Override
    public boolean equals(Object o) {
        if (((Beacon)o).getMajor() == this.major && ((Beacon)o).getMinor() == this.minor) return true;
        else return false;
    }
}
