package de.cas.mse.exercise.csvdb.impl;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDbTest {

    private AddressDb addressDb;
    private FileStorage fileStorage;

    @Before
    public void setup() {
        fileStorage = new FileStorage(Paths.get("data").toAbsolutePath().resolve("Address.csv"));
        addressDb = new AddressDb(fileStorage);
    }

    @After
    public void teardown() {
        fileStorage.getPath().toFile().delete();
    }

    @Test
    public void insert_shouldContainOneLineInFile() throws Exception {
        final Address a = new Address();
        a.setName("test");
        a.setStreet("str");
        a.setTown("ort");
        a.setZip("23553");

        addressDb.insert(a);

        final List<String> fileLines = fileStorage.read();
        assertEquals(1, fileLines.size());
        assertEquals(addressDb.toCsvLine(a), fileLines.get(0));
    }

}
