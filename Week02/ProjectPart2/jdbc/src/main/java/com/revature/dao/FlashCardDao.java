package com.revature.dao;

import com.revature.bean.FlashCard;

import java.util.List;

public interface FlashCardDao {
    void createFlashCard(FlashCard f);
    FlashCard selectFlashCardById(Integer id);
    void createFlashCardSP(FlashCard fc);
    FlashCard getFlashCardByQuestion(FlashCard fc);
    List<FlashCard> getAllFlashCards();
    int updateFlashCard(FlashCard fc);
    int deleteFlashCardById(int id);
}
