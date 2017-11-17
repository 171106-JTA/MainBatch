package com.revature.main;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImpl;

public class Driver {

	public static void main(String[] args) throws Exception {
		FlashCardDao dao = new FlashCardDaoImpl();
		FlashCard fc = new FlashCard("Did JDBC work?", "Yes it did!");
		
		dao.createFlashCard(fc);
		
	}

}
