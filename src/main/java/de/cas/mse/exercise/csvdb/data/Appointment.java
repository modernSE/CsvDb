package de.cas.mse.exercise.csvdb.data;

import de.cas.mse.exercise.csvdb.impl.AddressDb;

public class Appointment implements DbObject {

	private String guid;
	private String time;
	private String date;

	public Appointment() {
	}

	public Appointment(String line) {
		final String[] split = line.split(AddressDb.CSV_SEPARATOR);
		setGuid(split[0]);
		setDate(split[1]);
		setTime(split[2]);
	}

	@Override
	public String getGuid() {
		return guid;
	}

	@Override
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toCsvLine() {
		return getGuid() + AddressDb.CSV_SEPARATOR + getDate() + AddressDb.CSV_SEPARATOR + getTime();
	}

}
