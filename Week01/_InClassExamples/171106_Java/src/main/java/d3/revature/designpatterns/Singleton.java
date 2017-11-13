package d3.revature.designpatterns;

public class Singleton {

	
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
}
