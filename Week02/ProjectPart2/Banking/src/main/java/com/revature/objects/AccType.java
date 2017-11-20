/**
 * Class Type of accounts
 * 
 * For scalability, seemed a good idea to have this class
 * rather than using literals.
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 * 
 */package com.revature.objects;

import java.util.Date;

import com.revature.generalmethods.GeneralFunctions;

public class AccType {
	/*
	 * Banks handle numerous types of accounts
	 * (checking, saving, loans, ...)
	 * */
	
	//Properties
	private int accTypeID;			//0 Admin
									//1 Simple user
	private String accTypeName;
	private Date accTypDate; 		//Date of type incorporation
	
	//Constructor
	public AccType(int accTypeID, String accTypeName, Date accTypDate) {
		this.accTypeID = accTypeID;
		this.accTypeName = accTypeName;
		this.accTypDate = accTypDate;
	}

	//Getters and Setters
	public int getAccTypeID() {
		return accTypeID;
	}

	public String getAccTypeName() {
		return accTypeName;
	}
	public void setAccTypeName(String accTypeName) {
		this.accTypeName = accTypeName;
	}

	public Date getAccTypDate() {
		return accTypDate;
	}
	public void setAccTypDate(Date accTypDate) {
		this.accTypDate = accTypDate;
	}

	@Override
	public String toString() {
		return accTypeID + " - " + accTypeName + " - " + new GeneralFunctions<Integer>().formatDate(accTypDate, "MM/dd/yyyy");
	}

}
