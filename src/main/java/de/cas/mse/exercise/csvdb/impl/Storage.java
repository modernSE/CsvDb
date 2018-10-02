package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Storage {

	// Path implies FileStorage. Use something more abstract like a String key
	List<String> getLines(Path tablePath) throws IOException;

	void insert(Path tablePath, String line);
}
