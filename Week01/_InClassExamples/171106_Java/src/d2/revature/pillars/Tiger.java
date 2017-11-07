package d2.revature.pillars;

public class Tiger extends Cat{
	private String name;
	private int age;
	
	
	
	@Override
	public String species() {
		return "tiger";
	}
	
	@Override
	public String whatAmI() {
		return "Dude... im a tiger";
	}
}
