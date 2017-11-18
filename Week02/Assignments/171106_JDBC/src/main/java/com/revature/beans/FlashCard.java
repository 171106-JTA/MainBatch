package com.revature.beans;

/*
 * This is my bean that will be used to represent my
 * FlashCard table.
 */

public class FlashCard {
	private Integer id;
	private String question;
	private String Answer;
	
	public FlashCard(String question, String answer) {
		this.question = question;
		Answer = answer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	@Override
	public String toString() {
		return "FlashCard [id=" + id + ", question=" + question + ", Answer=" + Answer + "]";
	}
	
	
	
}
