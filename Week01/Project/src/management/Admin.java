package management;

public class Admin extends User {


	public Admin(String a, String b, int c, int d) {
		super(a, b, c, d);
		super.setBalance(d + 1000);
	}
	
	public static String displayAdmin(Admin admin) {
		return "Admin: " + admin.userName + "\npassword: " + admin.password + "\nRank: " + admin.rank + "\nBalance: " + admin.balance;
	}
	

	@Override
	public String toString() {
		return "Admin [userName=" + userName + ", password=" + password + ", rank=" + rank + ", balance=" + balance
				+ "]\n";
	}
	
	
}
