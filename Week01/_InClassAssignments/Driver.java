package d3.revature.collections;

import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static void main(String[] args) {

		Car car1 = new Car("Toyota", "Camry", "Red", 30000, 2018);
		Car car2 = new Car("Lexus", "RX", "White", 40000, 2018);
		Car car3 = new Car("Honda", "Pilot", "Blue", 25000, 2015);
		Car car4 = new Car("Hyundai", "Azera", "Red", 28000, 2017);
		Car car5 = new Car("Toyota", "Highlander", "Black", 25000, 2018);
		Car car6 = new Car("Ford", "Mustang", "Yellow", 70000, 2017);
		Car car7 = new Car("Chevy", "Silverado", "Grey", 56000, 2018);
		
		List<Car> cars = new ArrayList<>();
		cars.add(car1);
		cars.add(car2);
		cars.add(car3);
		cars.add(car4);
		cars.add(car5);
		cars.add(car6);
		cars.add(car7);
		
		System.out.println(cars); //sorted naturally by price
		cars.sort(new CarComparator()); //now going to sort by model name (alphebetically)
		System.out.println(cars); 
	}
}