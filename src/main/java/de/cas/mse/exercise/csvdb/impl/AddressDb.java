package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DbObject;
import de.cas.mse.exercise.csvdb.storage.CSVStorage;
import de.cas.mse.exercise.csvdb.storage.CSVToAddressMapper;
import de.cas.mse.exercise.csvdb.storage.CSVToDbObjectMapper;
import de.cas.mse.exercise.csvdb.storage.Storage;


public class AddressDb {
	protected final Path basePath = Paths.get("data").toAbsolutePath();
	
	protected Storage<DbObject> storage;
	
	public AddressDb() {
		storage = new CSVStorage(basePath, new CSVToAddressMapper());
	}

	public Address loadObject(final String guid, final Class<Address> type) {
		return storage.loadObject(guid, type);
	}



	public List<Address> loadAllObjects(final Class<Address> type) {
		return storage.loadAllObjects(type);
	}

	public void insert(final Address address) {
		storage.insert(address);
	}

}
