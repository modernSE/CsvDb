package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;

public class DynamicDB implements CsvDB<Address> {
	
	final String[] mockArray = {"A","B", "C", "D", "E"};
	
	public List<Address> getMockList(){
		
	 List<Address> mockAddresses = new ArrayList<>();
	 Address address = new Address();
	
	address.setGuid("a");
	address.setName("b");
	address.setStreet("c");
	address.setZip("e");
	address.setTown("e");
	mockAddresses.add(address);
	
	return mockAddresses;
	
	}
	private static final String CSV_SEPARATOR = ",";
	protected final Path basePath = Paths.get("data").toAbsolutePath();

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
	
			final Optional<String> matchedAddress = getMockList().stream().filter(e -> e.startsWith(guid)).findAny();
			return matchedAddress.get();

		}
	}


	@Override
	public List<Address> loadAllObjects(final Class<Address> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			return lines.stream().map(e -> turnToAddress(e)).collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Address insert(final Address address) {
		setGuidIfNeeded(address);
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
