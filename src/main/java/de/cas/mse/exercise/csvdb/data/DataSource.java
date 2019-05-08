package de.cas.mse.exercise.csvdb.data;

import java.nio.file.Path;
import java.util.List;

public interface DataSource {
	
	String getPath();
	
	String getSeparator();
	
	List<String> readAllLines();
	
	void writeLines();
}
