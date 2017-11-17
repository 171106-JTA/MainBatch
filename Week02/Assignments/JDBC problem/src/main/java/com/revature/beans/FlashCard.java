package com.revature.beans;

/*
 * This is a bean that will be used to represent the FlashCard SQL table. 
 */

public class FlashCard implements Comparable<FlashCard>{
	
	private Integer id; 
	private String question;
	private String answer;
	
	public FlashCard(String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
	}
	
	public FlashCard(Integer id, String question, String answer) {
		super();
		this.id = Integer.valueOf(id); 
		this.question = question;
		this.answer = answer;
	}
	
	public FlashCard() {
		super();
		this.question = "QUESTION";
		this.answer = "ANSWER";
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
	
	@Override
	public String toString() {
		return "FlashCard [id=" + id + ", question=" + question + ", answer=" + answer + "]";
	}

	@Override
	public int compareTo(FlashCard fc) {
		return this.id.compareTo(fc.id); 
	} 
	
	
	
	
	
	

}
