package com.peterson.bowsercastle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to handle transactions.
 * @author Alex
 */
public class Vault {
	private static final String LOANS_WTG_TXT = "loans_waiting.txt";
	private static final String LOANS_TXT = "loans.txt";
	/**Collections used to store users and their loans.*/
	private Map<String, Integer> loans;
	private Map<String, Integer> loansWaiting;

	/**
	 * Grab the loans from storage.
	 */
	public Vault() {
		try {
			loansWaiting = grabWaitingLoans();
			loans = grabLoans();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * User deposits funds into their account
	 * @param user
	 * @param coins
	 * @return if the transaction was successful
	 */
	public Transaction depositCoins(User user, int coins) {
		if (coins > 0) {
			user.setCoins(user.getCoins() + coins);
			return Transaction.SUCCESS;
		}
		return Transaction.FAIL;
	}

	/**
	 * User takes money from their account
	 * @param user
	 * @param coins
	 * @return if the transaction was successful
	 */
	public Transaction withdrawCoins(User user, int coins) {
		if (coins <= user.getCoins()){
			user.setCoins(user.getCoins() - coins);
			return Transaction.SUCCESS;
		}
		return Transaction.FAIL;
	}

	/**
	 * 
	 * @param user
	 */
	public void levelUp(final User user) {
		final int level = user.levelUp(); //calculate how many coins the user earned
		user.setLevel(user.getLevel() + level);	
	}

	/**
	 * Members may apply for a loan.
	 * @param member
	 * @param amount
	 */
	public void applyForLoan(User member, int amount) {
		loansWaiting.put(member.getName(), amount);
	}

	/**
	 * The member's loan has been accepted and is on file.
	 * @param member
	 */
	public void acceptLoan(final User member) {
		int borrowedCoins = loansWaiting.get(member.getName());
		member.setCoins(member.getCoins() + borrowedCoins); //add the coins to the user's account
		loans.put(member.getName(), borrowedCoins); //user's loan is stored
		loansWaiting.remove(member.getName());
	}

	/**Remove the user from the loans collection.*/
	public void denyLoan(User member) {
		loansWaiting.remove(member);
	}

	public Map<String, Integer> getLoansWaiting() {
		return loansWaiting;
	}

	public Map<String, Integer> getLoans() {
		return loans;
	}

	/**
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> grabLoans() throws IOException {
		Map<String,Integer> loans = new HashMap<>();

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(LOANS_TXT);
			if (fis.available() != 0) {//this is a new file, create King Bowser
				ois = new ObjectInputStream(fis);
				loans = ((HashMap<String, Integer>) ois.readObject()); //store all of our users in our arraylist
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) fis.close();
			if (ois != null) ois.close();
		}

		return loans;
	}


	/**
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> grabWaitingLoans() throws IOException {
		Map<String,Integer> loans = new HashMap<>();

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(LOANS_WTG_TXT);
			if (fis.available() != 0) {//this is a new file, create King Bowser
				ois = new ObjectInputStream(fis);
				loans = ((HashMap<String, Integer>) ois.readObject()); //store all of our users in our arraylist
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) fis.close();
			if (ois != null) ois.close();
		}

		return loans;
	}


	/**
	 * Upon exiting the program, serialize our collection of waiting loans
	 * @param <T>
	 * @throws IOException 
	 */
	public void serializeWaitingLoans() throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fls = null;
		try {
			fls = new FileOutputStream(LOANS_WTG_TXT);
			oos = new ObjectOutputStream(fls);
			oos.writeObject(loansWaiting); //serialize our queue of users
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fls != null) fls.close(); //close our input and output streams
			if (oos != null) oos.close();
		}
	}

	/**
	 * Upon exiting the program, serialize our collection of loans
	 * @param <T>
	 * @throws IOException 
	 */
	public void serializeLoans() throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fls = null;
		try {
			fls = new FileOutputStream(LOANS_TXT);
			oos = new ObjectOutputStream(fls);
			oos.writeObject(loans); //serialize our queue of users
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fls != null) fls.close(); //close our input and output streams
			if (oos != null) oos.close();
		}
	}


	/**
	 * A user is depositing coins towards their loan.
	 * @param user
	 * @param deposit
	 * @return if the transaction was successful or failed
	 */
	public Transaction payLoan(User user, int deposit) {
		if (deposit < 0) return Transaction.FAIL;
		int loan = loans.get(user);

		int amountOwed = loan - deposit;

		if (amountOwed < 0) {//the user has deposited more coins than they owe
			amountOwed = -amountOwed;
			user.setCoins(user.getCoins() + amountOwed);
			loans.remove(user);
		} else {
			loans.put(user.getName(), amountOwed); //the user has made a deposit less than what they owe
		}

		return Transaction.SUCCESS;
	}
}