package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.io.AddressIO;

public class AddressFileBasedIO implements AddressIO {

	private static final String CSV_SEPARATOR = ",";
	protected final Path basePath = Paths.get("data").toAbsolutePath();

	@Override
	public void insertAddress(List<String> addressValues) {
		final Path tableFile = determineTableFile();
		try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(toCsvLine(addressValues));
		} catch (final IOException e) {
			// exception handling not covered
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<List<String>> loadAllAddresses() {
		final Path tableFile = determineTableFile();
		try {
			return Files.readAllLines(tableFile).stream() //
					.map(e -> Arrays.asList(e.split(CSV_SEPARATOR))) //
					.collect(Collectors.toList());
		} catch (IOException e) {
			// exception handling not covered
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> findFirst(Predicate<List<String>> p) {
		final Optional<List<String>> matchedAddress = loadAllAddresses().stream() //
				.filter(p) //
				.findFirst();
		return matchedAddress.orElseThrow(() -> new RuntimeException("no record found"));
	}

	private String toCsvLine(final List<String> address) {
		return address.get(0) + CSV_SEPARATOR + address.get(1) + CSV_SEPARATOR + address.get(2) + CSV_SEPARATOR
				+ address.get(3) + CSV_SEPARATOR + address.get(4);
	}

	private Path determineTableFile() {
		return basePath.resolve("Address.csv");
	}

}
