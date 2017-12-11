package com.project1.classes;

import java.io.FileInputStream;
import java.sql.Date;

import org.apache.commons.fileupload.FileItem;

public class Form {
	int requestID;
	String employeeID;
	double amount;
	String typeRem;
	int currViewer;
	String status;
	Date dateSub;
	FileItem document;
	String fileName;
	double alteredAmount;
	int alterApproved;
	
	
	public double getAlteredAmount() {
		return alteredAmount;
	}
	public void setAlteredAmount(double alteredAmount) {
		this.alteredAmount = alteredAmount;
	}
	public int getAlterApproved() {
		return alterApproved;
	}
	public void setAlterApproved(int alterApproved) {
		this.alterApproved = alterApproved;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public FileItem getDocument() {
		return document;
	}
	public void setDocument(FileItem document) {
		this.document = document;
	}
	public Form(String employeeID, double amount, String typeRem) {
		super();
		this.employeeID = employeeID;
		this.amount = amount;
		this.typeRem = typeRem;
	}
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTypeRem() {
		return typeRem;
	}
	public void setTypeRem(String typeRem) {
		this.typeRem = typeRem;
	}
	public int getCurrViewer() {
		return currViewer;
	}
	public void setCurrViewer(int currViewer) {
		this.currViewer = currViewer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateSub() {
		return dateSub;
	}
	public void setDateSub(Date dateSub) {
		this.dateSub = dateSub;
	}
	@Override
	public String toString() {
		return "Form [requestID=" + requestID + ", employeeID=" + employeeID + ", amount=" + amount + ", typeRem="
				+ typeRem + ", currViewer=" + currViewer + ", status=" + status + ", dateSub=" + dateSub + ", document="
				+ document + ", fileName=" + fileName + ", alteredAmount=" + alteredAmount + ", alterApproved="
				+ alterApproved + "]";
	}
	
	
	
	
	
	
	
	
	
}
