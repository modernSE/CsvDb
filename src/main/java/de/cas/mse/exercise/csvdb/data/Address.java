package de.cas.mse.exercise.csvdb.data;

import de.cas.mse.exercise.csvdb.impl.AddressDb;

public class Address implements DbObject {
	private String guid;
	private String name;
	private String street;
	private String zip;
	private String town;

	public Address() {
	}

	public Address(String line) {
		final String[] split = line.split(AddressDb.CSV_SEPARATOR);
		setGuid(split[0]);
		setName(split[1]);
		setStreet(split[2]);
		setZip(split[3]);
		setTown(split[4]);
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((town == null) ? 0 : town.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (town == null) {
			if (other.town != null)
				return false;
		} else if (!town.equals(other.town))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	@Override
	public String toCsvLine() {
		return getGuid() + AddressDb.CSV_SEPARATOR + getName() + AddressDb.CSV_SEPARATOR + getStreet()
				+ AddressDb.CSV_SEPARATOR + getZip() + AddressDb.CSV_SEPARATOR + getTown();
	}
}
