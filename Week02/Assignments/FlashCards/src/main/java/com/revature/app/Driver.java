package com.revature.app;

import java.sql.SQLException;
import java.util.List;

import com.revature.connection.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImpl;
public class Driver {

	public static void main(String[] args) throws SQLException {
		FlashCardDao fcd = new FlashCardDaoImpl();
		List<FlashCard> list;
		FlashCard fc;
		
		
		fc = fcd.findFlashCard(1);
		fcd.createFlashCard(new FlashCard("my question", "my answer"));
		list = fcd.getAllFlashCards();
		fc = list.get(list.size() - 1);
		fc.setQuestion("this is an update");
		System.out.println("updated:>" + fcd.updateFlashCardById(fc));
		System.out.println("deleted:>" + fcd.deleteFlashCardById(fc));
		
		System.out.println(fc);
	}

}
