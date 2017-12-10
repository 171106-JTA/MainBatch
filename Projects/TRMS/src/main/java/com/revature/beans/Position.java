package com.revature.beans;

public class Position {
	private int positionid;
	private String position;
	public int getPositionid() {
		return positionid;
	}
	public void setPositionid(int positionid) {
		this.positionid = positionid;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String toJSON() {
		return "{" 
				+ "\"positionid\": " + this.getPositionid() + ","
				+ "\"position\": " + "\"" + this.getPosition() + "\""
				+ "}";
	}
}
