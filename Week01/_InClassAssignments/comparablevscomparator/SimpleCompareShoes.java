package d4.revature.comparablevscomparator;

import java.util.ArrayList;
import java.util.List;

public class SimpleCompareShoes {
	/*
	 * Driver class
	 * */

	public static void main(String[] args) {
		Shoe shoe1 = new Shoe(10, "Nike", 50);
		Shoe shoe2 = new Shoe(8, "Adidas", 48);
		Shoe shoe3 = new Shoe(11, "Hola", 23.6);
		Shoe shoe4 = new Shoe(6, "Bibi", 42);
		
		List <Shoe> list = new ArrayList<>();
		list.add(shoe1);
		list.add(shoe2);
		list.add(shoe3);
		list.add(shoe4);
		
		System.out.println("=======Before Sorting====== \n" + list);
		list.sort(null);
		System.out.println("=======Natural Sorting====== \n" + list);
		list.sort(new ShoesComparator());
		System.out.println("=======Sorted by price====== \n" + list);
		
	}

}
