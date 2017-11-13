package com.revature.strings;

public class FinalAndStatic {

	public static final void main(String[] args)
	{
		System.out.println(Donut.donutCount);

		Donut donut = new Donut("glazed");		
		Donut donut2 = new Donut("garbage");
		
		System.out.println(Donut.donutCount);
		System.out.println(donut.getDonutCount());
		System.out.println(donut2.getDonutCount());
		
		
	}
}
