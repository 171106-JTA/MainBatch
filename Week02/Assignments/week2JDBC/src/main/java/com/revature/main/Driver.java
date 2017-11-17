package com.revature.main;

import java.util.Iterator;
import java.util.List;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDaoImplement; 

public class Driver {
	public static void main(String[] args) throws Exception{
		FlashCardDaoImplement dao = new FlashCardDaoImplement();
		FlashCard fc = null;

		////////////////////////////////////
		//Previous In-class work
		///////////////////////////////////
//		fc = new FlashCard("Did JDBC work? A", "Yes it did! A");
//		
//		dao.createFlashCard(fc);
		
//		System.out.println(dao.selectFlashCardByID(1));
		
//		dao.createFlashCardSP(new FlashCard("StoredProcedure Example", "Success!"));
		
//		fc = new FlashCard("get parameters", "Are fun1!!"); 
//		dao.createFlashCard(fc);
		
		
		//Original Conneciton code!
//		Connection conn = ConnectionUtil.getConnection();
//		
//		close(conn);
//		System.out.println("Successs!!!!");
		
		//Call and check getAllFlashCards()
		List<FlashCard> the_fc_list = dao.getAllFlashCards();
		Iterator<FlashCard> myIter = the_fc_list.iterator();
		while(myIter.hasNext()) {
			FlashCard temp = myIter.next();
			System.out.println("QUESTION: " + temp.getQuestion() + ", ANSWER: " + temp.getAnswer());
		}
		
		FlashCard temp = new FlashCard("Hello?", "Hello!!!!");
		temp.setId(10);
		dao.updateFlashCardById(temp);
		
		dao.deleteFlashCardById(10);
		
		System.out.println("Query Complete!!!!");
	}
}
