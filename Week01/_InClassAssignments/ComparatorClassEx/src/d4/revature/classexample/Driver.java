package d4.revature.classexample;

import java.util.ArrayList;
import java.util.List;



public class Driver {
	
	public static void main(String[] args) {
		List<Soda> list= new ArrayList<>();
		list.add(new Soda("Dr. Pepper", "Cherry", 250));
		list.add(new Soda("Sprite", "Lime", 200));
		list.add(new Soda("Coke", "Cola", 300));
		list.add(new Soda("Pepsi", "Garbage", 400));
		
		list.sort(null);
		System.out.println("Sort by Name\n");
		for(Soda soda: list)
		{
			System.out.println(soda.getName());
		}
		list.sort(new ReverseSodaComparator());
		System.out.println("Sort by Reverse Name\n");
		for(Soda soda: list)
		{
			System.out.println(soda.getName());
		}
		list.sort(new SodaCaloryComparator());
		System.out.println("Sort by Calories\n");
		for(Soda soda: list)
		{
			System.out.println(soda.getName());
		}
		
	}
	
	
	

}
