package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.Book;
import de.cas.mse.exercise.csvdb.data.DbObject;

public class ObjectFactory<T> {
	
	public DbObject getDbObject(final Class<T> type) {
		String clazzName = type.getName();
		if(clazzName.equals(Book.class.getName())) {
			return new Book();
		} else if (clazzName.equals(Address.class.getName())) {
			return new Address();
		}
		return null;
	}
	
	
	
}
