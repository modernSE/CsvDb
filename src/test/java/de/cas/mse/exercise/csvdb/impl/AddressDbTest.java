package de.cas.mse.exercise.csvdb.impl;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.cas.mse.exercise.csvdb.persistence.CsvSource;
import de.cas.mse.exercise.csvdb.persistence.FileBasedCsvSource;
import de.cas.mse.exercise.csvdb.persistence.InMemoryCsvSource;
import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDbTest {

    private CsvSource csvSource;
    private CsvSource csvSource2;
    private AddressDb addressDb;
    private AddressDb addressDb2;
    private Path csvFilePath =  Paths.get("data").toAbsolutePath().resolve("Address.csv");

	@Before
	public void setup() {         
        csvSource = new FileBasedCsvSource( csvFilePath );
        csvSource2 = new InMemoryCsvSource(  );
        addressDb = new AddressDb(csvSource);
        addressDb2 = new AddressDb(csvSource2);
	}

	@After
	public void teardown() {
		csvFilePath.toFile().delete();
	}

	@Test
	public void insert_shouldContainOneLineInFile() throws Exception{
		final Address a = new Address();
		a.setName("test");
		a.setStreet("str");
		a.setTown("ort");
		a.setZip("23553");

        addressDb.insert(a);
        addressDb2.insert(a);

        final List<String> fileLines = csvSource.readAllLines();
        final List<String> fileLines2 = csvSource2.readAllLines();
		assertEquals(1, fileLines.size());
		assertEquals(1, fileLines2.size());
        assertEquals(addressDb.toCsvLine(a), fileLines.get(0));
        assertEquals(addressDb2.toCsvLine(a), fileLines2.get(0));
	}
}
