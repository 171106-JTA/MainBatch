package com.revature.beans;

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
	private int caveId;
	
	@OneToMany(mappedBy="dwelling", fetch=FetchType.EAGER)
	private Set<Bear> residents = new HashSet<Bear>();
	
	@Column(name="SQ_FOOTAGE")
	private double squarefootage;
	@Column(name="CAVE_TYPE")
	private String caveType;
	
	public int getCaveId() {
		return caveId;
	}
	public void setCaveId(int caveId) {
		this.caveId = caveId;
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
	public String getCaveType() {
		return caveType;
	}
	public void setCaveType(String caveType) {
		this.caveType = caveType;
	}
	public Cave(int caveId, Set<Bear> residents, double squarefootage, String caveType) {
		super();
		this.caveId = caveId;
		this.residents = residents;
		this.squarefootage = squarefootage;
		this.caveType = caveType;
	}
	
	public Cave( Set<Bear> residents, double squarefootage, String caveType) {
		this.residents = residents;
		this.squarefootage = squarefootage;
		this.caveType = caveType;
	}
	
	public Cave(double squarefootage, String caveType) {
		this.squarefootage = squarefootage;
		this.caveType = caveType;
	}
	
	public Cave() {

	}
	@Override
	public String toString() {
		return "Cave [caveId=" + caveId + ", residents=" + residents + ", squarefootage=" + squarefootage
				+ ", caveType=" + caveType + "]";
	}
	
	
}
