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
import de.cas.mse.exercise.csvdb.data.DbObject;

public abstract class AbstractDb<T extends DbObject> implements CsvDB<T> {

	protected static final String CSV_SEPARATOR = ",";
	protected final Path basePath = Paths.get("data").toAbsolutePath();

	protected abstract Path determineTableFile();

	protected abstract T convert(final String line);

	protected abstract String toCsvLine(final T dbObject);

	@Override
	public T loadObject(final String guid, final Class<T> type) {
		final List<String> lines = readAllLines();
		final Optional<String> matchedType = lines.stream().filter(e -> e.startsWith(guid)).findAny();
		return convert(matchedType //
				.orElseThrow(() -> new RuntimeException("dbObject with guid " + guid + "not found")));
	}

	@Override
	public List<T> loadAllObjects(final Class<T> type) {
		final List<String> lines = readAllLines();
		return lines.stream().map(e -> convert(e)).collect(Collectors.toList());
	}

	@Override
	public T insert(final T dbObject) {
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

	private void setGuidIfNeeded(final T dbObject) {
		if (dbObject.getGuid() == null) {
			dbObject.setGuid(createGuid());
		}
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	private List<String> readAllLines() {
		final Path tableFile = determineTableFile();
		try {
			return Files.readAllLines(tableFile);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
