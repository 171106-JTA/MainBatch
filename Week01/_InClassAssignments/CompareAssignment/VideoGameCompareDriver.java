package d4.revature.collections;

import java.util.ArrayList;
import java.util.List;

public class VideoGameCompareDriver {
	
	public static void main(String[] args) {
		List<VideoGame> games = new ArrayList<>();
		List<String> s = new ArrayList<>();
		
		s.add("Apple");
		s.add("Banana");
		s.add("Avocado");
		s.add("Pear");
		s.add("Soursop");
		s.add("Zruit");
		
		games.add(new VideoGame(60, "Diablo IV", 2018));
		games.add(new VideoGame(4, "Sonic: Underground", 2000));
		games.add(new VideoGame(59, "Elder Scrolls VI: Morrowsky", 2019));
		games.add(new VideoGame(1, "Bobbert's Revenge", 2001));
		games.add(new VideoGame(3, "Half-Life 3", 3020));
		
		//natural order: publish date
		games.sort(null);
		
		for(VideoGame game:games) {
			System.out.println("Date: " + game.getPublishingDate() + " - " + game.getTitle());
		}
		
		System.out.println("===Price===");
		System.out.println("Before\n");
		for(VideoGame game: games) {
			System.out.println("$" + game.getPrice() + " : " + game.getTitle());
		}
		
		games.sort(new VideoGamePriceComparator());
		
		System.out.println("\nAfter");
		for(VideoGame game: games) {
			System.out.println("$" + game.getPrice() + " : " + game.getTitle());
		}
		
		System.out.println("===============");
		System.out.println("\nFruits Strings in Reverse");
		
		s.sort(new ReverseStringComparator());
		
		for(String str: s) {
			System.out.println(str);
		}
	}
	
	
}
