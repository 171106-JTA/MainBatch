package com.revature.main;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDaoImpl;
import com.revature.util.ConnectionUtil;

public class Driver {
	
	public static void main(String[] args) throws Exception {
			Connection conn;	
			close(conn = ConnectionUtil.getConnection());
			System.out.println("Success");
			FlashCard fc = null;
			
			FlashCardDaoImpl dao = new FlashCardDaoImpl();

//==========Exercise=============			
			
			
			System.out.println(dao.getAllFlashCards());
			fc = new FlashCard("Am I presenting", "Yes");
			fc.setId(20);
			dao.updateFlashCardById(fc);
			
			System.out.println(dao.deleteFlashCardById(28) + " row was changed");
			
			
//=======Examples=========================================			
			
			/*
			fc = new FlashCard("Did JDBC WOrk?", "Yes it did!");
			
			dao.createFlashCard(fc);
			System.out.println(dao.selectFlashCardById(1));
			
			dao.createFlashCard(new FlashCard("StoredProcedure EXample", "Success!"));
			
			fc = new FlashCard("A UNI", "Is not fun");
			dao.createFlashCard(fc);
			
			System.out.println(dao.getAnswerByQuestion(fc));
			*/
	}
}
