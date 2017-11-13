package com.revature.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableVsComparator {

	public static void main(String[] args) {
		List<Book> books = new ArrayList<Book>();
		
		 books.add(new Book("The Way of Bobbert", "Ryan Lessley", 3));
		 books.add(new Book("It", "Bobbert", 317));
		 books.add(new Book("Bible", "Jesus", 250));
		 books.add(new Book("The Return of Bobbert", "Grantley", 416));
		 
		 System.out.println(books);
		 
		 for (Book book : books)
		 {
			 System.out.println(book.getName());
		 }

		 books.sort(null);
		 System.out.println();
		 for (Book book : books)
		 {
			 System.out.println(book.getName() + " by " + book.getAuthor());
		 }
		 
		 books.sort(new BookNameComparator());
		 System.out.println();
		 for (Book book : books)
		 {
			 System.out.println(book.getName() + " by " + book.getAuthor());
		 }
	}

}
