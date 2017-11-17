package com.revature.main;

import com.revature.DAO.FlashCardDao;
import com.revature.DAO.FlashCardDaoImpl;
import com.revature.beans.FlashCard;

public class Driver {
	public static void main(String[] args) throws Exception
	{
		FlashCardDao dao = new FlashCardDaoImpl();
		FlashCard fc = new FlashCard("Did JDBC work?", "Yes");
		dao.createFlashCard(fc);
		
		System.out.println(dao.selectFlashCardById(1));
		
		dao.createFlashCardSP(new FlashCard("StoredProcedureExample", "Success!"));
		
		fc = new FlashCard("A unique question!", "Are FUn!");
		dao.createFlashCard(fc);
		
//		System.out.println(dao.getAnswerByQuestion(fc));
	}
}
