package scratch.paper;

public class Card implements Comparable<Card> {
	private String name;
	private String manaCost;
	private int power;
	private int toughness;
	private double cost;
	
	public Card(String name, String manaCost, int power, int toughness, double cost) {
		super();
		this.name = name;
		this.manaCost = manaCost;
		this.power = power;
		this.toughness = toughness;
		this.cost = cost;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManaCost() {
		return manaCost;
	}
	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getToughness() {
		return toughness;
	}
	public void setToughness(int toughness) {
		this.toughness = toughness;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(Card card) {
		if(this.getCost()< card.getCost()) {
			return -1;
		}else if(this.getCost() > card.getCost()) {
			return 1;
		}
		return 0;
	}
}
