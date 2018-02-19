package de.cas.mse.exercise.csvdb.data;

public interface DbConverter<T extends DbObject> {

    String convertToDbEntry(T dbObject);

    T convertFromDbEntry(String entry);
}
