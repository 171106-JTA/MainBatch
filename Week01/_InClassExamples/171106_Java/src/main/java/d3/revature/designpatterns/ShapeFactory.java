package d3.revature.designpatterns;

public class ShapeFactory {
	/*
	 * A factory design pattern is used to provide a class that serves up a specific object
	 * for a user. A user can use a factory to get new instances of an object that can be configured
	 * for the user before they actually get it.
	 */
	public Shape getShape(String request){
		request = request.toLowerCase();
		if(request.equals("circle")){
			return new Circle();
		}
		if(request.equals("triangle")){
			return new Triangle();
		}
		return null;
	}
}
