package d4.revature.collections;

import java.util.Comparator;

<<<<<<< HEAD
public class BookNameComparator implements Comparator<Book> {
	@Override 
	public int compare(Book b1, Book b2) {
		return b1.getName().compareTo(b2.getName());
	}
	
=======
public class BookNameComparator implements Comparator<Book>{

	@Override
	public int compare(Book b1, Book b2) {
		return b1.getName().compareTo(b2.getName());
	}

>>>>>>> 2d976666d41267e0920e072e3c4d76944d25cc5e
}
