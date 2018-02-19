package de.cas.mse.exercise.csvdb.impl;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDbTest {

	private GenericDb addressDb;
	private AddressConverter addressConverter;
	private FileBackend fileBackend;

	@Before
	public void setup() {
		fileBackend = new FileBackend("Address.csv");
		addressConverter = new AddressConverter();
		addressDb = new GenericDb(addressConverter, fileBackend);
	}

	@After
	public void teardown() {
		fileBackend.determineTableFile().toFile().delete();
	}

	@Test
	public void insert_shouldContainOneLineInFile() throws Exception{
		final Address a = new Address();
		a.setName("test");
		a.setStreet("str");
		a.setTown("ort");
		a.setZip("23553");

		addressDb.insert(a);

		final List<String> fileLines = Files.readAllLines(fileBackend.determineTableFile());
		assertEquals(1, fileLines.size());
		assertEquals(addressConverter.convertToDbEntry(a), fileLines.get(0));
	}

}
