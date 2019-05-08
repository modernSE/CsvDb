package de.cas.mse.exercise.csvdb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cas.mse.exercise.csvdb.Database;
import de.cas.mse.exercise.csvdb.data.Address;

public class InMemoryAddressDb implements Database<Address> {

	private Map<String, Address> addresses = new HashMap<>();
	
	@Override
	public Address loadObject(String guid, Class<Address> type) {
		return addresses.get(guid);
	}

	@Override
	public List<Address> loadAllObjects(Class<Address> type) {
		return new ArrayList<>(addresses.values());
	}

	@Override
	public Address insert(Address object) {
		object.setGuidIfNeeded();
		this.addresses.put(object.getGuid(), object);
		return object;
	}

}
