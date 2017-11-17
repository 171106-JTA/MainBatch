package scratch.paper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableNTor {
	public static void main(String[] args) {
		List<Card> cards = new ArrayList<>();
		cards.add(new Card("Card 1", "UU5", 5, 5, 40.22));
		cards.add(new Card("Bobbert", "WUB3", 10, 2, 100.22));
		cards.add(new Card("Shivan Dragon", "RR4", 6, 6, 1.02));
		cards.add(new Card("Plunderer", "UB1", 2, 1, 0.35));
		cards.add(new Card("Minion", "BG1", 1, 1, 0.22));

		System.out.println("=============Sort by price===================");
		cards.sort(null);
		for (Card card : cards) {
			System.out.println(card.getName() + "||  cost: $ " + card.getCost());
		}
		System.out.println("=============Sort by Card Name=====================");

		Collections.sort(cards, new CardNameComparator());

		for (Card card : cards) {
			System.out.println(card.getName() + "||  cost: $ " + card.getCost());
		}
		System.out.println("=============Sort by Card Name Z-A =====================");

		Collections.sort(cards, new ReverseComparator());

		for (Card card : cards) {
			System.out.println(card.getName() + "||  cost: $ " + card.getCost());
		}

	}
}
