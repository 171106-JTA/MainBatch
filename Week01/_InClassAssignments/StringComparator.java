package d4.revature.collections;

public class StringComparator implements Comparator<String>{
	
	@Override
	public int compare(String b1, String b2) {
		return -1*(b1.getName().compareTo(b2.getName()));
	}

}
