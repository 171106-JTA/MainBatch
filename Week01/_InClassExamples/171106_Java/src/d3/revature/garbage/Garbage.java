package d3.revature.garbage;

public class Garbage {
<<<<<<< HEAD
	public static void main(String[] args) {
		/*
		 * Java was hailed as safe language by managin memory for the user. This is 
		 * different from other language like C and C++ where the user had to handle their 
		 * memory themselves.
		 * The JVM has something called the GarbageCollector, which will handle the cleaning up 
		 * of any dead references/objects.
		 * 
		 * Three ways an object is queued up for garbage collection.
		 * - Object goes out of scope (e.g. method ends that object was created in.)
		 * -Object loses all references./
		 * --Reference set to null
		 * -Object reference points to a different object. Previous object will get collected.
		 * 
		 * You cannot force garbage collection you can only request it by System.gc(). 
		 * 
		 * */
		
		EmptyCan can = new EmptyCan(1);
	}
=======

	public static void main(String[] args) {
		/*
		 * Java was hailed as safe language by managing memory for the user. This is
		 * different from other languages like C and C++ where the user had to handle their
		 * memory themselves.
		 * The JVM has something called the GarbageCollector, which will handle the cleaning up
		 * of any dead references/objects.
		 * 
		 * Three ways an object is queued up for garbage collection.
		 * 
		 * Object goes out scope (e.g. method ends that object was created in.)
		 * Object loses all references.
		 * Reference set to null
		 * Object reference points to a different object. Previous object will get collected.
		 * 
		 * You CANNOT force garbage collection. You can only request it via System.gc().
		 */
		
		for(int i = 0; i < 100000000; i++){
			EmptyCan can = new EmptyCan(i);
			System.gc();
		}
	}

>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
}
