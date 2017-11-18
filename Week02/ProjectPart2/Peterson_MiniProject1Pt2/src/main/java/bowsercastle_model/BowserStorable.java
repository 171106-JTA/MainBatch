package bowsercastle_model;

import java.util.Map;
import java.util.Queue;

public interface BowserStorable {
	public Queue<User> getUsers();
	public void insertUser(User user);
	public void log(String message);
	public void updateCoins(User user);
	public void updateLevel(User user);
	public void updateRole(User user);
	public void deleteUser(User user);
	public Transaction depositCoins(User user, int coins);
	public Transaction withdrawCoins(User user, int coins);
	public void levelUp(User user);
	public Transaction payLoan(User user, int coins);
	public Map<Integer, Integer> getLoans();
	public void acceptLoan(User user);
	public void denyLoan(User user);
	public Map<Integer, Integer> getLoansWaiting();
	public void applyForLoan(User user, int amount);

}