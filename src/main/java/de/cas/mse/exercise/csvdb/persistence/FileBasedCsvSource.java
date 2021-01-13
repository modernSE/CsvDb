package de.cas.mse.exercise.csvdb.persistence;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class FileBasedCsvSource implements CsvSource {
    
    Path filePath;

    public FileBasedCsvSource(Path filePath) {
        this.filePath = filePath;
    }

    public List<String> readAllLines() {
        try {
			return Files.readAllLines(filePath);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    public void insertLine(String line){
		try (final RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(line);
		} catch (final IOException e) {
			e.printStackTrace();
		}
    }
}