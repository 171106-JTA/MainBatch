package com.revature.beans;

/*
 * This is my bean that will be used to represent my FlashCard table.
 */
public class FlashCard {
	private Integer fid;
	private String question;
	private String answer;
	
	public FlashCard(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
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
		return "FlashCard [fid=" + fid + ", question=" + question + ", answer=" + answer + "]";
	}
}