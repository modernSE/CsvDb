package de.cas.mse.exercise.csvdb.impl;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDb implements CsvDB<Address> {

	private static final String CSV_SEPARATOR = ",";
	protected final Path basePath;

	public AddressDb() {
		Path relativePath = Paths.get("data");
		basePath = relativePath.toAbsolutePath();
	}

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			Optional<String> matchedAddress = Optional.empty();

			for (String line : lines) {
				if (line.startsWith(guid)) {
					matchedAddress = Optional.of(line);
					break;
				}
			}

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
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			Stream<String> stream = lines.stream();
			Stream<Address> addressStream = stream.map(this::turnToAddress);
			return addressStream.collect(toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Address insert(final Address address) {
		setGuidIfNeeded(address);
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			long fileLength = file.length();
			file.seek(fileLength);
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
		String guid = address.getGuid();
		String name = address.getName();
		String street = address.getStreet();
		String zip = address.getZip();
		String town = address.getTown();
		List<String> fields = Arrays.asList(guid, name, street, zip, town);
		return String.join(CSV_SEPARATOR, fields);
	}

	protected Path determineTableFile() {
		final String fileName = "Address.csv";
		return basePath.resolve(fileName);
	}

	private String createGuid() {
		UUID newGuid = UUID.randomUUID();
		String newGuidAsString = newGuid.toString();
		String partiallyNormalizedGuid = newGuidAsString.replaceAll("-", "");

		return partiallyNormalizedGuid.toUpperCase();
	}

}
