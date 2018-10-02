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

public class AddressDb implements CsvDB<Address> {

	private static final String CSV_SEPARATOR = ",";

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
		// path belongs to file storage and not to DB
		final Path tableFile = determineTableFile();
		try {
			// Lesevorgang in eine Storage Klasse auslagern
			final List<String> lines = Files.readAllLines(tableFile);
			final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			return deserialize(
					matchedAddress.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	// deserialize belongs to dbObject (or alternativly PersistenceLayer) but not the DB
	public Address deserialize(String addressLine) {
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
	public Address insert(final Address address) {
		setGuidIfNeeded(address);
		final Path tableFile = determineTableFile();
		
		// Schreibvorgang in eine Storage Klasse auslagern
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

	public Path determineTableFile() {
		// ggf. Dateinamen auch direkt im Konstruktor definieren
		return basePath.resolve("Address.csv");
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}


	@Override
	public List<Address> loadAllObjects(Class<Address> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
