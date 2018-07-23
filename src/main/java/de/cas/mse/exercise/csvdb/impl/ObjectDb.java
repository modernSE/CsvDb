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
import de.cas.mse.exercise.csvdb.data.Book;
import de.cas.mse.exercise.csvdb.data.DbObject;

public class ObjectDb<T extends DbObject> implements CsvDB<T> {

	protected final Path basePath = Paths.get("data").toAbsolutePath();

	@Override
	public T loadObject(final String guid, final Class<T> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			return turnToObject(
					matchedAddress.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")), type);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private T turnToObject(final String line, final Class<T> type) {
		ObjectFactory<T> objectFactory = new ObjectFactory<>();
		DbObject dbObject = objectFactory.getDbObject(type);
		// duplicate call of getDbObject()
		return (T) objectFactory.getDbObject(type);
	}

	@Override
	public List<T> loadAllObjects(final Class<T> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			return lines.stream().map(e -> turnToObject(e, type)).collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T insert(final T object) {
		setGuidIfNeeded(object);
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(toCsvLine(object));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	private void setGuidIfNeeded(final T object) {
		if (object.getGuid() == null) {
			object.setGuid(createGuid());
		}
	}

	protected String toCsvLine(final T object) {
		return object.toCsvLine();
	}

	protected Path determineTableFile() {
		return basePath.resolve("Address.csv");
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
