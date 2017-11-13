package management;

import static org.junit.Assert.*;

import org.junit.Test;

public class CasinoTest {
	
	
	User u = new User("test", "test", 0, 0);

	
	@Test
	public void setUsernameTest() {
		u.setUserName("red");
		String output = u.getUsername();
		assertEquals("red" ,output);
	}
	
	@Test
	public void setPasswordTest() {
		u.setPassword("pass");
		String output = u.getPassword();
		assertEquals("pass" ,output);
	}
	
	@Test
	public void setRankTest() {
		u.setRank(1);
		int output = u.getRank();
		assertEquals(1 ,output);
	}
	
	@Test
	public void setBalanceTest() {
		u.setBalance(100);
		int output = u.getBalance();
		assertEquals(100 ,output);
	}
	
	@Test
	public void getUsernameTest() {
		String output = u.getUsername();
		assertEquals("test", output);
	}
	
	@Test
	public void getPasswordTest() {
		String output = u.getPassword();
		assertEquals("test", output);
	}
	
	@Test
	public void getRankTest() {
		int output = u.getRank();
		assertEquals(0, output);
	}
	
	@Test
	public void getBalanceTest() {
		int output = u.getBalance();
		assertEquals(0, output );
	}
	

}
