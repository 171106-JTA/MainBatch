package com.revature.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="HONEY_POT")
public class HoneyPot {
	@Id
	@Column(name="HONEY_POT_ID")
	@SequenceGenerator(sequenceName="HONEY_POT_GENERATOR", name="HONEY_POT_GENERATOR")
	@GeneratedValue(generator="HONEY_POT_GENERATOR", strategy=GenerationType.SEQUENCE)
	private int honeypotId;
	
	@Column
	private double volume;
	
	@Column(name="HONEY_AMOUNT")
	private double honeyAmount;
	public HoneyPot() {
		super();
	}
	public HoneyPot(double volume) {
		super();
		this.volume = volume;
	}
	public HoneyPot(int honeypotId, double volume) {
		super();
		this.honeypotId = honeypotId;
		this.volume = volume;
	}
	
	public HoneyPot(double volume, double honeyAmount) {
		super();
		this.volume = volume;
		this.honeyAmount = honeyAmount;
	}
	public HoneyPot(int honeypotId, double volume, double honeyAmount) {
		super();
		this.honeypotId = honeypotId;
		this.volume = volume;
		this.honeyAmount = honeyAmount;
	}
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
	@Override
	public String toString() {
		return "HoneyPot [honeypotId=" + honeypotId + ", volume=" + volume + "]";
	}
}
