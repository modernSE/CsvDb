package de.cas.mse.exercise.csvdb.files;

import java.nio.file.Path;
import java.util.List;

public interface IFileParser {

	List<String[]> parseFile(Path tableFile);
}
