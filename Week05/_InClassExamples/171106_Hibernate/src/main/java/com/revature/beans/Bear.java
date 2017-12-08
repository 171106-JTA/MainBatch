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

@Entity //Designates the class as a persistent class.
@Table(name="BEAR") //Designate what table it represents
public class Bear {
	
	@Id //Designates a property as the primary key
	@Column(name="BEAR_ID")
	//Set up the sequence for incrementing id's for bears
	@SequenceGenerator(sequenceName="BEAR_SEQ", name="BEAR_SEQ")
	@GeneratedValue(generator="BEAR_SEQ", strategy=GenerationType.SEQUENCE)
	private int bearId;
	
	//By default, fetchtypes are LAZY
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="BEAR_HOME") //Named column bear home, and designated as 
	//a foreign key.
	private Cave dwelling;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="HONEYPOT_ID")
	private HoneyPot honeypot;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="PARENT_CUB",
			joinColumns=@JoinColumn(name="BEAR_ID"), 
			inverseJoinColumns=@JoinColumn(name="CUB_ID"))
	private Set<Bear> bearCubs;
	
	@Column(name="BEAR_COLOR")
	private String bearColor;
	
	private double weight; //Newtons
	private double height;
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
	public HoneyPot getHoneypot() {
		return honeypot;
	}
	public void setHoneypot(HoneyPot honeypot) {
		this.honeypot = honeypot;
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
	public Bear(int bearId, Cave dwelling, HoneyPot honeypot, Set<Bear> bearCubs, String bearColor, double weight,
			double height) {
		super();
		this.bearId = bearId;
		this.dwelling = dwelling;
		this.honeypot = honeypot;
		this.bearCubs = bearCubs;
		this.bearColor = bearColor;
		this.weight = weight;
		this.height = height;
	}
	
	public Bear() {
		this.bearCubs = new HashSet<Bear>();
	}
	
	public Bear(Cave dwelling, HoneyPot honeypot, String bearColor, double weight,
			double height) {
		this.dwelling = dwelling;
		this.honeypot = honeypot;
		this.bearColor = bearColor;
		this.weight = weight;
		this.height = height;
		this.bearCubs = new HashSet<Bear>();
	}
	
	
	
}
