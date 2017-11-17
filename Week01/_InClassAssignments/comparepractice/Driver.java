package com.revature.comparepractice;

import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static void main(String[] args) {
	
		List<AnObject> anObjects = new ArrayList<>();
		AnObject anObject;
		
		anObject = new AnObject(1, "Bibbidy", "ydibbiB");
		anObjects.add(anObject);
		anObject = new AnObject(54, "Zippidy", "ydippiZ");
		anObjects.add(anObject);
		anObject = new AnObject(23, "Flippidy", "ydippilF");
		anObjects.add(anObject);
		anObject = new AnObject(49, "Dibbidy", "ydibbiD");
		anObjects.add(anObject);
		
		anObjects.sort(null);
		System.out.println("Comparable------------");
		for(AnObject obj: anObjects) {
			
			System.out.println(obj.getSomething() + " : " + obj.getSomethingElse() + " : " + obj.getNothing());
		}
		
		anObjects.sort(anObject);
		System.out.println("Comparator Normal------------");
		for(AnObject obj: anObjects) {
			System.out.println(obj.getSomething() + " : " + obj.getSomethingElse() + " : " + obj.getNothing());
		}
		
		List<AnotherObject> anotherObjects = new ArrayList<>();
		AnotherObject anotherObject;
		
		anotherObject = new AnotherObject(1, "Bibbidy", "ydibbiB");
		anotherObjects.add(anotherObject);
		anotherObject = new AnotherObject(54, "Zippidy", "ydippiZ");
		anotherObjects.add(anotherObject);
		anotherObject = new AnotherObject(23, "Flippidy", "ydippilF");
		anotherObjects.add(anotherObject);
		anotherObject = new AnotherObject(49, "Dibbidy", "ydibbiD");
		anotherObjects.add(anotherObject);
	
		anotherObjects.sort(anotherObject);
		System.out.println("Comparator Reverse------------");
		for(AnotherObject obj: anotherObjects) {
			
			System.out.println(obj.getSomething() + " : " + obj.getSomethingElse() + " : " + obj.getNothing());
		}
	}
}
