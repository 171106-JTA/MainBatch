package com.revature.collections;

public class Pojo implements Comparable<Pojo>{
	private String Name;
	private int id;
	private int numOfKids;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumOfKids() {
		return numOfKids;
	}
	public void setNumOfKids(int numOfKids) {
		this.numOfKids = numOfKids;
	}
	public Pojo(String name, int id, int numOfKids) {
		super();
		Name = name;
		this.id = id;
		this.numOfKids = numOfKids;
	}
	@Override
	public int compareTo(Pojo o) {
		if(this.getId() < o.getId()) {
			return -1;
		}else if(this.getId() > o.getId()) {
			return 1;
		}
		return 0;
	}
	
	
}
