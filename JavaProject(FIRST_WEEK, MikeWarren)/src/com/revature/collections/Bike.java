package com.revature.collections;

public class Bike implements Comparable<Bike>{
	
	private String brand;
	private String name;
	private int mileage;
	private int speeds;

	public Bike(String brand, String name, int mileage, int speeds) {
		super();
		this.brand = brand;
		this.name = name;
		this.mileage = mileage;
		this.speeds = speeds;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getMileage() {
		return mileage;
	}
	
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
	
	public int getSpeeds() {
		return speeds;
	}
	
	public void setSpeeds(int speeds) {
		this.speeds = speeds;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Bike [brand=" + brand + ", name=" + name + ", mileage=" + mileage + ", speeds=" + speeds + "]";
	}

	@Override
	public int compareTo(Bike o) {
		// first comes brand
		int compare = this.brand.compareTo(o.brand);
		if (compare != 0) return compare;
		// then comes name
		compare = this.name.compareTo(o.name);
		if (compare != 0) return compare;
		// then comes speed
		compare = Integer.compare(this.speeds, o.speeds);
		if (compare != 0) return compare;
		// then comes mileage
		return Integer.compare(this.mileage, o.mileage);
	}
	
	
	
}
