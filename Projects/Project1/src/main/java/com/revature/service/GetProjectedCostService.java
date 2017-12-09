package com.revature.service;

import com.revature.beans.Cost;

public class GetProjectedCostService {
	
	public static double getProjectedCost(double cost, String eventName) {
		double projectedCost = 0;
		
		cost = Cost.getCost(eventName).getValue() * (cost / 100);
		
		return projectedCost;
	}
}