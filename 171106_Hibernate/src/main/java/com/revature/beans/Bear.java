package com.revature.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity	// designates the class as a persistent class
@Table(name="BEAR") // what table it represents
public class Bear {
	
	@Id // designates a property as the primary key
	@Column(name="BEAR_ID")
	// set up the sequence for incrementing id's for bears
	@SequenceGenerator(sequenceName="BEAR_SEQ", name="BEAR_SEQ")
	@GeneratedValue(generator="BEAR_SEQ", strategy=GenerationType.SEQUENCE)
	private int bearId;
	
	// by default, fetch types are lazy
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="BEAR_HOME") // named column bear home, and designated as a foreign key
	private Cave dwelling;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="HONEYPOT_ID")
	private HoneyPot honeyPot;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="PARENT_CUB", 
		joinColumns=@JoinColumn(name="BEAR_ID"),
		inverseJoinColumns=@JoinColumn(name="CUB_ID"))
	private Set<Bear> bearCubs;
	
	@Column(name="BEAR_COLOR")
	private String bearColor;
	
	private double weight;
	
	
	private double height; // weight in kilos
	
	
	public Bear() {
		this.bearCubs = new HashSet<>();
	}


	public Bear(int bearId, Cave dwelling, HoneyPot honeyPot, Set<Bear> bearCubs, String bearColor, double weight,
			double height) {
		super();
		this.bearId = bearId;
		this.dwelling = dwelling;
		this.honeyPot = honeyPot;
		this.bearCubs = bearCubs;
		this.bearColor = bearColor;
		this.weight = weight;
		this.height = height;
	}


	/**
	 * 
	 * @param dwelling
	 * @param honeyPot
	 * @param bearCubs
	 * @param bearColor
	 * @param weight
	 * @param height
	 */
	public Bear(Cave dwelling, HoneyPot honeyPot, String bearColor, double weight, double height) {
		this();
		this.dwelling = dwelling;
		this.honeyPot = honeyPot;
		this.bearColor = bearColor;
		this.weight = weight;
		this.height = height;
	}
	

	

	public int getBearId() {
		return bearId;
	}

	public void setBearId(int bearId) {
		this.bearId = bearId;
	}

	public Cave getDwelling() {
		return dwelling;
	}

	public void setDwelling(Cave dwelling) {
		this.dwelling = dwelling;
	}

	public HoneyPot getHoneyPot() {
		return honeyPot;
	}

	public void setHoneyPot(HoneyPot honeyPot) {
		this.honeyPot = honeyPot;
	}

	public Set<Bear> getBearCubs() {
		return bearCubs;
	}

	public void setBearCubs(Set<Bear> bearCubs) {
		this.bearCubs = bearCubs;
	}

	public String getBearColor() {
		return bearColor;
	}

	public void setBearColor(String bearColor) {
		this.bearColor = bearColor;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
}
