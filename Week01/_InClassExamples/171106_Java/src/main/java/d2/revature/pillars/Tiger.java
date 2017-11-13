package d2.revature.pillars;

public class Tiger extends Cat{
	private String name;
	private int age;
	public String word = "tiger";
	
	
	
	@Override
	public String species() {
		return "tiger";
	}
	
	@Override
	public String whatAmI() {
		return "Dude... im a tiger";
	}

	@Override
	public void walk() {
		System.out.println("Prowl on 4 legs.");
		
	}

	@Override
	public void eat() {
		System.out.println("Havin an icy cold bowl of frosted flakes.");
		
	}

	@Override
	public void makeNoise() {
		System.out.println("They're bobbert");
	}
	
	public void tigerStuff(){
		System.out.println("What...?");
	}
}
