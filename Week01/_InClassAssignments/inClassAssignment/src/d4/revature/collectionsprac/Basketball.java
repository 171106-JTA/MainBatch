package d4.revature.collectionsprac;

import java.util.ArrayList;
import java.util.List;

public class Basketball {

	public static void main(String[] args) {
		
		List <BasketballPlayer> players = new ArrayList<>();
		players.add(new BasketballPlayer("Porzinigs", "Power Forward", 30.0));
		players.add(new BasketballPlayer("Ntilikina", "Point Guard", 4.7));
		players.add(new BasketballPlayer("Hardawy Jr.", "Shooting Guard", 20.7));
		players.add(new BasketballPlayer("Antetekoumpo", "Forward", 31.5));
		
		System.out.println("Natural Order Highest PPG");
		players.sort(null);
		System.out.println(players.toString() + "\n");
		
		System.out.println("Sorts different");
		players.sort(new BasketballComparator());
		System.out.println(players.toString() + "\n" );
		
		System.out.println("Reverse Alphabetical");
		players.sort(new BasketballComparator2());
		System.out.println(players.toString() + "\n");

	}
	
	
}
