package d4.revature.classexample;

public class Soda implements Comparable<Soda> {
	private String name;
	private String flavor;
	private int calories;
	
	@Override
	public String toString() {
		return "Soda [name=" + name + ", flavor=" + flavor + ", calories=" + calories + "]";
	}
	
	public Soda(String name, String flavor, int calories) {
		super();
		this.name = name;
		this.flavor = flavor;
		this.calories = calories;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlavor() {
		return flavor;
	}
	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}

	@Override
	public int compareTo(Soda soda) {
		return this.getName().compareTo(soda.getName());
	}
	
}
