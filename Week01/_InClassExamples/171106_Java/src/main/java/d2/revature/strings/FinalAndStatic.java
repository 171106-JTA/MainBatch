package d2.revature.strings;

public final class FinalAndStatic {

	public static final void main(String[] args) {
		/*
		 * A final class cannot be extended.
		 * A final method cannot be inherited.
		 * A final variable cannot be reassigned/changed;
		 */
		final int x = 5;
		
/*		x++;
		x = 6;*/
		int y = x;
		y++;
		
		
		System.out.println(Donut.donutCount);
		
		Donut donut = new Donut("Glazed");
		Donut donut2 = new Donut("Sugarless Garbage");
		
		System.out.println(Donut.getDonutCount()); //2
		System.out.println(donut.getDonutCount()); //2
		System.out.println(donut2.getDonutCount());//2
	}

}
