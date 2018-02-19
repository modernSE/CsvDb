package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DbBackend;
import de.cas.mse.exercise.csvdb.data.DbConverter;
import de.cas.mse.exercise.csvdb.data.DbObject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GenericDb<T extends DbObject> implements CsvDB<T> {


    private DbConverter<T> dbConverter;

    private DbBackend dbBackend;

    public GenericDb(DbConverter<T> dbConverter, DbBackend dbBackend) {
        this.dbBackend = dbBackend;
        this.dbConverter = dbConverter;
    }

    @Override
    public T loadObject(final String guid, final Class<T> type) {
        Optional<String> line = dbBackend.loadLine(guid);
        return dbConverter.convertFromDbEntry(
                line.orElseThrow(() -> new RuntimeException("Db entry with guid " + guid + " not found")));
    }


    @Override
    public List<T> loadAllObjects(final Class<T> type) {
        return dbBackend.loadAllLines()
                        .stream()
                        .map(dbConverter::convertFromDbEntry)
                        .collect(Collectors.toList());
    }

    @Override
    public T insert(final T object) {
        setGuidIfNeeded(object);
        String line = dbConverter.convertToDbEntry(object);
        dbBackend.insert(line);
        return object;
    }

    private void setGuidIfNeeded(final T object) {
        if (object.getGuid() == null) {
            object.setGuid(createGuid());
        }
    }

    private String createGuid() {
        return UUID.randomUUID()
                   .toString()
                   .replaceAll("-", "")
                   .toUpperCase();
    }

}
