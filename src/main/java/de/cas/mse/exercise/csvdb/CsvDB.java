package de.cas.mse.exercise.csvdb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DbObject;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface CsvDB<T extends DbObject> {
	
	public T loadObject(String guid, Class<T> type);
	
	public List<T> loadAllObjects(Class<T> type);
	
	public T insert(T object);
	
	public T deserialize(String line);
	
	public Path determineTableFile();
	
	public default List<DbObject> loadAllObjects(PersistanceLayer file) {
		List<String> lines = file.loadCsVDB();
		return lines.stream().map(e -> deserialize(e)).collect(Collectors.toList());
		
	}

}
