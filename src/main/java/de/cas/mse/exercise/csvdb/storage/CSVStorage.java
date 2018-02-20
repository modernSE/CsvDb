package de.cas.mse.exercise.csvdb.storage;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.data.DbObject;

public class CSVStorage implements Storage<DbObject> {
	
	private Path tableFilePath;
	private  CSVToDbObjectMapper<? extends DbObject> mapper;
	
	public CSVStorage(Path tableFilePath, CSVToDbObjectMapper<? extends DbObject> mapper) {
		this.tableFilePath = tableFilePath;
	}

	@Override
	public DbObject loadObject(String guid, Class<DbObject> type) {
		try {
			final List<String> lines = Files.readAllLines(tableFilePath);
			final Optional<String> lineWithGuid = lines.stream().filter(e -> e.startsWith(guid)).findAny();
			Optional<DbObject> object = lineWithGuid.map(mapper::convert);
			return object.orElseThrow(
					() -> new RuntimeException("Object of type " + type.getName() + " with id " + guid + "not found"));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DbObject> loadAllObjects(Class<DbObject> type) {
		try {
			final List<String> lines = Files.readAllLines(tableFilePath);
			return lines.stream().map(mapper::convert).collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(DbObject object) {
		setGuidIfNeeded(object);
		try (final RandomAccessFile file = new RandomAccessFile(tableFilePath.toFile(), "rw")) {
			file.seek(file.length());
			file.writeBytes(mapper.toCsvLine(object));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setGuidIfNeeded(final DbObject object) {
		if (object.getGuid() == null) {
			object.setGuid(GuidGenerator.generateGuid());
		}
	}

}
