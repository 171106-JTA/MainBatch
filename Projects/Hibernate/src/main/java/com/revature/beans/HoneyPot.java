package main.java.com.revature.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@NamedQueries({
	@NamedQuery(name="getAllHoneypots", query="FROM HoneyPot")
})
@NamedNativeQueries({ // allows for native SQL queries, will make application more tightly coupled because it is specific language so is not a good idea
	@NamedNativeQuery(name="getSmallHoneypots", query="SELECT * FROM Honeypot WHERE volume < :maxsize", resultClass=HoneyPot.class)
})
@Entity
@Table(name="HONEY_POT")
public class HoneyPot {
	@Id
	@Column(name="HONEYPOT_ID")
	@SequenceGenerator(sequenceName="HONEYPOT_SEQ", name="HONEYPOT_SEQ")
	@GeneratedValue(generator="HONEYPOT_SEQ", strategy=GenerationType.SEQUENCE)
	private int honeypotid;
	@Column
	private double volume;
	@Column
	private double amount;
	public HoneyPot() {
		super();
	}
	public HoneyPot(double volume) {
		super();
		this.volume = volume;
	}
	public HoneyPot(double volume, double amount) {
		super();
		this.volume = volume;
		this.amount = amount;
	}
	public HoneyPot(int honeypotid, double volume, double amount) {
		super();
		this.honeypotid = honeypotid;
		this.volume = volume;
		this.amount = amount;
	}
	public int getHoneypotid() {
		return honeypotid;
	}
	public void setHoneypotid(int honeypotid) {
		this.honeypotid = honeypotid;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "HoneyPot [honeypotid=" + honeypotid + ", volume=" + volume + ", amount=" + amount + "]";
	}
}
