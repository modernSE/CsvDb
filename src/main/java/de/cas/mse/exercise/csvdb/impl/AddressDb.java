package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.PersistenceLayer;
import de.cas.mse.exercise.csvdb.Record;
import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDb<T extends PersistenceLayer> implements CsvDB<Address> {

    private final T persistenceLayer;

    public AddressDb(final T persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
    }

    @Override
    public Address loadObject(final String guid, final Class<Address> type) {
        List<Address> addresses = persistenceLayer.getAllRecords().map(it -> turnToAddress(it))
                .collect(Collectors.toList());
        Optional<Address> matchingAddress = addresses.stream().filter(it -> it.getGuid().equals(guid)).findFirst();
        if (addresses.isEmpty())
            throw new RuntimeException("address with guid " + guid + "not found");
        return matchingAddress.get();
    }

    private Address turnToAddress(final Record record) {
        final Address addressObject = new Address();
        addressObject.setGuid(record.fields[0]);
        addressObject.setName(record.fields[1]);
        addressObject.setStreet(record.fields[2]);
        addressObject.setZip(record.fields[3]);
        addressObject.setTown(record.fields[4]);
        return addressObject;
    }

    private Record turnToRecord(final Address address) {
        Record result = new Record();
        result.fields = new String[] { address.getGuid(), address.getName(), address.getStreet(), address.getZip(),
                address.getTown() };
        return result;
    }

    @Override
    public List<Address> loadAllObjects(final Class<Address> type) {
        return persistenceLayer.getAllRecords().map(it -> turnToAddress(it)).collect(Collectors.toList());
    }

    @Override
    public Address insert(final Address address) {
        setGuidIfNeeded(address);
        persistenceLayer.insert(turnToRecord(address));
        return address;
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
