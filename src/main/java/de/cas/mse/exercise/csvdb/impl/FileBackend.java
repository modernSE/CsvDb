package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.DbBackend;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class FileBackend implements DbBackend {

    protected final Path basePath = Paths.get("data").toAbsolutePath();

    private final String fileName;

    FileBackend(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> loadAllLines() {
        final Path tableFile = determineTableFile();
        try {
            return Files.readAllLines(tableFile);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> loadLine(String guid) {
        return loadAllLines().stream()
                             .filter(e -> e.startsWith(guid))
                             .findAny();
    }

    @Override
    public Optional<String> insert(String lineToInsert) {
        Path tableFile = determineTableFile();
        try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
            file.seek(file.length());
            file.writeBytes(lineToInsert);
            return Optional.of(lineToInsert);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    protected Path determineTableFile() {
        return basePath.resolve(fileName);
    }

}
