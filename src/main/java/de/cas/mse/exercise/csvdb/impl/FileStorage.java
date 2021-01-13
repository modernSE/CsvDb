package de.cas.mse.exercise.csvdb.impl;

import java.util.List;

import de.cas.mse.exercise.csvdb.Storage;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorage implements Storage {

    protected final Path path;

    public FileStorage(Path path) {
        this.path = path;
    }

    public List<String> read() {
        try {
            return Files.readAllLines(path);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(List<String> input) {
        try (final RandomAccessFile file = new RandomAccessFile(path.toFile(), "rw")) {
            file.seek(file.length());
            input.forEach(line -> {
                try {
                    file.writeBytes(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public Path getPath() {
        return this.path;
    }

}