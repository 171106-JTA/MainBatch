package d2.revature.pillars;


/*
 * Interfaces are like abstract classes, except there are a few key differences.
 * -Interfaces are a contract, what ever concrete class implements them, must provide 
 * implementation for the method signatures.
 * -
 */
public interface Animalable {
	public void walk();
	public void eat();
	public void makeNoise();
}
