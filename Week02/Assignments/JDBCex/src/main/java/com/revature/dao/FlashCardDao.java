package com.revature.dao;

import java.util.List;

import com.revature.beans.FlashCard;

public interface FlashCardDao {
	public void createFlashCard(FlashCard fc);
	public FlashCard selectFlashCardByID(Integer id);
	public void createFlashCardSP(FlashCard fc);
	public FlashCard getAnswerByQuestion(FlashCard fc);
	
	
	public List<FlashCard> getAllFlashCards();
	public int updateFlashCardById(FlashCard fc);
	public int deleteFlashCardById(Integer id);
}
