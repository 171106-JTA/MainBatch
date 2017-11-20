package com.miniproject2.main;

import com.miniproject2.dao.UserDao;

public class Driver {

	public static void main(String[] args) {
		UserDao dao = new UserDao();
		User user = null;
		if(dao.userExist("Bobbert") < 1) {
			dao.createUser("Bobbert", "THEBobbert");
			user = new User("Bobbert", "THEBobbert");
			user.setBalance(1000000);
			user.setActivated(1);
			user.setLocked(0);
			user.setAdmin(1);
			dao.updateUser(user);
		}
		/*User user = new User("Bobbert", "THEBobbert");
		user.setBalance(1000000);
		user.setActivated(1);
		user.setLocked(0);
		user.setAdmin(1);
		dao.updateUser(user);*/
		//System.out.println(user);
		//dao.createUser("Bruce", "Not Batman");
		//dao.createUser("Bobbert", "THEBobbert");
		//User currUser = dao.getUser("Bruce");
		//System.out.println(currUser.getBalance());
		//System.out.println(currUser);
		System.out.println("------------");
		//System.out.println(dao.userExist("Bobbert"));
		//dao.createUser("Tester", "testing");
		System.out.println(dao.getActList());
	}

}
