package bank.accounts;

public class User extends Account implements Manageable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4565388540799755650L;

	public User() {
		super();
		
	}

	public User(String username, String password, String firstName, String lastName, int pinNumber) {
		super(username, password, firstName, lastName, pinNumber);
		
	}

	@Override
	public void showBalance() {
		System.out.println("Your current balance is: $" + getBalance());
	}
}
