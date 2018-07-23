package de.cas.mse.exercise.csvdb.data;

public class Book implements DbObject {
	
	private String guid;
	private String title;
	private String isbn;
	private String author;
	
	@Override
	public String getGuid() {
		return guid;
	}

	@Override
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toCsvLine() {
		return getGuid() + CSV_SEPARATOR + getTitle() + CSV_SEPARATOR + getIsbn() + CSV_SEPARATOR + getAuthor();
	}

	

}
