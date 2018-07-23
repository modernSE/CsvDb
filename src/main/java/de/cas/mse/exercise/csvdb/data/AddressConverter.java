package de.cas.mse.exercise.csvdb.data;

import de.cas.mse.exercise.csvdb.impl.Converter;

public class AddressConverter implements Converter<Address>{
	
	private static final String CSV_SEPARATOR = ",";
	
	@Override
	public String convertToString(Address object) {
		return String.join(CSV_SEPARATOR, object.getGuid(), object.getName(), object.getStreet(), object.getTown(), object.getZip());
	}
	
	@Override
	public Address convertToDbObject(String addressLine) {
		final String[] split = addressLine.split(CSV_SEPARATOR);
		final Address addressObject = new Address();
		addressObject.setGuid(split[0]);
		addressObject.setName(split[1]);
		addressObject.setStreet(split[2]);
		addressObject.setZip(split[3]);
		addressObject.setTown(split[4]);
		return addressObject;
	}

}
