package de.cas.mse.exercise.csvdb.impl;

import java.util.List;

import de.cas.mse.exercise.csvdb.data.DbObject;

public interface DataSource<T extends DbObject> {

	List<String> getData(Class<T> type);

	T save(T dbObject);

}
