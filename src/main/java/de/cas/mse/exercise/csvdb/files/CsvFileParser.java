package de.cas.mse.exercise.csvdb.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.data.Address;

public class CsvFileParser implements IFileParser{

	private final String csvSeparator;
	
	public CsvFileParser(String separator) {
		this.csvSeparator = separator;
	}
	
	@Override
	public List<String[]> parseFile(Path tableFile) {
		try {
			final List<String> lines = Files.readAllLines(tableFile);
	
			final List<String[]> splittedLines = lines.stream().map(x -> x.split(csvSeparator))
													  .collect(Collectors.toList());
			
			return splittedLines;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
