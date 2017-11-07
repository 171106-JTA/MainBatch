package bank.accounts;

public class User extends Account implements Manageable {

	@Override
	public void showBalance() {
		System.out.println("Your current balance is: $" + getBalance());
	}
}
