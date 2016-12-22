package de.cas.mse.exercise.csvdb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import de.cas.mse.exercise.csvdb.io.AddressIO;

public class AddressIOInMemory implements AddressIO {

	private List<List<String>> db = new ArrayList<>();

	@Override
	public void insertAddress(List<String> addressValues) {
		db.add(addressValues);
	}

	@Override
	public List<List<String>> loadAllAddresses() {
		return db;
	}

	@Override
	public List<String> findFirst(Predicate<List<String>> p) {
		return db.stream().filter(p).findFirst().orElseThrow(() -> new RuntimeException());
	}

}
