package com.revature.main;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImpl;

public class Driver {

	
	// ASSSUME: Flashcard fc id is 1
	public static void main(String[] args) throws Exception {
		FlashCardDao dao = new FlashCardDaoImpl();
		FlashCard fc = new FlashCard("Did JDBC work?", "Yes it did!");
		
		dao.createFlashCard(fc);
		
		System.out.println(dao.selectFlashCardById(1));
		fc.setId(1);
		
		dao.createFlashCardSP(new FlashCard("StoredProcedure Example", "Success!"));
		
		System.out.println(dao.getAllFlashCards());
		
		System.out.println(dao.updateFlashCardById(fc) + " rows affected");
		
		System.out.println(dao.deleteFlashCardById(fc.getId()) + " rows affected");
		
/*		fc = new FlashCard("A UNIQUE FLIPPIN QUESTION2", "Are A PAIN IN MY A!2");
		dao.createFlashCard(fc);*/
		/*
		System.out.println(dao.getAnswerByQuestion(fc));*/
		
	}

}
