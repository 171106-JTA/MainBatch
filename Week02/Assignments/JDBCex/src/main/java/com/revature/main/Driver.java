package com.revature.main;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.util.List;

import com.revature.beans.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImp;
import com.revature.util.ConnectionUtil;

public class Driver {

	public static void main(String[] args) throws Exception {
		Connection conn;
		close(conn = ConnectionUtil.getConnection());
		FlashCardDao dao= new FlashCardDaoImp();
		FlashCard fc = new FlashCard(42,"HELLO", "YEAH!");
		List<FlashCard> cards=dao.getAllFlashCards();
		int x= dao.updateFlashCardById(fc);
		//int y= dao.deleteFlashCardById(42);
		System.out.println(x);
		cards =dao.getAllFlashCards();
		for(FlashCard card:cards)
			System.out.println(card.toString());

	}

}
