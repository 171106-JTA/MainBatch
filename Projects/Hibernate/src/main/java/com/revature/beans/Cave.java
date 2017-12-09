package main.java.com.revature.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CAVE")
public class Cave {
	@Id
	@Column(name="CAVE_ID")
	@SequenceGenerator(sequenceName="CAVE_SEQ", name="CAVE_SEQ")
	@GeneratedValue(generator="CAVE_SEQ", strategy=GenerationType.SEQUENCE)
	private int caveid;
	@Column
	@OneToMany(fetch=FetchType.EAGER)
	private Set<Bear> residents = new HashSet<Bear>();
	@Column(name="SQ_FOOTAGE")
	private double squarefootage;
	@Column(name="CAVE_TYPE")
	private String cavetype;
	public Cave() {
		super();
	}
	public Cave(double squarefootage, String cavetype) {
		super();
		this.squarefootage = squarefootage;
		this.cavetype = cavetype;
	}
	public Cave(Set<Bear> residents, double squarefootage, String cavetype) {
		super();
		this.residents = residents;
		this.squarefootage = squarefootage;
		this.cavetype = cavetype;
	}
	public Cave(int caveid, Set<Bear> residents, double squarefootage, String cavetype) {
		super();
		this.caveid = caveid;
		this.residents = residents;
		this.squarefootage = squarefootage;
		this.cavetype = cavetype;
	}
	public int getCaveid() {
		return caveid;
	}
	public void setCaveid(int caveid) {
		this.caveid = caveid;
	}
	public Set<Bear> getResidents() {
		return residents;
	}
	public void setResidents(Set<Bear> residents) {
		this.residents = residents;
	}
	public double getSquarefootage() {
		return squarefootage;
	}
	public void setSquarefootage(double squarefootage) {
		this.squarefootage = squarefootage;
	}
	public String getCavetype() {
		return cavetype;
	}
	public void setCavetype(String cavetype) {
		this.cavetype = cavetype;
	}
}
