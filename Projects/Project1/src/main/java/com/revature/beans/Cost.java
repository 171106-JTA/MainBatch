package com.revature.beans;

public enum Cost {
	
	UNIVERSITY_COURSE(80),
	SEMINAR(60),
	CERTIFICATION_PREPARATION_CLASS(75),
	CERTIFICATION(100),
	TECHNICAL_TRAINING(90),
	OTHER(30);
	
	private double value;
	
	Cost(double value) {
		this.setValue(value);
	}

	public static Cost getCost(String eventName) {
		eventName = eventName.toUpperCase();
		if (eventName.equals("SEMINAR")) {
			return Cost.SEMINAR;			
		} else if (eventName.equals("UNIVERSITY COURSE")) {
			return Cost.UNIVERSITY_COURSE;			
		} else if (eventName.equals("CERTIFICATION PREPARATION CLASS")) {
			return Cost.CERTIFICATION_PREPARATION_CLASS;			
		} else if (eventName.equals("CERTIFICATION")) {
			return Cost.CERTIFICATION;			
		} else if (eventName.equals("OTHER")) {
			return Cost.OTHER;			
		} else if (eventName.equals("TECHNICAL TRAINING")) {
			return Cost.TECHNICAL_TRAINING;			
		}
		
		return null;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}