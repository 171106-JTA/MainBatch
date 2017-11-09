package d4.revature.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.revature.hello.Person;

public class CollectionsExample {
	public static void main(String[] args) {
		System.out.println("=================LISTS======================");

		// array via list
		int[] i1 = { 2, 4, 1, 5, 3 };

		// Array via setup
		int[] i2 = new int[5];
		for (int i = 0; i < i2.length; i++) {
			i2[i] = (i + 1);
		}
		Arrays.sort(i1);
		System.out.println(Arrays.toString(i1));

		/*
		 * Generics: -Generics as denoted by the angle brackets below, are used to
		 * enforce datatypes for a collection. They can also be used to parameterize a
		 * data type for a method For example: ArrayList<T>:: -Generics can help enforce
		 * method safety with type control as well as allow a user to make more dynamic
		 * and flexible methods
		 */
		List<Integer> list = new ArrayList<>();
		list.add(4);
		list.add(1);
		list.add(3);
		list.add(2);
		list.add(5);
		System.out.println("An Array List " + list);
		/*
		 * ::Note: Primitive data types like int, are auto-boxed when placed in the
		 * list.
		 * 
		 */
		for (int i : list) {
			System.out.println(i);
		}

		System.out.println("=============Iterators=======================");

		Iterator it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		it = list.iterator(); // resets the iterator
		/*
		 * Iterators are an object that can be used to traverse a collection.
		 * ListIterators are just like iterators except they can also go backwards. This
		 * is opposed to Iterators ability o only move forwards.
		 */

		ListIterator lit = list.listIterator();
		while (lit.hasNext()) {
			/*
			 * grabs next value, then moves iterator, grabs prev value then move back then
			 * grabs next value then moves iterator
			 */
			System.out.println(lit.next() + " " + lit.previous() + " " + lit.next());
		}
		System.out.println("Pre Shuffle: " + list);
		Collections.shuffle(list);
		System.out.println("Post Shuffle: " + list);
		System.out.println("================Sets=====================");

		HashSet<Integer> set = new HashSet<>();
		set.add(5);
		set.add(1);
		set.addAll(list); // Adds the entirety into a current collection
		System.out.println(set);

		/*
		 * Sets do not allow duplicates and auto sorts contents.
		 * 
		 */
		HashSet<Person> set2 = new HashSet<>();
		set2.add(new Person("Douglas", 25, "What"));
		set2.add(new Person("Adam", 19, "What again"));
		set2.add(new Person("Krug", 29, "lul"));
		set2.add(new Person("Gromash", 39, "Loktar"));
		System.out.println(set2);

		Queue<Integer> queue = new PriorityQueue<>();
		queue.offer(4);
		queue.offer(2);
		queue.offer(3);
		queue.offer(1);
		System.out.println(queue);

		System.out.println("===============MAPS===================");
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "Bobbert");
		map.put(2, "Robbert");
		map.put(4, "Wilbbert");
		map.put(3, "Bert");
		
		System.out.println(map);
		/* Iterable over keysets which means NO duplicates!! 
		 * HashMap key can be null because even though null is not a int, Integer is an
		 * object which means it can have state of null
		 * 
		 * */
		map.put(null, null);
		map.put(null, "Flavablerba");
		System.out.println(map);
		/* In order to iterate through a map, we have to get our hands on something that is 
		 * Actually iterable. Thankfully, maps provides a function to receive all the keys a set. 
		 * A veritable KeySet
		 * */
		System.out.println("==============Iterate trhough a Map====================");
		for (Integer i : map.keySet()) {
			System.out.println(i + ": " + map.get(i));
		}
		
		Hashtable <Integer, String> ht = new Hashtable<>();
		
		// ht.put(2, null);
		/*
		 * Hash Table vs HashMaps 
		 * 	- HashMaps can have null key or values whereas HashTables throws
		 * a null pointer exception for any null input. (Both key and value)
		 * 	- HashTable, is a legacy class. This means that it isnt quite deprecated
		 * but you should be using hashmaps anyways
		 * 	- Also HashTable is Synchronized compared to Hashmap which is not. 
		 */
		
	}
}
