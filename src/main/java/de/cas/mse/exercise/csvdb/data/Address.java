package de.cas.mse.exercise.csvdb.data;

import java.util.UUID;

public class Address implements DbObject {
	private String guid;
	private String name;
	private String street;
	private String zip;
	private String town;

	@Override
	public String getGuid() {
		if (guid == null)
			guid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
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
	
	// TODO: refactoring
	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
	public void setGuidIfNeeded() {
		if (this.getGuid() == null) {
			this.setGuid(createGuid());
		}
	}
}
