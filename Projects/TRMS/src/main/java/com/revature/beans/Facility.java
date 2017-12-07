package com.revature.beans;

public class Facility {
	private  int facilityid;
	private  String address;
	private  String city;
	private  String state;
	public int getFacilityid() {
		return facilityid;
	}
	public void setFacilityid(int facilityid) {
		this.facilityid = facilityid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String toJSON() {
		return "{" 
				+ "'facilityid': " + this.getFacilityid() + ","
				+ "'address': " + "'" + this.getAddress() + "',"
				+ "'city': " + "'" + this.getCity() + "',"
				+ "'state': " + "'" + this.getState()
				+ "}";
	}
}
