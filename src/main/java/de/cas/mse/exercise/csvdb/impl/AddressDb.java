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

import de.cas.mse.exercise.csvdb.DB;
import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.files.CsvFileParser;
import de.cas.mse.exercise.csvdb.files.IFileParser;

public class AddressDb implements DB<Address> {

	protected final Path basePath = Paths.get("data").toAbsolutePath();
	private IFileParser parser;
	
	public AddressDb(IFileParser parser) {
		this.parser = parser;
	}

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
		final Path tableFile = determineTableFile();

		List<String[]> splittedLines = parser.parseFile(tableFile);

		final Optional<String[]> matchedAddress = splittedLines.stream().filter(e -> e[0].startsWith(guid)).findAny();

		return turnToAddress(
				matchedAddress.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")));

	}

	private Address turnToAddress(final String[] split) {
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

		List<String[]> lines = parser.parseFile(tableFile);

		return lines.stream().map(e -> turnToAddress(e)).collect(Collectors.toList());
	}

	@Override
	public Address insert(final Address address) {
		setGuidIfNeeded(address);
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(address.toCsvLine(CSV_SEPARATOR));
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

	protected Path determineTableFile() {
		return basePath.resolve("Address.csv");
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
