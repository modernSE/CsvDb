package de.cas.mse.exercise.csvdb;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;

// Insert / read Funktionen definieren
public class FileSystem implements PersistanceLayer {
	
	Path path;
	
	public FileSystem(Path path) {
		this.path = path;
	}

	@Override
	public void persist(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> loadCsVDB() {
		// Guter Ansatz, die Liste in eine eigene Methode auszulagern
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
 