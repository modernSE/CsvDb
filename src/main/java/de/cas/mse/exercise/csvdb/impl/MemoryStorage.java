package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryStorage implements Storage {

	private Map<Path, List<String>> memory;

	public MemoryStorage() {
		memory = new HashMap<>();
	}

	@Override
	public List<String> getLines(Path tablePath) throws IOException {
		return memory.get(tablePath);
	}

	@Override
	public void insert(Path tablePath, String line) {
		List<String> lines;
		if (memory.containsKey(tablePath)) {
			lines = memory.get(tablePath);
		} else {
			lines = new ArrayList<String>();
			memory.put(tablePath, lines);
		}
		lines.add(line);
	}

}
