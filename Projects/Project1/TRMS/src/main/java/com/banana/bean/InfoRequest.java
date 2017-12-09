package com.banana.bean;

import java.sql.Blob;

public class InfoRequest {
	
	private int irId;
	private int rrId;
	private int requesteeId;
	private String info;
	private String fileName;
	private Blob blob;
	
	public InfoRequest(int irId, int rrId) {
		super();
		this.irId = irId;
		this.rrId = rrId;
	}

	public InfoRequest(int irId, int rrId, int requesteeId, String info, String fileName, Blob blob) {
		super();
		this.irId = irId;
		this.rrId = rrId;
		this.requesteeId = requesteeId;
		this.info = info;
		this.fileName = fileName;
		this.blob = blob;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getIrId() {
		return irId;
	}

	public void setIrId(int irId) {
		this.irId = irId;
	}

	public int getRrId() {
		return rrId;
	}

	public void setRrId(int rrId) {
		this.rrId = rrId;
	}

	public int getRequesteeId() {
		return requesteeId;
	}

	public void setRequesteeId(int requesteeId) {
		this.requesteeId = requesteeId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Blob getBlob() {
		return blob;
	}

	public void setBlob(Blob blob) {
		this.blob = blob;
	}
	
	
}
