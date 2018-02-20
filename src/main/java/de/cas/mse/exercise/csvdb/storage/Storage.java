package de.cas.mse.exercise.csvdb.storage;


import java.util.List;

import de.cas.mse.exercise.csvdb.data.DbObject;

public interface Storage<T extends DbObject> {

	public T loadObject(String guid, Class<T> type);
	public List<T> loadAllObjects(Class<T> type);
	public void insert(T object);

}
