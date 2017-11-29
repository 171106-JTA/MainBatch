package com.revature.main;

import java.sql.Connection;
import java.util.ArrayList;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImpl;
import com.revature.util.ConnectionUtil;

import static com.revature.util.CloseStreams.close;


public class Driver {

	public static void main(String[] args) throws Exception {
//		Connection conn; 
//		close(conn = ConnectionUtil.getConnection());
//		System.out.println("SUCCESS!");
		
		FlashCardDao dao = new FlashCardDaoImpl();
//		FlashCard fc = new FlashCard("Did JDBC work?", "Yes it did!");
//		
//		dao.createFlashCard(fc);
		
		System.out.println(dao.selectFlashCardById(1));
		
//		dao.createFlashCardSP(new FlashCard("StoredProcedure Example", "Success!"));
		
		ArrayList<FlashCard> fcList = (ArrayList<FlashCard>) dao.getAllFlashCards();
		System.out.println(fcList);
		
		FlashCard fc2 = new FlashCard("Test updating FlashCards by ID.", "Ok. Will do.");
		fc2.setId(7);
		dao.createFlashCard(fc2);
		System.out.println("Records updated: " + dao.updateFlashCardById(fc2));
		
		System.out.println("Records updated: " + dao.deleteFlashCardById(8));
		
		
		
/*		fc = new FlashCard("A UNIQUE FLIPPIN QUESTION2", "Are A PAIN IN MY A!2");
		dao.createFlashCard(fc);*/
		/*
		System.out.println(dao.getAnswerByQuestion(fc));*/
		
	}

}
