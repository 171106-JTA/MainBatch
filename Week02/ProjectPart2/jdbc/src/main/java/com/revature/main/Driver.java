package com.revature.main;

import com.revature.bean.FlashCard;
import com.revature.dao.FlashCardDao;
import com.revature.dao.FlashCardDaoImpl;
import java.sql.SQLException;

public class Driver {
    public static void main(String[] args) throws SQLException {
        FlashCardDao dao = new FlashCardDaoImpl();
        FlashCard fc = new FlashCard("Does JDBC work?", "we will find out!");
        /*
        dao.createFlashCard(fc);  // WHY YOU NO WORK!?

        dao.createFlashCardSP(fc);
        System.out.println(dao.selectFlashCardById(1));

        fc = new FlashCard("what are the chances you will work", "we just may find out");
        dao.createFlashCardSPlashCardSP(fc);

        System.out.println(dao.getFlashCardByQuestion(fc));
        */
        System.out.println(dao.getAllFlashCards());
        fc = new FlashCard(2, "the chances you will work?", "100% now that we already know!");
        System.out.println(dao.updateFlashCard(fc));
        System.out.println(dao.deleteFlashCardById(12));
    }
}
