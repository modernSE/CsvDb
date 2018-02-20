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
import de.cas.mse.exercise.csvdb.data.DbObject;

public class Db<T extends DbObject> implements CsvDB<T> {

	protected final Path basePath = Paths.get("data").toAbsolutePath();

	@Override
	public T loadObject(final String guid, final Class<T> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			return turnToAddress(
					matchedAddress.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}



	@Override
	public List<T> loadAllObjects(final Class<T> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			T dbObject = type.newInstance();
			return lines.stream().map(e -> dbObject.convertFromCsv(e, csvSeparator)).collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T insert(final T dbObject) {
		setGuidIfNeeded(dbObject);
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(dbObject.toCsvLine(csvSeparator)));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return dbObject;
	}

	private void setGuidIfNeeded(final T dbObject) {
		if (dbObject.getGuid() == null) {
			dbObject.setGuid(createGuid());
		}
	}

	protected Path determineTableFile() {
		return basePath.resolve("Address.csv");
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
