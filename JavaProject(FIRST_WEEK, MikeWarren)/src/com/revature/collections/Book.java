package com.revature.collections;

public class Book implements Comparable<Book>{
	private String name;
	private String author;
	private int pageCount;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public Book(String name, String author, int pageCount) {
		super();
		this.name = name;
		this.author = author;
		this.pageCount = pageCount;
	}
	
	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + ", pageCount=" + pageCount + "]";
	}

	@Override
	public int compareTo(Book book) {
		return this.author.compareTo(book.getAuthor());
	}
	
}
