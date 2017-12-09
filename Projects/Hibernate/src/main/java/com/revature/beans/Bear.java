package main.java.com.revature.beans;

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

@Entity
@Table(name="BEAR")
public class Bear {
	@Id
	@Column(name="BEAR_ID")
	@SequenceGenerator(sequenceName="BEAR_SEQ", name="BEAR_SEQ")
	@GeneratedValue(generator="BEAR_SEQ", strategy=GenerationType.SEQUENCE)
	private int bearid;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER) // by default fechtype is lazy
	@JoinColumn(name="BEAR_HOME") // namded column bear home and designated as foreign key
	private Cave dwelling;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="HONEYPOT_ID")
	private HoneyPot honeypot;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="PARENT_CUB", joinColumns=@JoinColumn(name="BEARID"), inverseJoinColumns=@JoinColumn(name="CUBID"))
	private Set<Bear> bearcubs;
	@Column(name="BEAR_COLOR")
	private String bearcolor;
	@Column
	private double weight;
	@Column
	private double height;
	public Bear() {
		super();
	}
	public Bear(Set<Bear> bearcubs, String bearcolor, double weight, double height) {
		super();
		this.bearcubs = bearcubs;
		this.bearcolor = bearcolor;
		this.weight = weight;
		this.height = height;
	}
	public Bear(HoneyPot honeypot, Set<Bear> bearcubs, String bearcolor, double weight, double height) {
		super();
		this.honeypot = honeypot;
		this.bearcubs = bearcubs;
		this.bearcolor = bearcolor;
		this.weight = weight;
		this.height = height;
	}
	public Bear(Cave dwelling, HoneyPot honeypot, Set<Bear> bearcubs, String bearcolor, double weight, double height) {
		super();
		this.dwelling = dwelling;
		this.honeypot = honeypot;
		this.bearcubs = bearcubs;
		this.bearcolor = bearcolor;
		this.weight = weight;
		this.height = height;
	}
	public Bear(int bearid, Cave dwelling, HoneyPot honeypot, Set<Bear> bearcubs, String bearcolor, double weight, double height) {
		super();
		this.bearid = bearid;
		this.dwelling = dwelling;
		this.honeypot = honeypot;
		this.bearcubs = bearcubs;
		this.bearcolor = bearcolor;
		this.weight = weight;
		this.height = height;
	}
	public int getBearid() {
		return bearid;
	}
	public void setBearid(int bearid) {
		this.bearid = bearid;
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
	public Set<Bear> getBearcubs() {
		return bearcubs;
	}
	public void setBearcubs(Set<Bear> bearcubs) {
		this.bearcubs = bearcubs;
	}
	public String getBearcolor() {
		return bearcolor;
	}
	public void setBearcolor(String bearcolor) {
		this.bearcolor = bearcolor;
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
	@Override
	public String toString() {
		return "Bear [bearid=" + bearid + ", honeypot=" + honeypot + ", bearcubs=" + bearcubs
				+ ", bearcolor=" + bearcolor + ", weight=" + weight + ", height=" + height + "]";
	}
}
