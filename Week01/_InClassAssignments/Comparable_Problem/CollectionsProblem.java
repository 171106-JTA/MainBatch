package d4.revature.collections;

import java.util.ArrayList;
import java.util.List;

public class CollectionsProblem {

	public static void main(String[] args) {
		
		List<Card> cards = new ArrayList<>();
		cards.add(new Card("Goblin Warloeader", "1R", "Battle Cry", 2, 2));
		cards.add(new Card("Terrastadon", "6GG", "When this enters play, destroy three noncreature permanents," +  
				"then for each permanent destroyed this way, its controller gets a 3/3 green Elephant creature coken.", 9, 9)); 
		cards.add(new Card("Capable Duelist", "2W", "Double Strike", 1, 1));
		cards.add(new Card("Dragonlord Ojutai", "4UW", "Flying\n\tAs long as it's not your turn, Dragonlord Ojutai has hexproof.\n\t" +
				"When Dragonlord Ojutai deals combat damage to a player, you may draw three cards, then discard two.", 5, 7));
		cards.add(new Card("Reassembling Skeleton", "B", "1B: Return Reassembling Skeleton from your graveyard to play.", 1, 1)); 
		
		System.out.println("=====UNSORTED CARDS=====");
		int i = 1; 
		for(Card c : cards) {
			System.out.println(i++ + ": " +  c);
		}
		
		System.out.println("\n=====CARDS SORTED BY NAME=====");
		cards.sort(null); 
		i = 1; 
		for(Card c : cards) {
			System.out.println(i++ + ": " +  c);
		}
		
		System.out.println("\n=====ALTERNATIVE SORT: BY CONVERTED MANA COST=====");
		cards.sort(new CardCMCComparator());
		i = 1; 
		for(Card c : cards) {
			System.out.println(i++ + ": " +  c);
		}
				
		
		List<String> strings = new ArrayList<>();
		strings.add("I'm a string"); 
		strings.add("This is a string");
		strings.add("Hello, strings!");
		strings.add("Oh look, a string!");
		strings.add("Who invited this string?");
		strings.add("Shoe strings");
		
		System.out.println("\n=====UNSORTED STRINGS=====");
		for(String s : strings) {
			System.out.println(s);
		}
		
		System.out.println("\n=====SORTING STRINGS====="); 
		strings.sort(null);
		for(String s : strings) {
			System.out.println(s);
		}
		
		
		System.out.println("\n=====SORTING IN REVERSE=====");
		strings.sort(new StringReverseComparator());
		for(String s : strings) {
			System.out.println(s);
		}
		
		
	}

}
