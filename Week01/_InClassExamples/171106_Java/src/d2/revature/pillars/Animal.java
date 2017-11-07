package d2.revature.pillars;


/*
 * An abstract class is a class that cannot be instantiated.
 * An abstract class CAN have concrete methods AND abstract methods
 * -An abstract method is a method that does not have implementation
 * -Implementation is reserved for a subclass to handle.
 * -It only enforces the requirement that that specific method exists down the line.
 */
public abstract class Animal {
	
	public String whatAmI(){
		return "I am an animal!";
	}
	public abstract String species();
	
}
