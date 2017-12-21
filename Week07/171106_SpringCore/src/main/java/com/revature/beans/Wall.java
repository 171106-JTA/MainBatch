package com.revature.beans;

import org.springframework.stereotype.Component;

@Component("wall")
public class Wall {
	private String material = "Wood";
	
	public Wall(){
		
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public String toString() {
		return "Wall [material=" + material + "]";
	}
	
}
