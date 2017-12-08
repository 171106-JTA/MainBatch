package com.banana.service;

import com.banana.dao.UpdateDAOImpl;

public class InfoRequestManipulation {
	public static void insert(int rrId) {
		UpdateDAOImpl udao = new UpdateDAOImpl();
		udao.insertInfoRequest(rrId);
	}
}
