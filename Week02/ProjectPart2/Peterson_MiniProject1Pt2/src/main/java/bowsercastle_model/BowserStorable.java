package bowsercastle_model;

import java.util.Queue;

public interface BowserStorable {
	public Queue<User> getUsers();
	public void insertUser(User user);
	public void log(String message);
	

}