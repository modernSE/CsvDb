package de.cas.mse.exercise.csvdb.storage;


import de.cas.mse.exercise.csvdb.data.Address;

public class CSVToAddressMapper implements CSVToDbObjectMapper<Address> {
	
	private static final String CSV_SEPARATOR = ",";
	
	@Override
	public Address convert(final String addressLine) {
		final String[] split = addressLine.split(CSV_SEPARATOR);
		final Address addressObject = new Address();
		addressObject.setGuid(split[0]);
		addressObject.setName(split[1]);
		addressObject.setStreet(split[2]);
		addressObject.setZip(split[3]);
		addressObject.setTown(split[4]);
		return addressObject;
	}
	
	@Override
	public String toCsvLine(final Address address) {
		return address.getGuid() + CSV_SEPARATOR + address.getName() + CSV_SEPARATOR + address.getStreet()
				+ CSV_SEPARATOR + address.getZip() + CSV_SEPARATOR + address.getTown();
	}
}
