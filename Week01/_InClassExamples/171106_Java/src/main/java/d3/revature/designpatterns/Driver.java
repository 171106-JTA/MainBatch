package d3.revature.designpatterns;

public class Driver {

	public static void main(String[] args) {
		Singleton s1 = Singleton.getSingleton();
		Singleton s2 = Singleton.getSingleton();

		
		System.out.println(Singleton.instanceCount);
		System.out.println(System.identityHashCode(s1));
		System.out.println(System.identityHashCode(s2));
		
		System.out.println(s1==s2);
		
		ShapeFactory sf = new ShapeFactory();
		
		Shape shape1 = sf.getShape("CiRcLe");
		Shape shape2 = sf.getShape("TriaNGle");
		Shape shape3 = sf.getShape("Bobbert");
		shape1.draw();
		shape2.draw();
		shape3.draw();
		
	}
	

}
