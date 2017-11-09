package d2.revature.pillars;


/*
 * Interfaces are like abstract classes, except there are a few key differences.
 * -Interfaces are a contract, what ever concrete class implements them, must provide 
 * implementation for the method signatures.
 * -Interfaces, as of java 8+, can now have concrete classes. This is done through one
 * of two ways. Either use a static modifier, or use a new modifier, default.
 * -Compared to Abstract classes, differences include:
 * --You can "implement" as many interfaces as you want.
 * --You can only extend one abstract class at a time.
 * --Abstract classes can have concrete methods by default, whereas interfaces require
 * a keyword for it.
 * --All variables in an interface, default to public, static, and final.
 */
public interface Animalable {
	int i = 0; //Implicitly public, static, final.
	public void walk();
	public void eat();
	void makeNoise(); //This method is actually public.
	public default void randomMethod(){
		System.out.println("Implemented");
	}
	
}
