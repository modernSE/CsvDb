package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.cas.mse.exercise.csvdb.data.DbObject;

public class FileDataSource<T extends DbObject> implements DataSource<T> {

	// pass in basePath via constructor
	protected final Path basePath = Paths.get("data").toAbsolutePath();

	@Override
	public List<String> getData(Class<T> type) {
		// return in try/catch?
		try {
			return Files.readAllLines(determineTableFile(type));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T save(T dbObject) {
		final Path tableFile = determineTableFile(dbObject.getClass());
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(dbObject.toCsvLine());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return dbObject;
	}

	// dangerous? files must be called the same as object +1
	protected Path determineTableFile(Class<? extends DbObject> type) {
		return basePath.resolve(type.getName() + ".csv");
	}

}
