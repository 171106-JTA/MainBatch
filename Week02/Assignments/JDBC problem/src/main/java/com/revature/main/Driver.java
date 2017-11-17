package com.revature.main;

import java.util.ArrayList;
import java.util.List;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImpl;

public class Driver {

	public static void main(String[] args) throws Exception{
//		Connection conn; 
//		close(conn = ConnectionUtil.getConection());
//		System.out.println("SUCCESS");
//		^These are superfluous now. All handled by the DAO
		
		FlashCardDao dao = new FlashCardDaoImpl(); 
		FlashCard fc = new FlashCard("Did JDBC work?", "Yes it did!"); 
		
		//dao.createFlashCard(fc);
		System.out.println(dao.selectFashCardById(5));
		
		fc = new FlashCard("Did callable statement work?","Yes it did!"); 
		//dao.createFlashCardSP(fc);
		System.out.println(dao.selectFlashCardByQuestion("Are we human?")); 
		
		System.out.println("\n===== In-Class Problem - Get All Flash Cards =====");
		List<FlashCard> cards = dao.getAllFlashCards();
		cards.sort(null);
		cards.forEach(System.out::println);
		
		System.out.println("\n===== In-Class Problem - Update by ID =====");
		fc = new FlashCard(new Integer(3), "Did the update work?", "Yes it did!"); 
		System.out.println(dao.updateFlashCardById(fc) + " rows affected");  
		cards = dao.getAllFlashCards();
		cards.forEach(System.out::println);
				
		System.out.println("\n===== In-Class Problem - Delete by ID =====");
		System.out.println(dao.deleteFlashCardById(4) + " rows affected");  
		cards = dao.getAllFlashCards();
		cards.forEach(System.out::println);
		
		
		
	}

}
