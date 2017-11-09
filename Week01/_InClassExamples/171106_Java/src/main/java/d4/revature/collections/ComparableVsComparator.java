package d4.revature.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableVsComparator {

	public static void main(String[] args) {
		List <Book>books = new ArrayList<>();
		
		books.add(new Book("IT", "Bobbert", 317));
		books.add(new Book("Bible", "Jesus", 250));
		books.add(new Book("The Return of Bobbert", "Grantley", 416));
		books.add(new Book("The Way of Bobbert", "Ryan Lessley", 3));
		
		System.out.println(books);
		
		for(Book book : books){
			System.out.println(book.getName());
		}
		
		books.sort(null);
		
		for(Book book : books){
			System.out.println(book.getPageCount() + ": " + book.getName());
		}
		
		books.sort(new BookNameComparator());
		
		for(Book book : books){
			System.out.println(book.getPageCount() + ": " + book.getName());
		}
		
		List list = new ArrayList();
		list.add("Stuff");
		list.add("Apples");
		list.add("Zebras");
		list.sort(null);
		System.out.println(list);
		list.sort(new StringZ2AComparator());
		System.out.println(list);
		
	}

}
