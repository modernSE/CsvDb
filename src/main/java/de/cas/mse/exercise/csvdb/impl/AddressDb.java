package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDb extends CsvDb {

	private static final String CSV_FILENAME = "Address.csv";

	@Override
	protected Address createDbObject(String[] objectElements) {
		final Address addressObject = new Address();
		addressObject.setGuid(objectElements[0]);
		addressObject.setName(objectElements[1]);
		addressObject.setStreet(objectElements[2]);
		addressObject.setZip(objectElements[3]);
		addressObject.setTown(objectElements[4]);
		return addressObject;
	}

	@Override
	protected String getFileName() {
		return CSV_FILENAME;
	}
}
