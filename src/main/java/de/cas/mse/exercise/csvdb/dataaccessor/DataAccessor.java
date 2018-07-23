package de.cas.mse.exercise.csvdb.dataaccessor;

public interface DataAccessor {
	public String[] loadObject(String guid, Class type);
	public String[][] loadAllObjects(Class type);
	public void insert(String[] object, Class type);
}
