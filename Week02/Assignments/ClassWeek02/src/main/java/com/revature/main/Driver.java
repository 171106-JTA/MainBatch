package main.java.com.revature.main;

import main.java.com.revature.beans.FlashCard;
import main.java.com.revature.dao.FlashCardDao;
import main.java.com.revature.dao.FlashCardDaoImpl;

public class Driver {

	public static void main(String[] args) throws Exception {
		FlashCardDao dao = new FlashCardDaoImpl();
		FlashCard fc = new FlashCard("Did JDBC work?", "Yes it did!");
		fc.setId(3);

		//dao.createFlashCard(fc);

		System.out.println(dao.selectFlashCardById(1));
		dao.createFlashCardSP(fc);
		dao.createFlashCardSP(new FlashCard("StoredProcedure Example", "Success!"));
		dao.createFlashCardSP(new FlashCard("Sture Example", "ss!"));
		System.out.printf("GetAll Method: %s%n", dao.getAllFlashCards());
		fc = new FlashCard("Hello?", "Heeeeeey!");
		fc.setId(3);
		System.out.printf("Update Method: %d%n", dao.updateFlashCardById(fc));
		System.out.printf("GetAll Method: %s%n", dao.getAllFlashCards());
		System.out.printf("Delete Method: %d%n", dao.deleteFlashCardById(3));
		System.out.printf("GetAll Method: %s%n", dao.getAllFlashCards());
		/*
		 * fc = new FlashCard("A UNIQUE FLIPPIN QUESTION2", "Are A PAIN IN MY A!2");
		 * dao.createFlashCard(fc);
		 */
		/*
		 * System.out.println(dao.getAnswerByQuestion(fc));
		 */

	}

}
