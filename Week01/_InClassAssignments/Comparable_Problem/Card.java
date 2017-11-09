package d4.revature.collections;

public class Card implements Comparable<Card>{

	private String name; 
	private String cmc;
	private String cardText;
	private int power;
	private int tough;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCmc() {
		return cmc;
	}
	public void setCmc(String cmc) {
		this.cmc = cmc;
	}
	public String getCardText() {
		return cardText;
	}
	public void setCardText(String cardText) {
		this.cardText = cardText;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getTough() {
		return tough;
	}
	public void setTough(int tough) {
		this.tough = tough;
	}
	
	
	
	@Override
	public String toString() {
		int paddingAmt = 32 - this.name.length();
		String padding = ""; 
		while(paddingAmt > 0) {
			padding += "\t"; 
			paddingAmt -= 8; 
		}
		 
		return this.name + padding + this.cmc + "\t" + power + "/" + tough + "\n\t" + this.cardText;
//		
//		return "Card [name=" + name + ", cmc=" + cmc + ", cardText=" + cardText + ", power=" + power + ", tough="
//				+ tough + "]";
	}
	public Card(String name, String cmc, String cardText, int power, int tough) {
		super();
		this.name = name;
		this.cmc = cmc;
		this.cardText = cardText;
		this.power = power;
		this.tough = tough;
	}
	
	@Override
	public int compareTo(Card c) {
		return this.getName().compareTo(c.getName());
		
	} 
	
	
	
	
	
	
}
