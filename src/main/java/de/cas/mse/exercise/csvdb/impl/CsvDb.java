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

import de.cas.mse.exercise.csvdb.ObjectDb;
import de.cas.mse.exercise.csvdb.data.DbObject;

public abstract class CsvDb implements ObjectDb<DbObject> {

	private static final String CSV_SEPARATOR = ",";
	protected final Path basePath = Paths.get("data").toAbsolutePath();

	@Override
	public DbObject loadObject(final String guid, final Class<DbObject> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			final Optional<String> matchedDbObject = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			return convertToDbObject(
					matchedDbObject.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected DbObject convertToDbObject(final String objectLine) {
		final String[] split = objectLine.split(CSV_SEPARATOR);
		return createDbObject(split);
	}
	
	abstract protected DbObject createDbObject(final String[] objectElements);

	@Override
	public List<DbObject> loadAllObjects(final Class<DbObject> type) {
		final Path tableFile = determineTableFile();
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			return lines.stream().map(e -> convertToDbObject(e)).collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DbObject insert(final DbObject dbObject) {
		setGuidIfNeeded(dbObject);
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(toCsvLine(dbObject));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return dbObject;
	}

	private void setGuidIfNeeded(final DbObject dbObject) {
		if (dbObject.getGuid() == null) {
			dbObject.setGuid(createGuid());
		}
		
	}
	
	protected String toCsvLine(DbObject dbObject) {
		return dbObject.getElementsAsString(CSV_SEPARATOR);
	}


	protected Path determineTableFile() {
		return basePath.resolve(getFileName());
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
	protected abstract String getFileName();
	
}
