package de.cas.mse.exercise.csvdb.data;

public interface DbObject {
	
	final String CSV_SEPARATOR = ",";

	public String getGuid();
	
	public void setGuid(String guid);
	
	public DbObject convertFromCsv(String csvLine, String csvSeparator);
	
	public String toCsvLine(String csvSeparator);
}
