package de.cas.mse.exercise.csvdb.storage;
import de.cas.mse.exercise.csvdb.data.DbObject;

public interface CSVToDbObjectMapper<T extends DbObject> {
	T convert(String line);
	String toCsvLine(T object);
}