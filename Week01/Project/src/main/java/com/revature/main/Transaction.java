package com.revature.main;

public class Transaction {
	private Integer 	current_uid;
	private String 	trans_type;
	private Double 	trans_amt;
	private Integer	recipeint_uid;
	
	
	public Transaction(Integer current_uid, String trans_type, Double trans_amt, Integer recipeint_uid) {
		super();
		this.current_uid = current_uid;
		this.trans_type = trans_type;
		this.trans_amt = trans_amt;
		this.recipeint_uid = recipeint_uid;
	}
	
	public Integer getCurrent_uid() {
		return current_uid;
	}
	public void setCurrent_uid(Integer current_uid) {
		this.current_uid = current_uid;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public Double getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(Double trans_amt) {
		this.trans_amt = trans_amt;
	}
	public Integer getRecipeint_uid() {
		return recipeint_uid;
	}
	public void setRecipeint_uid(Integer recipeint_uid) {
		this.recipeint_uid = recipeint_uid;
	}
	
	@Override
	public String toString() {
		return "[current_uid: " + current_uid + "\ttype: " + trans_type + "\tamount: " + trans_amt
				+ "\trecipeint_uid: " + recipeint_uid + "]";
	}
	
	
}
