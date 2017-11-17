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

<<<<<<< HEAD
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
=======
import d1.revature.hello.Person;

public class CollectionsExample {

	public static void main(String[] args) {
		System.out.println("=====LISTS=====");
		
		//Array via list
		int[] i1 = {2,1,5,4,3};
		
		//Array via excrutiating setup
		int[] i2 = new int[5];
		for(int i = 0; i < i2.length; i++){
			i2[i] = (i+1);
		}
		
		//i1[5] = 6;
		
		Arrays.sort(i1);
		System.out.println("A normal array: " + Arrays.toString(i1));
		
		/*
		 * Generics:
		 * -Generics, as denoted by the angle brackets below, are used to enforce
		 * datatypes for a collection. They can also be used to parameterize a
		 * datatype for a method. For example: <T>
		 * -Generics can help enforce method safety with type control, as well as 
		 * allow a user to make more dynamic and flexible methods.
		 */
		/*
		 * Note: Primitive datatypes, like int, are autoboxed when placed in
		 * the list.
>>>>>>> 2d976666d41267e0920e072e3c4d76944d25cc5e
		 */
		List<Integer> list = new ArrayList<>();
		list.add(4);
		list.add(1);
		list.add(3);
		list.add(2);
		list.add(5);
<<<<<<< HEAD
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
=======
		//NOTE: I am adding elements to the array without having specified
		//a size for it.
		System.out.println("An arraylist" + list);
		System.out.println(list.contains(3));
		//Brackets are optional for single line conditionals
		if(list.contains(2))
			list.remove(2);
		
		int ii = 2;
		System.out.println(ii<2 ? "true"  : "false");
		System.out.println(5%2);
		
		for(int i : list){
			System.out.println(i);
		}
		
		System.out.println("=====Iterators=====");
		
		Iterator it = list.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
			
		}
		it = list.iterator();  //resets the iterator
		
		/*
		 * Iterators are an object that can be used to traverse a collection.
		 * ListIterators are just like iterators except, they can also go backwards.
		 * This is opposed to Iterators ability to only move forward.
		 */
		
		ListIterator lit = list.listIterator();
		while(lit.hasNext()){
>>>>>>> 2d976666d41267e0920e072e3c4d76944d25cc5e
			System.out.println(lit.next() + " " + lit.previous() + " " + lit.next());
		}
		System.out.println("Pre Shuffle: " + list);
		Collections.shuffle(list);
		System.out.println("Post Shuffle: " + list);
<<<<<<< HEAD
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

=======
		
		System.out.println("=====Sets=====");
		HashSet <Integer>set = new HashSet<>();
		set.add(5);
		set.add(1);
		set.addAll(list); //Adds the entirety into a current collection.
		System.out.println(set);
		/*
		 * Sets do not allow duplicates, and they automatically sort contents.
		 */
		
		HashSet<Person> set2 = new HashSet<>();

		System.out.println("=====queues=====");
		
>>>>>>> 2d976666d41267e0920e072e3c4d76944d25cc5e
		Queue<Integer> queue = new PriorityQueue<>();
		queue.offer(4);
		queue.offer(2);
		queue.offer(3);
		queue.offer(1);
		System.out.println(queue);
<<<<<<< HEAD

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
=======
		for(int i = queue.size(); i>0; i--){
			System.out.println(queue.poll());
		}
		
		System.out.println("=====MAPS=====");
		
		Map <Integer,String>map = new HashMap<>();
		map.put(1, "Bobbert");
		map.put(2, "Robert");
		map.put(3, "Wilbert");
		map.put(4, "Bert");
		
		System.out.println(map);
		
		map.put(2, "ryan");
		System.out.println(map);
		
		map.put(null, "What?");
		map.put(6, null);
		map.put(null, null);
		System.out.println(map);
		
		System.out.println("=====Iterate through a map====");
		/*
		 * In order to iterate through a map, we have to get our hands on something
		 * that is actually iterable. Thankfully, maps provide a function to receive
		 * all the keys a set. A veritable KeySet
		 */
		//We take the keyset, and grab values based off of each key.
		for(Integer i: map.keySet()){
			System.out.println(i + ": " + map.get(i));
		}
		
		Hashtable <Integer, String>ht = new Hashtable<>();
		//ht.put(2, null); Throws a null pointer exception
		
		/*
		 * Difference between a hashmap and hashtable:
		 * -Hashmaps can have a null key or value, whereas a hashtable throws
		 * a null pointer exception for any null input. (Both key and value)
		 * -Hashtable, is a legacy class. This means that it isn't quite 
		 * deprecated, but you should be using hashmap anyways. 
		 * -Hashtable is synchronized, as oppose to hashmap which is not.
		 */
		
		
		
	}

>>>>>>> 2d976666d41267e0920e072e3c4d76944d25cc5e
}
