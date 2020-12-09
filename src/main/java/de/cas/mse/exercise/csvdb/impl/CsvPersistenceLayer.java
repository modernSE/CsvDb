package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import de.cas.mse.exercise.csvdb.PersistenceLayer;
import de.cas.mse.exercise.csvdb.Record;

public class CsvPersistenceLayer implements PersistenceLayer {

    List<String> lines;
    private static final String CSV_SEPARATOR = ",";
    protected final Path basePath = Paths.get("data").toAbsolutePath();

    @Override
    public void initialize() throws IOException {
        final Path tableFile = determineTableFile();
        lines = Files.readAllLines(tableFile);
    }

    @Override
    public Stream<Record> getAllRecords() {
        return lines.stream().map(it -> this.turnToRecord(it));
    }

   	private Record turnToRecord(String line) {
		final String[] split = line.split(CSV_SEPARATOR);
        final Record record = new Record();
        record.fields = split;
		return record;
	}
    
	protected Path determineTableFile() {
		return basePath.resolve("Address.csv");
    }

    protected String toCsvLine(final Record record) {
		return String.join(CSV_SEPARATOR, record.fields);
	}

    @Override
    public void insert(Record record) {
        final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(toCsvLine(record));
		} catch (final IOException e) {
			e.printStackTrace();
		}
    }
}