package com.revature.pillars;

public class Tiger extends Cat {
	private String name;
	private int age;
	
	public String word = "Tiger";
	
	@Override
	public String species() {
		return "tiger";
	}
	
	@Override
	public String whatAmI() {
		return "Dude... I'm a tiger";
	}

	@Override
	public void walk() {
		System.out.println("Prowl on 4 legs.");
		
	}

	@Override
	public void eat() {
		System.out.println("Having frosted flakes");
		
	}

	@Override
	public void makeNoise() {
		System.out.println("They're....ggggrrreaaatttt!");
		
	}
	
	public void tigerStuff()
	{
		System.out.println("What...!?");
	}
}
