package d2.revature.strings;

public class TrickyStrings {

	String s;
	public static void main(String[] args) {
		System.out.println(1 + 2 + 3 + 4); //
		System.out.println(1 + 2 + 3 + "4");
		System.out.println(1 + 2 + "3" + "4");
		System.out.println(1 + "2" + 3 + "4");
		System.out.println(1 + "2" + 3 + 4);
		
		System.out.println(1 + "a");
		
		TrickyStrings ts = new TrickyStrings();
		ts.stupidStrings();
		
	}
	public void stupidStrings(){
		//System.out.println(s.length());	//You will ALWAYS get a nullPointerException if you call
		//System.out.println(s);			//any method on a reference that points to null.
		
		s += "yeeup";
		System.out.println(s);
	}

}
