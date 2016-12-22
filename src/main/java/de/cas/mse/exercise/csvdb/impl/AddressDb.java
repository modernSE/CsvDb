package de.cas.mse.exercise.csvdb.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.io.AddressIO;

public class AddressDb implements CsvDB<Address> {

	private AddressIO io;

	public AddressDb(AddressIO io) {
		this.io = io;
	}

	@Override
	public Address insert(final Address address) {
		setGuidIfNeeded(address);
		io.insertAddress(convertToList(address));
		return address;
	}

	@Override
	public List<Address> loadAllObjects(final Class<Address> type) {
		return io.loadAllAddresses().stream().map(this::turnToAddress).collect(Collectors.toList());
	}

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
		return turnToAddress(io.findFirst(a -> a.get(0).equals(guid)));
	}

	protected List<String> convertToList(final Address address) {
		return Arrays.asList(address.getGuid(), address.getName(), address.getStreet(), address.getTown(),
				address.getZip());
	}

	private Address turnToAddress(final List<String> addressLine) {
		final Address addressObject = new Address();
		addressObject.setGuid(addressLine.get(0));
		addressObject.setName(addressLine.get(0));
		addressObject.setStreet(addressLine.get(0));
		addressObject.setZip(addressLine.get(0));
		addressObject.setTown(addressLine.get(0));
		return addressObject;
	}

	private void setGuidIfNeeded(final Address address) {
		if (address.getGuid() == null) {
			address.setGuid(createGuid());
		}
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
