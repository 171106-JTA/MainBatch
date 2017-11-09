package n4.revature.inclass;

import java.util.ArrayList;
import java.util.List;

import n4.revature.collections.BookNameComparator;

public class Driver {

	public static void main(String[] args) {
		
		//Create list of PersonName objects
		List <PersonName> personList = new ArrayList<>();

		//Add PersonName objects to the list
		personList.add(new PersonName());
		personList.add(new PersonName("KP", "Saini", 26));
		personList.add(new PersonName("Michal", "Jackson", 50));
		personList.add(new PersonName("Elvis", "Presley", 70));
		
		//Print list with overriden toString() method
		System.out.println("===Natural Ordering of List using toString()====");
		System.out.println(personList);
		
		//Display list with the compareTo method in the PersonName class by calling null in sort method
		personList.sort(null);
		System.out.println("===Comparing By Age====");
		for(PersonName person : personList) {
			System.out.println(person.getLastName() + ", " + person.getFirstName() 
		+ " - " + person.getAge());
		}
		
		//Display list with defined comparitor in NameComparitor
		//Last name in reverse alphabetical order
		personList.sort(new NameComparitor());
		System.out.println("=======Comparing by last name in reverse alphabetical order=====");
		for(PersonName person : personList) {
			System.out.println(person.getLastName() + ", " + person.getFirstName() 
		+ " - " + person.getAge());
		}
	}
}
