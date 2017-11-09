package d3.revature.designpatterns;

public class Singleton {
<<<<<<< HEAD

=======
	
	/*
	 * A singleton is a design pattern that aims to create an object that may only ever have
	 * ONE instance.
	 */
	
	static int instanceCount = 0;
	private static Singleton singleton; //We don't want users directly accessing our singleton
	
	
	private Singleton(){
		instanceCount++;
	}
	
	public static Singleton getSingleton(){
		if(singleton==null){
			singleton = new Singleton();
		}
		return singleton;
	}
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
}
