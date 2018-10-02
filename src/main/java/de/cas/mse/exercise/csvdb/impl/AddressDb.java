package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;

// this class does way to much, e.g.
// - serializes Addresses
// - deserializes Addresses
// - manage data
public class AddressDb implements CsvDB<Address> {

	private static final String CSV_SEPARATOR = ",";
	// all path logic should move to FileStorage subclass of Storage
	protected final Path basePath = Paths.get("data").toAbsolutePath();
	private Storage storage;

	public AddressDb(Storage storage) {
		this.storage = storage;
	}

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
		final Path tableFile = determineTableFile();
		// aulagern
		try {
			final List<String> lines = storage.getLines(tableFile);
			final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			// move error handling
			return turnToAddress(
					matchedAddress.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Address turnToAddress(final String addressLine) {
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
	public List<Address> loadAllObjects(final Class<Address> type) {
		final Path tableFile = determineTableFile();
		// aulagern
		try {
			final List<String> lines = storage.getLines(tableFile);
			return lines.stream().map(e -> turnToAddress(e)).collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Address insert(final Address address) {
		setGuidIfNeeded(address);
		final Path tableFile = determineTableFile();
		storage.insert(tableFile, toCsvLine(address));
		return address;
	}

	private void setGuidIfNeeded(final Address address) {
		if (address.getGuid() == null) {
			address.setGuid(createGuid());
		}
	}

	protected String toCsvLine(final Address address) {
		return address.getGuid() + CSV_SEPARATOR + address.getName() + CSV_SEPARATOR + address.getStreet()
				+ CSV_SEPARATOR + address.getZip() + CSV_SEPARATOR + address.getTown();
	}

	protected Path determineTableFile() {
		return basePath.resolve("Address.csv");
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
