package de.cas.mse.exercise.csvdb.impl;

import java.util.List;

import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DbObject;

public class DbObjectToSpecificTypeFactory {
	
	public DbObjectToSpecificTypeFactory() {
		
	}
	
	public DbObject createDbObjectWithSpecificType(String line, final Class<DbObject> type) {
		
		if(type == Address.class) {
			return createAddress(line);
		}
	}
}
