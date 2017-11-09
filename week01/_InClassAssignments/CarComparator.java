package d3.revature.collections;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

	/**
	 * Let's sort by make
	 */
	@Override
	public int compare(Car car1, Car car2) {
		
		return car1.getMake().compareTo(car2.getMake());
	}

}