package de.cas.mse.exercise.csvdb.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.Storage;
import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDb implements CsvDB<Address> {

    private static final String CSV_SEPARATOR = ",";
    Storage storage;

    public AddressDb(final Storage storage) {
        this.storage = storage;
    }

    @Override
    public Address loadObject(final String guid, final Class<Address> type) {

        final List<String> lines = storage.read();
        final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
        return turnToAddress(
                matchedAddress.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")));
    }

    private Address turnToAddress(final String addressLine) {
        final String[] split = addressLine.split(CSV_SEPARATOR);
        final Address addressObject = new Address();
        addressObject.setGuid(split[0]);
        addressObject.setName(split[1]);
        addressObject.setStreet(split[2]);
        addressObject.setZip(split[3]);
        addressObject.setTown(split[4]);
        return addressObject;
    }

    @Override
    public List<Address> loadAllObjects(final Class<Address> type) {
        final List<String> lines = storage.read();
        return lines.stream().map(e -> turnToAddress(e)).collect(Collectors.toList());
    }

    @Override
    public Address insert(final Address address) {
        setGuidIfNeeded(address);
        storage.write(List.of(toCsvLine(address)));

        return address;
    }

    private void setGuidIfNeeded(final Address address) {
        if (address.getGuid() == null) {
            address.setGuid(createGuid());
        }
    }

    protected String toCsvLine(final Address address) {
        return address.getGuid() + CSV_SEPARATOR + address.getName() + CSV_SEPARATOR + address.getStreet()
                + CSV_SEPARATOR + address.getZip() + CSV_SEPARATOR + address.getTown();
    }

    private String createGuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}
