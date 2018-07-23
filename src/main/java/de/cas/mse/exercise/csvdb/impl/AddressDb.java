package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DbObject;

// keine spezielle addressDb, sondern allgemein
public class AddressDb implements CsvDB<DbObject> {

	private static final String CSV_SEPARATOR = ",";
	protected final Path basePath = Paths.get("data").toAbsolutePath();
	private HashMap<String, DbObject> data = new HashMap<>();
	private DataStorage storageType;
	private DbObjectToSpecificTypeFactory factory;

	public AddressDb(DataStorage storageType) {
		this.storageType = storageType;
		this.factory = new DbObjectToSpecificTypeFactory();
	}

	@Override
	public DbObject loadObject(final String guid, final Class<DbObject> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			String foundLine = matchedAddress
					.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found"));
			//DbObject objectToLoad = factory.createDbObjectWithSpecificType(foundLine, type);
			return turnToAddress(foundLine);

		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Generischer return für Objecte
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
	public List<DbObject> loadAllObjects(final Class<DbObject> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			return lines.stream().map(e -> turnToAddress(e)).collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/* insert-Verhalten lässt sich verallgemeinern 
	--> Verhaltensinterface, das durch verschiedene Verhaltensklassen implementiert werden kann*/
	@Override
	public DbObject insert(final DbObject address) {
		setGuidIfNeeded(address);
		if (storageType == DataStorage.FILE) {
			return insertInFile(address);
		}

		if (storageType == DataStorage.INMEMORY) {
			return insertInMemory(address);
		}

		if (storageType == DataStorage.DATABASE) {
			return null;
		}

		return null;

	}

	private DbObject insertInMemory(DbObject address) {
		return data.put(address.getGuid(), address);
	}

	private DbObject insertInFile(DbObject address) {
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(toCsvLine((Address) address));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return address;
	}

	private void setGuidIfNeeded(final DbObject address) {
		if (address.getGuid() == null) {
			address.setGuid(createGuid());
		}
	}
	
	// generisch
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
