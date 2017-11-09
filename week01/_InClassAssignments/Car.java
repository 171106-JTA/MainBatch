package d3.revature.collections;

/**
* Car.
*/
public class Car implements Comparable<Car> {
	
	private int cost, year;
	private String make, model, color;
	
	public Car(String make, String model, String color, int cost, int year) {
		this.cost = cost;
		this.year = year;
		this.make = make;
		this.model = model;
		this.color = color;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
	@Override
	public String toString() {
		return "Car [cost=" + cost + ", year=" + year + ", make=" + make + ", model=" + model + ", color=" + color
				+ "]";
	}
	/**
	 * Lets sort by cost of car
	 */
	@Override
	public int compareTo(Car car) {
		
		if (this.cost < car.cost) {
			return -1;
		} else if (this.cost > car.cost) {
			return 1;
		}
		return 0;
	}
	

}