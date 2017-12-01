package com.revature.dao;

import java.util.List;

import com.revature.beans.FlashCard;

public interface FlashCardDao {
	public void createFlashCard(FlashCard fc);
	public FlashCard selectFlashCardById(Integer id);
	public void createFlashCardSP(FlashCard fc);
	public FlashCard getAnswerByQuestion(FlashCard fc);
}
