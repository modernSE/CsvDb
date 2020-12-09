package de.cas.mse.exercise.csvdb.persistence;

import java.util.List;

public interface CsvSource {
    
    List<String> readAllLines();
    
    void insertLine(String line);
}