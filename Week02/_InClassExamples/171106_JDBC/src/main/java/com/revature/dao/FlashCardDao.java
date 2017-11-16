package com.revature.dao;

import com.revature.beans.FlashCard;

public interface FlashCardDao {
	public void createFlashCard(FlashCard fc);
	public FlashCard selectFlashCardById(Integer id);
}
