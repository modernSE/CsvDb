package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.Book;
import de.cas.mse.exercise.csvdb.data.DbConverter;

public class BookConverter implements DbConverter<Book> {
    private static final String CSV_SEPARATOR = ",";

    @Override
    public String convertToDbEntry(Book book) {
        return book.getGuid() + CSV_SEPARATOR +
                book.getTitle() + CSV_SEPARATOR +
                book.getAuthor();
    }

    @Override
    public Book convertFromDbEntry(String entry) {
        String[] splitEntry = entry.split(CSV_SEPARATOR);
        final Book bookObject = new Book();
        bookObject.setGuid(splitEntry[0]);
        bookObject.setTitle(splitEntry[1]);
        bookObject.setAuthor(splitEntry[2]);
        return bookObject;
    }
}
