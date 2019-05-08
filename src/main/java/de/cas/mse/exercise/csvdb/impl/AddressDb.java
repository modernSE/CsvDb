package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DataSource;

public class AddressDb implements CsvDB<Address> {

	private DataSource source;
	public AddressDb(DataSource source) {
		this.source = source;
	}

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
//		final Path tableFile = determineTableFile();
		final List<String> lines = source.readAllLines();
		final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
		return turnToAddress(
				matchedAddress.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")));
	}

	private Address turnToAddress(final String addressLine) {
		final String[] split = addressLine.split(source.getSeparator());
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
//		final Path tableFile = determineTableFile();
		final List<String> lines = source.readAllLines();
		return lines.stream().map(e -> turnToAddress(e)).collect(Collectors.toList());
	}

	@Override
	public Address insert(final Address address) {
		setGuidIfNeeded(address);
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(toCsvLine(address));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return address;
	}

	private void setGuidIfNeeded(final Address address) {
		if (address.getGuid() == null) {
			address.setGuid(createGuid());
		}
	}

	protected String toCsvLine(final Address address) {
		return address.getGuid() + source.getSeparator() + address.getName() + source.getSeparator() + address.getStreet()
				+ source.getSeparator() + address.getZip() + source.getSeparator() + address.getTown();
	}

	protected Path determineTableFile() {
		return Paths.get((source.getPath()));
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
