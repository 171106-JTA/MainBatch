package com.revature.beans;
/* 
 * This is my bean that will be used to represent my FlashCard table.
 */
public class FlashCard {
	private Integer id;
	private String question;
	private String answer;
	public Integer getId() {
		return id;
	}
	
	
	public FlashCard(String question, String answer) {
		this.question = question;
		this.answer = answer;
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
	
	
}
