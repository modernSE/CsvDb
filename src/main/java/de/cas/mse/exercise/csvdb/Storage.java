package de.cas.mse.exercise.csvdb;

import java.util.List;

public interface Storage {
    public List<String> read();

    public void write(List<String> input);
}