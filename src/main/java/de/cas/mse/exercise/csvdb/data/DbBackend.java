package de.cas.mse.exercise.csvdb.data;

import java.util.List;
import java.util.Optional;

public interface DbBackend {

    List<String> loadAllLines();

    Optional<String> loadLine(String guid);

    Optional<String> insert(String lineToInsert);
}
