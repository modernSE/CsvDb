package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.ws.soap.AddressingFeature;

import de.cas.mse.exercise.csvdb.ObjectDB;
import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.AddressConverter;
import de.cas.mse.exercise.csvdb.data.ArugmentsReceiver;
import de.cas.mse.exercise.csvdb.data.CsvReceiver;
import de.cas.mse.exercise.csvdb.data.DbObject;

public class CsvDb<T extends DbObject> implements ObjectDB<T> {
	
	private final Map<Class<?>, Converter<?>> converters = new HashMap<>();

	

	protected final Path basePath = Paths.get("data").toAbsolutePath();

	public CsvDb() {
		converters.put(Address.class, new AddressConverter());
	}
	
	@Override
	public T loadObject(final String guid, final Class<T> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			
			Converter<T> converter = (Converter<T>) converters.get(type);
			return converter.convertToDbObject(matchedAddress.get());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<T> loadAllObjects(final Class<T> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			Converter<T> converter = (Converter<T>) converters.get(type);
			return lines.stream().map(e -> converter.convertToDbObject(e)).collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T insert(final T address) {
		setGuidIfNeeded(address);
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			Converter<T> converter = (Converter<T>) converters.get(address.getClass());
			String string = converter.convertToString(address);
			file.writeBytes(string);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return address;
	}

	private void setGuidIfNeeded(final T address) {
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
