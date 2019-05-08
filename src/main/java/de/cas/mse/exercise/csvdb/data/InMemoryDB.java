package de.cas.mse.exercise.csvdb.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InMemoryDB implements DataSource {

	
	private String path;
	private String separator;
	
	public InMemoryDB(String path, String separator) {
		this.path = path;
		this.separator = separator;
	}
	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getSeparator() {
		return separator;
	}
	@Override
	public List<String> readAllLines() {
		try {
			return Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

}
