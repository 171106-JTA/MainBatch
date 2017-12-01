package com.revature.beans;

public enum Title {
	UNVERIFIED,
	EMPLOYEE,
	EMP_MANAGER,
	DEPARTMENTHEAD,
	BENCO,
	BENCO_MANAGER,
	CEO;
	
	public static Title getTitle(String title) {
		title = title.toUpperCase();
		if (title.equals(Title.UNVERIFIED.toString())) {
			return Title.UNVERIFIED;
		} else if (title.equals(Title.EMPLOYEE.toString())) {
			return Title.EMPLOYEE;
		} else if (title.equals(Title.EMP_MANAGER.toString())) {
			return Title.EMP_MANAGER;
		} else if (title.equals(Title.DEPARTMENTHEAD.toString())) {
			return Title.DEPARTMENTHEAD;
		} else if (title.equals(Title.BENCO_MANAGER.toString())) {
			return Title.BENCO_MANAGER;
		} else if (title.equals(Title.BENCO.toString())) {
			return Title.BENCO;
		} else if (title.equals(Title.CEO.toString())) {
			return Title.CEO;
		} else {
			return null;
		}
	}
}