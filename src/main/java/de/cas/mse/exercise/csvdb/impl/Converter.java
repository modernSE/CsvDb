package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.DbObject;

public interface Converter<T extends DbObject> {

	String convertToString(T object);
	
	T convertToDbObject(String string);
}
