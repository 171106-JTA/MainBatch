package com.revature.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class House {
	private String name;
	
	@Autowired
	private Wall wallId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Wall getWallId() {
		return wallId;
	}
	public void setWallId(Wall wallId) {
		this.wallId = wallId;
	}
	
	@Override
	public String toString() {
		return "House [name=" + name + ", wallId=" + wallId + "]";
	}
	public House(String name, Wall wallId) {
		super();
		this.name = name;
		this.wallId = wallId;
	}
	public House() {
		super();
	}	
	
}
