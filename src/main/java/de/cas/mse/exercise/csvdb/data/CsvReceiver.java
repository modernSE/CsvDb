package de.cas.mse.exercise.csvdb.data;

public class CsvReceiver implements ArugmentsReceiver{

	private static final String CSV_SEPARATOR = ",";
	
	@Override
	public String passArguments(String... arugments) {
		return String.join(CSV_SEPARATOR, arugments);
	}

}
