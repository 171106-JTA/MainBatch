package com.revature.main;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImplement;

public class Driver {

	public static void main(String[] args) throws SQLException {

		FlashCardDao dao = new FlashCardDaoImplement();
		FlashCard fc = null;
		
		fc = new FlashCard("java question", "java answer");
		dao.createFlashCard(fc);
		
//		System.out.println(dao.selectFlashCardByID(1));
		
//		dao.createFlashCardSP(new FlashCard("StoredProcedure Example", "Success"));
		
//		fc = new FlashCard("a unique question", "Are a pain");
//		dao.createFlashCard(fc);
		
//		System.out.println(dao.getAnswerByQuestion(fc)); 
		
		
		//update a flash card
		fc = new FlashCard("a new java question", "a new java answer");
		int eff = dao.updateFlashCardByID(fc);
		System.out.println("rows effected: " + eff);
		
		//delete the flash card we just updated
		eff = dao.deleteFlashCardByID(fc.getFid());
		System.out.println("rows effected: " + eff);
		
		//get a list of all flash cards in DB
		List<FlashCard> fc_list = dao.getAllFlashCards();
		System.out.println(fc_list.stream().collect(Collectors.toList()));
		
	}
}