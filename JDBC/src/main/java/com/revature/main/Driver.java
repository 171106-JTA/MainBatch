package com.revature.main;

import java.util.List;

//import java.sql.Connection;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImpl;
//import com.revature.util.ConnectionUtil;
//import static com.revature.util.CloseStreams.close;

public class Driver {
	public static void main(String[] args) throws Exception {
		
		FlashCardDao dao = new FlashCardDaoImpl();
		int affected = 0;
			
/*		
 * 		old
		======================================================
		FlashCard fc = new FlashCard("Did JDBC work?", "Yes it did!");
		dao.createFlashCard(fc);
		System.out.println(dao.selectFlashCardById(1));
		dao.createFlashCardSP(new FlashCard("StoredProcedure Example", "Success!"));
		
		fc = new FlashCard("A UNIQUE FLIPPIN QUESTION2", "Are A PAIN IN MY A!2");
		dao.createFlashCard(fc);
		 
		
		
		System.out.println(dao.getAnswerByQuestion(fc));
		
		Connection conn;
		close(conn = ConnectionUtil.getConnection());
		System.out.println("SUCCESS!");
		==========================================================
*/
				
		
		
		/*
		 * in-class exercise
		 */		
		//lists all cards
		List<FlashCard> all = dao.getAllFlashCards();
		for (FlashCard c : all) {
			System.out.println(c.toString());
		}
		
		FlashCard fc1 = new FlashCard("What color is the sky?", "Purple"); 
		fc1.setId(17);
		dao.createFlashCard(fc1);	//necessary to insert card into DB

		//updates card
		FlashCard fc2 = new FlashCard("Do I exist?", "(Un)fortunately."); 
		fc2.setId(18);
		dao.createFlashCard(fc2);		//necessary to insert card into DB
		dao.updateFlashCardByID(fc2);	//updates card in DB
		
		//deletes card
		affected = dao.deleteFlashCardByID(17);
		System.out.println(affected + " row(s) affected.");
		
		
//		FlashCard fc2 = new FlashCard("Are dogs cute?", "The cutest."); 
//		dao.createFlashCard(fc2);
//		affected = dao.deleteFlashCardByID(25);
//		System.out.println(affected + " row(s) affected.");
		
		
	}
}
