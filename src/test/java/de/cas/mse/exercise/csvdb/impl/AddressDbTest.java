package de.cas.mse.exercise.csvdb.impl;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDbTest {

	private CsvDb addressDb;

	@Before
	public void setup() {
		addressDb = new CsvDb();
	}

	@After
	public void teardown() {
		addressDb.determineTableFile().toFile().delete();
	}

	@Test
	public void insert_shouldContainOneLineInFile() throws Exception{
		final Address a = new Address();
		a.setName("test");
		a.setStreet("str");
		a.setTown("ort");
		a.setZip("23553");

		addressDb.insert(a);

		final List<String> fileLines = Files.readAllLines(addressDb.determineTableFile());
		assertEquals(1, fileLines.size());
		assertEquals("test,str,ort,23553", fileLines.get(0));
	}

}
