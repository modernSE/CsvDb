package de.cas.mse.exercise.csvdb.data;

public interface DbObject {
	
	static final String CSV_SEPARATOR = ",";
	
	String getGuid();
	
	void setGuid(String guid);
	
	String toCsvLine();
}
