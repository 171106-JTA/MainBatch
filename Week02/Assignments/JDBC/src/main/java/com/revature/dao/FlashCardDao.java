package com.revature.dao;

import java.util.List;

import com.revature.beans.FlashCard;


public interface FlashCardDao {
//what kind of interaction do we want of our DB
	public void createFlashCard(FlashCard fc);
	public FlashCard selectFlashCardById(Integer id);
	public void createFlashCardSP(FlashCard fc);			
	public FlashCard getAnswerByQuestion(FlashCard fc);
	
	public List<FlashCard> getAllFlashCards();
	public int updateFlashCardByID(FlashCard fc); //returns amt rows affected
	public int deleteFlashCardByID(Integer id); //returns amt rows affected
	
}
