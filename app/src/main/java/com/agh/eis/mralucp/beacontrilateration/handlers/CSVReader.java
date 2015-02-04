package com.agh.eis.mralucp.beacontrilateration.handlers;

import com.agh.eis.mralucp.beacontrilateration.model.CSVEntry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
//komentarz

/**
 * Created by Lucp on 2015-01-27.
 */
public class CSVReader {

    public static LinkedList<CSVEntry> readCSV(String path) {
        LinkedList<CSVEntry> entries = new LinkedList<CSVEntry>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {

                String[] lineRead = line.split(cvsSplitBy);

                entries.add(new CSVEntry(Long.valueOf(lineRead[0]), Integer.valueOf(lineRead[1])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return entries;
    }
}
