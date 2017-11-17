package com.revature.connection;

public class FlashCard {
	private Integer id;
	private String question;
	private String answer;
	
	
	public FlashCard(String question, String answer) {
		this.question = question;
		this.answer = answer;
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
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
