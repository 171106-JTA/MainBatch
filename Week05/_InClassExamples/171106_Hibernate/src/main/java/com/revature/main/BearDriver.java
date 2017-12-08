package com.revature.main;

import com.revature.dao.BearDao;

public class BearDriver {
	
	
	public static void main(String[] args) {
		BearDao bd = new BearDao();
		
		bd.insertBears();
	}

}
