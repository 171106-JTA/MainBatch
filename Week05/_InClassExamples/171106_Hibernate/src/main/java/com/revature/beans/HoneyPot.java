package com.revature.beans;

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
@NamedNativeQueries({
	@NamedNativeQuery(name="getSmallHoneypots", 
			query= "SELECT * FROM HoneyPOt WHERE volume < :maxsize",
			resultClass=HoneyPot.class)
})
/*
 * You can use Native in lieu of HQL, by invoking native SQL query names.
 * Native SQL is simply the literal SQL language for your driver.
 * this is frowned upon as it more tightly couples application to a specific
 * language.
 */

@Entity
@Table(name="HONEYPOT")
public class HoneyPot {
	
	@Id
	@Column(name="HONEYPOT_ID")
	@SequenceGenerator(name="HONEYPOT_SEQ", sequenceName="HONEYPOT_SEQ")
	@GeneratedValue(generator="HONEYPOT_SEQ", strategy=GenerationType.SEQUENCE)
	private int honeypotId;
	
	@Column
	private double volume;
	
	@Column(name="HONEY_AMOUNT")
	private double honeyAmount;
	
	
	public double getHoneyAmount() {
		return honeyAmount;
	}
	public void setHoneyAmount(double honeyAmount) {
		this.honeyAmount = honeyAmount;
	}
	public int getHoneypotId() {
		return honeypotId;
	}
	public void setHoneypotId(int honeypotId) {
		this.honeypotId = honeypotId;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}

	
	
	public HoneyPot(int honeypotId, double volume, double honeyAmount) {
		super();
		this.honeypotId = honeypotId;
		this.volume = volume;
		this.honeyAmount = honeyAmount;
	}
	public HoneyPot(double volume, double honeyAmount) {
		super();
		this.volume = volume;
		this.honeyAmount = honeyAmount;
	}
	public HoneyPot() {
	}
	@Override
	public String toString() {
		return "HoneyPot [honeypotId=" + honeypotId + ", volume=" + volume + ", honeyAmount=" + honeyAmount + "]";
	}

	
	
	
	
	
	
}
