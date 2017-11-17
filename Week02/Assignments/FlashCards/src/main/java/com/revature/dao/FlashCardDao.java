package com.revature.dao;

import java.util.List;

import com.revature.connection.FlashCard;

public interface FlashCardDao {
	public void createFlashCard(FlashCard card);
	public FlashCard findFlashCard(Integer id);
	public void createFlashCardSP(FlashCard fc);
	public FlashCard getFlashCardByQuestion(FlashCard question);
	
	public List<FlashCard> getAllFlashCards();
	public int updateFlashCardById(FlashCard fc);
	public int deleteFlashCardById(FlashCard fc);
}
