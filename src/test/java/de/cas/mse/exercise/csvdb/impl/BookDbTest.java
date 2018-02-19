package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookDbTest {

	private GenericDb bookDb;
	private BookConverter bookConverter;
	private FileBackend fileBackend;

	@Before
	public void setup() {
		fileBackend = new FileBackend("Book.csv");
		bookConverter = new BookConverter();
		bookDb = new GenericDb(bookConverter, fileBackend);
	}

	@After
	public void teardown() {
		fileBackend.determineTableFile().toFile().delete();
	}

	@Test
	public void insert_shouldContainOneLineInFile() throws Exception{
		final Book a = new Book();
		a.setTitle("1984");
		a.setAuthor("George Orwell");

		bookDb.insert(a);

		final List<String> fileLines = Files.readAllLines(fileBackend.determineTableFile());
		assertEquals(1, fileLines.size());
		assertEquals(bookConverter.convertToDbEntry(a), fileLines.get(0));
	}

}
