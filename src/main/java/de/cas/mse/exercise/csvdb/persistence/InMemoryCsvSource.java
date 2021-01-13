package de.cas.mse.exercise.csvdb.persistence;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;


public class InMemoryCsvSource implements CsvSource {
    
   private List<String> csvStrings;

    public InMemoryCsvSource(List<String> csvStrings) {
        this.csvStrings = csvStrings;
    }

    public InMemoryCsvSource() {
        this(new ArrayList<String>());
    }

    public List<String> readAllLines() {        
		return csvStrings;		
    }
    
    public void insertLine(String line){
		csvStrings.add(line);
    }
}