package de.cas.mse.exercise.csvdb.impl;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;

import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDbTest {

	@Test
	public void get_shouldResultInOneAdresses() throws Exception {
		TestableDataSource<Address> source = new TestableDataSource<>();
		String guid = UUID.randomUUID().toString().replaceAll("-", "");
		Address address1 = new Address();
		address1.setGuid(guid);
		address1.setName("ThisIsAName");
		source.save(address1);
		AddressDb addressDb = new AddressDb(source);
		List<Address> result = addressDb.loadAllObjects(Address.class);
		assertEquals(1, result.size());
		assertEquals(address1, result.get(0));
	}

	@Test
	@Ignore
	public void insert_shouldContainOneLineInFile() throws Exception {
		final Address a = new Address();
		a.setName("test");
		a.setStreet("str");
		a.setTown("ort");
		a.setZip("23553");

		addressDb.insert(a);

		final List<String> fileLines = Files.readAllLines(addressDb.determineTableFile());
		assertEquals(1, fileLines.size());
		assertEquals(addressDb.toCsvLine(a), fileLines.get(0));
	}

}
