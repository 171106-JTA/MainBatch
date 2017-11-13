package com.revature.collections;

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
		System.out.println("===========LISTS===========");
		
		// array via list
		int[] i1 = {2,1,5,4,3};
		
		// array via excruciating setup
		int[] i2 = new int[5];
		for (int i = 0; i < i2.length; i++)
		{
			i2[i] = i + 1;
		}
		
		Arrays.sort(i1);
		System.out.println("A normal array: " + Arrays.toString(i1));
		
		/*
		 * Generics :
		 * 	• Generics, as denoted by the angle brackets below, are used to enforce data types for a collection. They
		 * 		can also be used to parameterize a data type for a method. For example: <T>
		 *	• Generics can help enforce method safety with type control, as well as allow a user to make more dynamic 
		 *		and flexible methods. 
		 */
		/*
		 * NOTE: primitive data types, like int, are autoboxed when placed in the list. 
		 */
		List<Integer> list = new ArrayList<>();
		list.add(4);
		list.add(1);
		list.add(3);
		list.add(2);
		list.add(5);
		// NOTE : I'm adding elements to an array without having specified a size for it.
		System.out.println("An ArrayList" + list);
		System.out.println(list.contains(3));
		// brackets are optional for single line conditionals.
		if (list.contains(2)) list.remove(2);
		
//		list.contains(1) ? System.out.println(true) : System.out.println(false);
		System.out.println(list.contains(1) ? "List contains 1" : "List does not contain 1");
		
		for (int i : list)
		{
			System.out.println(i);
		}
		
		System.out.println("=======ITERATORS========");
		
		Iterator it = list.iterator();
		
		while (it.hasNext())
		{
			System.out.println(it.next());
			
		}
		it = list.iterator(); // resets the iterator
		/*
		 * Iterators are an object that can be used to traverse a collection.
		 * ListIterators are just like iterators except they can also go backwards.
		 * This is opposed to Iterators ability to only move forwards.
		 */
		
		ListIterator lit = list.listIterator();
		while (lit.hasNext())
		{
			System.out.println(lit.next() + " "  + lit.previous() + " " + lit.next());
		}
		System.out.println("Pre shufle: " + list);
		Collections.shuffle(list);
		System.out.println("Post shuffle: " + list);
		
		System.out.println("=========SETS========");
		HashSet<Integer> set = new HashSet();
		set.add(5);
		set.add(1);
		set.addAll(list);	// adds the entirety of a collection into a current collection
		System.out.println(set);
		/*
		 * Sets do not allow duplicates, and they automatically sort contents. 
		 */
		
		HashSet<Person> set2 = new HashSet();

		System.out.println("========QUEUES=========");
		
		Queue<Integer> queue = new PriorityQueue<>();
		queue.offer(4);
		queue.offer(2);
		queue.offer(3);
		queue.offer(1);
		System.out.println(queue);
		for (int i = queue.size(); i > 0; i--)
		{
			System.out.println(queue.poll());
		}
		
		System.out.println("=========MAPS===========");
		
		Map<Integer, String> map = new HashMap<>();
		map.put(1,"Bobbert");
		map.put(2,"Robert");
		map.put(3,"Wilbert");
		map.put(4,"Bert");
		
		System.out.println(map);
		
		map.put(2, "Ryan");
		System.out.println(map);
		
		map.put(null,  "What?");
		map.put(6, null);
		map.put(null, null);
		
		System.out.println(map);
		
		System.out.println("=====Iterate thru a map=====");
		/* 
		 * In order to iterate thru a map, we have to get our hands on something that is actually iteratable. 
		 * Thankfully, maps provide a function to receive all the keys in a set. A veritable KeySet
		 */
		// we take the keyset, and grab values based off of each key. 
		for (Integer i : map.keySet()) {
			System.out.println(i + ": " + map.get(i));
		}
		
		Hashtable<Integer, String> ht = new Hashtable<>();
//		ht.put(2, null);	// throws NullPointerException
		
		/*
		 * Difference between HashMap and Hashtable: 
		 * 	• HashMaps can have null key or value, whereas a Hashtable throws null pointer exception for any null input. 
		 * 		(key and value).
		 * 	• Hashtable is a legacy class. This means that it isn't quite deprecated, but you should be using HashMap 
		 * 		anyways.
		 * 	• Hashtable is synchronized, as opposed to HashMap, which is not.
		 */
		
		
	}
}
