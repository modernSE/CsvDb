package de.cas.mse.exercise.csvdb.dataaccessor;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.data.Address;

public class CsvDataAccessor implements DataAccessor {

	private static final String CSV_SEPARATOR = ",";
	protected final Path basePath = Paths.get("data").toAbsolutePath();
	
	@Override
	public String[] loadObject(String guid, Class type) {
		final Path tableFile = determineTableFile(type);
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			return matchedAddress.orElseThrow(() -> new RuntimeException("object with guid " + guid + "not found")).split(CSV_SEPARATOR);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String[][] loadAllObjects(Class type) {
		final Path tableFile = determineTableFile(type);
		try {
			final List<String> lines = Files.readAllLines(tableFile);
			return lines.stream()
				.map(line -> line.split(CSV_SEPARATOR))
				.collect(Collectors.toList()).toArray(new String[0][0]);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(String[] objectData, Class type) {
		final Path tableFile = determineTableFile(type);
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(toCsvLine(objectData));
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}
	
	protected String toCsvLine(final String[] objectData) {
		StringBuilder builder = new StringBuilder();
		for (String data : objectData) {
			builder.append(data + CSV_SEPARATOR);
		}
		String builderString = builder.toString();
		return removeLastSeparator(builderString);
	}

	private String removeLastSeparator(String builderString) {
		return builderString.substring(0, builderString.length() - CSV_SEPARATOR.length());
	}
	
	protected Path determineTableFile(Class type) {
		if (type.getName().equals(Address.class.getName())) {
			return basePath.resolve("Address.csv");
		}
		
		throw new RuntimeException("Invalid type.");
	}

}
