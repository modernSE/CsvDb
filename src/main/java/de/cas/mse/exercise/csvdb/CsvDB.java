package de.cas.mse.exercise.csvdb;

import java.util.List;

import de.cas.mse.exercise.csvdb.data.DbObject;

public interface CsvDB<T extends DbObject> {

	public T loadObject(String guid, Class<T> type);
	public List<T> loadAllObjects(Class<T> type);
	public T insert(T object);

}
