package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileStorage implements Storage {

	@Override
	public List<String> getLines(Path tableFile) throws IOException {
		return Files.readAllLines(tableFile);
	}

	@Override
	public void insert(Path tableFile, String line) {
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(line);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
