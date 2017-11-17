package main.java.com.revature.dao;

import java.util.List;

import main.java.com.revature.beans.FlashCard;

public interface FlashCardDao {
	public void createFlashCard(FlashCard fc);
	public FlashCard selectFlashCardById(Integer id);
	public void createFlashCardSP(FlashCard fc);
	public FlashCard getAnswerByQuestion(FlashCard fc);

	//To do
	public List<FlashCard> getAllFlashCards();
	public int updateFlashCardById(FlashCard fc);
	public int deleteFlashCardById(Integer id);
}
