package d2.revature.strings;

public class EqualsMethod {

	public static void main(String[] args) {
		String s1 = "dogs";
		String s2 = "dogs";
		
		System.out.println((s1 == s2) + "|" + System.identityHashCode(s1));
		System.out.println(s1.equals(s2) + "|" + System.identityHashCode(s2));

		
		Animal a1 = new Animal("Tiger", "Tony");
		Animal a2 = new Animal("Tiger", "Tony");
		
		System.out.println(a1 == a2);
		System.out.println(a1.equals(a2));
	}

}
