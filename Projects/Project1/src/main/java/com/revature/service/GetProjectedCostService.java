package com.revature.service;

import com.revature.beans.Cost;

public class GetProjectedCostService {
	
	public static double getProjectedCost(double cost, String eventName) {
		return  Cost.getCost(eventName).getValue() * (cost / 100);
	}
}