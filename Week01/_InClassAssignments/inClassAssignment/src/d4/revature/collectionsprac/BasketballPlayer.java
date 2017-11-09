package d4.revature.collectionsprac;

import d4.revature.collections.Book;

public class BasketballPlayer implements Comparable<BasketballPlayer> {
	private String player;
	private String position;
	private double ppg;
	
	
	public String getPlayer() {
		return player;
	}


	public void setPlayer(String player) {
		this.player = player;
	}

	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public double getPpg() {
		return ppg;
	}


	public void setPpg(double ppg) {
		this.ppg = ppg;
	}

	@Override
	public String toString() {
		return "BasketballPlayer [player=" + player + ", position=" + position + ", ppg=" + ppg + "]";
	}

	public BasketballPlayer(String player, String position, double ppg) {
		super();
		this.player = player;
		this.position = position;
		this.ppg = ppg;
	}


	@Override
	public int compareTo(BasketballPlayer bp) {
		if(this.getPpg() < bp.getPpg())	
			return -1;
		else if (this.getPpg()>bp.getPpg())
			return 1;
		return 0;
		
	}

	
	
	

}
