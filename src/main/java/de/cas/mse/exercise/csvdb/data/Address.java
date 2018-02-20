package de.cas.mse.exercise.csvdb.data;

public class Address implements DbObject {
	private String guid;
	private String name;
	private String street;
	private String zip;
	private String town;

	@Override
	public String getGuid() {
		return guid;
	}

	@Override
	public void setGuid(final String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(final String zip) {
		this.zip = zip;
	}

	public String getTown() {
		return town;
	}

	public void setTown(final String town) {
		this.town = town;
	}

	@Override
	public DbObject convertFromCsv(String csvLine, String csvSeparator) {
		final String[] split = csvLine.split(csvSeparator);
		Address address = new Address();
		address.setGuid(split[0]);
		address.setName(split[1]);
		address.setStreet(split[2]);
		address.setZip(split[3]);
		address.setTown(split[4]);
		
		return address;
	}

	@Override
	public String toCsvLine(String csvSeparator) {
		return getGuid() + csvSeparator + getName() + csvSeparator + getStreet()
		+ csvSeparator + getZip() + csvSeparator + getTown();
	}
}
