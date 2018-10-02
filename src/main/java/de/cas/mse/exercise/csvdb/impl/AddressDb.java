package de.cas.mse.exercise.csvdb.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DbObject;

public class AddressDb implements CsvDB<Address> {

	public static final String CSV_SEPARATOR = ",";

	private DataSource<Address> source;

	public AddressDb(DataSource<Address> source) {
		this.source = source;
	}

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
		final List<String> lines = source.getData(type);
		final Optional<String> matchedObject = lines.stream().filter(e -> e.startsWith(guid)).findAny();
		return toDbObject(matchedObject.orElseThrow(
				() -> new RuntimeException("object with guid " + guid + " and type " + type + " not found")));
	}

	// doesn't belong here; can be on the DbObject class like toCsvLine
	private Address toDbObject(final String line) {
		return new Address(line);
	}

	@Override
	public List<Address> loadAllObjects(final Class<Address> type) {
		final List<String> lines = source.getData(type);
		return lines.stream().map(e -> toDbObject(e)).collect(Collectors.toList());
	}

	@Override
	public Address insert(final Address dbObject) {
		setGuidIfNeeded(dbObject);
		return source.save(dbObject);
	}

	private void setGuidIfNeeded(final DbObject dbObject) {
		if (dbObject.getGuid() == null) {
			dbObject.setGuid(createGuid());
		}
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
