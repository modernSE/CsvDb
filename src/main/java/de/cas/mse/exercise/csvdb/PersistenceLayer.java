package de.cas.mse.exercise.csvdb;

import java.io.IOException;
import java.util.stream.Stream;

public interface PersistenceLayer {
    void initialize() throws IOException;
    Stream<Record> getAllRecords();
    void insert(Record record);
}