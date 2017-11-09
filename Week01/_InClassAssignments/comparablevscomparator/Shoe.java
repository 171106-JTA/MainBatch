package d4.revature.comparablevscomparator;

public class Shoe implements Comparable<Shoe> {
	private int size;
	private String brand;
	private double price;
	
	
	public Shoe(int size, String brand, double price) {
		this.size = size;
		this.brand = brand;
		this.price = price;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Shoe [size=" + size + ", brand=" + brand + ", price=" + price + "]";
	}


	//Natural order is by brand
	@Override
	public int compareTo(Shoe shoe) {
		return this.getBrand().compareTo(shoe.getBrand());
	}

	/*public int compareTo(Shoe shoe) {
		if (this.price < shoe.getPrice())
				return -1;
		else if (this.price < shoe.getPrice())
			return 1;
		return 0;
	}
	*/


}
