package d4.revature.collections;

import java.util.ArrayList;
import java.util.List;

public class ComparableVSComparator {
	public static void main(String[] args) {
		List<Book> books = new ArrayList<>();
		books.add(new Book("IT", "Bobbert", 250));
		books.add(new Book("Bilble", "Ryan Lessley", 50));
		books.add(new Book("The return of Bobbrt", "Grantley", 416));
		books.add(new Book("The way of Boobert", "Ryan Lessley", 3));
		
		System.out.println(books);
		
		//Collections.sort(books);
		books.sort(null);
		for(Book book : books) {
			System.out.println(book.getName()+" pg: "+book.getPageCount());
		}
	}
}
