package com.revature.dao;

import java.util.List;

import com.revature.beans.FlashCard;

public interface FlashCardDao {
	
	public void createFlashCard(FlashCard fc);
	public FlashCard selectFashCardById(Integer id); 
	public void createFlashCardSP(FlashCard fc);
	public FlashCard selectFlashCardByQuestion(String q);
	
	public List<FlashCard> getAllFlashCards(); 
	public int updateFlashCardById(FlashCard fc);
	public int deleteFlashCardById(Integer id); 
	
}
