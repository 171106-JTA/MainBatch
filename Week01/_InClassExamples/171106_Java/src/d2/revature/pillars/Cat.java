package d2.revature.pillars;

public abstract class Cat extends Animal{
	/*
	 * The only modifiers you can use for a class are public, abstract, and final, also
	 * just the default modifier.
	 */
	
	@Override
	public String whatAmI() {
		//return super.whatAmI(); //Would call the "I am an animal" parent implentation
		return "I am a cat";
	}
	
	@Override
	public String species() {
		return "Cat";
	}

}
