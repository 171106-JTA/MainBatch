package com.revature.pillars;

public class Driver {

	public static void main(String[] args) {
		Tiger tigger = new Tiger();
		tigger.eat();
		tigger.tigerStuff();
		
		System.out.println("==========Tiger1===========");
		Animal tiger1 = new Tiger();
		System.out.println(tiger1.whatAmI());
		tiger1.eat();
		
		// Can't use tigerStuff() because tiger1 is still considered Animal
/*		tiger1 = (Tiger)tiger1;
		tiger1.tigerStuff();*/
		((Tiger)tiger1).tigerStuff();
		
		System.out.println(tiger1.word);
		System.out.println(((Tiger)tiger1).word);
		
		
		Tiger tigerParent = new Tiger();
		TigerCub tigerCub = new TigerCub();
		
		tigerParent = tigerCub;	// upcasting
		tigerCub = (TigerCub)tigerParent;	// downcasting
	}

}
