package d4.rev.mani_comparable_comparator;

import java.util.ArrayList;
import java.util.List;



public class Watch implements Comparable<Watch> {
	String brand; 
	String color; 
	int cost; 
	
	public Watch (String brand, String color, int cost){
		this.brand = brand;
		this.color = color;
		this.cost = cost;
	}
	
	
	//BRAND
	public String getBrand() {
		return brand;
	}	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	//COLOR
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	//COST
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	//toString
	@Override
	public String toString() {
		return "Brand: " + getBrand() + "\tColor: " + getColor() + "\tCost: $" + getCost() + "\t"; 
	}
	
	//defines Comparable natural ordering (by brand)
	@Override
	public int compareTo(Watch w2) {
		//what if natural ordering is by string? do use string's compareTo?
		return this.getBrand().compareTo(w2.getBrand());		
	}

/*  should be in own class
	//defines Comparator custom ordering (by color)
	@Override
	public int compare(Watch w1, Watch w2) {
		return w1.getColor().compareTo(w2.getColor());
	}

	//Comparator that sorts strings from Z-A
	public int reverseCompare(Watch w1, Watch w2) {
		return w1.getBrand().compareTo(w2.getBrand())*-1; 
	}
	
*/	
	

	//MAIN
	public static void main(String[] args) {
		Watch w1 = new Watch("MVMT", "taupe", 145);
		Watch w2 = new Watch("Rolex", "black", 22170);
		Watch w3 = new Watch("Fossil", "mauve", 62);
		Watch w4 = new Watch("KateSp", "grey", 195);
		
		List<Watch> list = new ArrayList<>();
		list.add(w1);
		list.add(w3);
		list.add(w2);
		list.add(w4);
		
		
		//natural
		list.sort(null);
		System.out.println("NATURAL (by brand)");
		System.out.println(list.toString());
		
		//different 
		list.sort(new CompareDifferent());
		System.out.println("\nDIFFERENT (by cost)");
		for (Watch w : list) {
			System.out.println(w.toString());
		}
		
		//reverse
		list.sort(new CompareReverse());
		System.out.println("\nREVERSE (Z-A)");
		for (Watch w : list) {
			System.out.println(w.toString());
		}
		
	}
}
