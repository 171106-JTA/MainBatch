package com.revature.main;

import java.util.List;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImpl;

public class Driver {

	public static void main(String[] args) throws Exception {
		/*Connection conn;
		close(conn = ConnectionUtil.getConnection());
		System.out.println("SUCCESS!");
*/
		FlashCardDao dao = new FlashCardDaoImpl();
		FlashCard fc = new FlashCard("Did JDBC work?", "Yes it did!");
		fc.setId(20);
		/*dao.createFlashCard(fc);*/
		
		//System.out.println(dao.selectFlashCardById(1));
		//System.out.println(dao.getAllFlashCards());
		System.out.println(dao.updateFlashCardById(fc));
		
		/*dao.createFlashCard(new FlashCard("Stored Procedure Example", "Success!"));
		
		fc = new FlashCard("Unique", "Are Terrible!");
		dao.createFlashCard(fc); 
		
		System.out.println(dao.getAnswerByQuestion(fc));*/
	}

}
