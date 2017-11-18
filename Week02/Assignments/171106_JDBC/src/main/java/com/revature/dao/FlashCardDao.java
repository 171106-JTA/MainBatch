package com.revature.dao;

import java.util.List;

import com.revature.beans.FlashCard;

public interface FlashCardDao {
	public void createFlashCard(FlashCard fc);
	public FlashCard selectFlashCardById(Integer id);
	public void createFlashCardSP(FlashCard fc);
	public FlashCard getAnswerByQuestion(FlashCard fc);
	
	//Implement these 3
	public List<FlashCard> getAllFlashCards();
	public int updateFlashCardById(FlashCard id);
	public int deleteFlashCardById(Integer id);

}
