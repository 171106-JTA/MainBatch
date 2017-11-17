package com.revature.dao;

import java.util.List;

import com.revature.beans.FlashCard;

public interface FlashCardDao {
	public void createFlashCard(FlashCard fc);
	public FlashCard selectFlashCardByID(Integer id);
	public void createFlashCardSP(FlashCard fc);
	public FlashCard getAnswerByQuesiton(FlashCard fc);
	
	public List<FlashCard> getAllFlashCards();
	public int updateFlashCardById(FlashCard fc); //Returns the number of rows affected
	public int deleteFlashCardById(Integer id); //Returns the number of rows affected
}
