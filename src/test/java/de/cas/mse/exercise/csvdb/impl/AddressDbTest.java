package de.cas.mse.exercise.csvdb.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDbTest {

	private AddressDb addressDb;
	private AddressIOInMemory memoryDb;

	@Before
	public void setup() {
		memoryDb = new AddressIOInMemory();
		addressDb = new AddressDb(memoryDb);
	}

	@Test
	public void insert_shouldContainOneLineInFile() throws Exception{
		final Address a = new Address();
		a.setName("test");
		a.setStreet("str");
		a.setTown("ort");
		a.setZip("23553");

		addressDb.insert(a);

		final List<List<String>> fileLines = memoryDb.loadAllAddresses();
		assertEquals(1, fileLines.size());
		assertThat(fileLines.get(0)).containsExactlyElementsOf(addressDb.convertToList(a));
	}

}
