package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DbConverter;

public class AddressConverter implements DbConverter<Address> {
    private static final String CSV_SEPARATOR = ",";

    @Override
    public String convertToDbEntry(Address address) {
        return address.getGuid() + CSV_SEPARATOR +
                address.getName() + CSV_SEPARATOR +
                address.getStreet() + CSV_SEPARATOR +
                address.getZip() + CSV_SEPARATOR +
                address.getTown();
    }

    @Override
    public Address convertFromDbEntry(String entry) {
        String[] splitEntry = entry.split(CSV_SEPARATOR);
        final Address addressObject = new Address();
        addressObject.setGuid(splitEntry[0]);
        addressObject.setName(splitEntry[1]);
        addressObject.setStreet(splitEntry[2]);
        addressObject.setZip(splitEntry[3]);
        addressObject.setTown(splitEntry[4]);
        return addressObject;
    }
}
