import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StringComp implements Comparator<String>{

	@Override
	public int compare(String s1, String s2) {
		String min;
		min = s1.length() < s2.length() ? s1 : s2;
		for(int i = 0; i < min.length(); i++) {
			if(s1.trim().toLowerCase().charAt(i) != s2.trim().toLowerCase().charAt(i)) {
				return s1.toLowerCase().charAt(i) > s2.toLowerCase().charAt(i) ? -1 : 1;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		List<String> list = Arrays.asList("Rat", "Zoa", "Zoaa", "zAd", "bat", "cat", "dog");
		Collections.sort(list, new StringComp());
		System.out.println(list);

	}
}
