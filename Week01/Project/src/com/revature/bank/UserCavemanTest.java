package com.revature.bank;

import java.util.HashMap;
import java.util.Map;

public class UserCavemanTest extends User {

	public UserCavemanTest(String name, String pass) {
		super(name, pass);
	}

	public static void main(String[] args) {
		String str = "string",
				hashed = hashString(str),
				hashedHashed = hashString(hashed);
			System.out.printf("string == '%s'\n", str);
			System.out.printf("hashed == '%s'\n", hashed);
			System.out.printf("hashedHashed == '%s'\n", hashedHashed);
			
		Users users = new Users();
		users.add(new User("Jimmbo", "soething"));
		users.add(new User("Ryan", "rlessley"));
		System.out.println(users);
		User ryan = users.getByName("Ryan");
		ryan.setState(User.ACTIVE);
		System.out.println(users);
		// let's compare two Admins...
		
		System.out.println("\n\nComparing Admins...");
		Admin a1 = new Admin("MikeW", "38dh238");
		Admin a2 = new Admin("MikeW", "38dh248");
		Admin a3 = new Admin("MikeW", "38dh238");
		System.out.println("a1 == " + a1 + "\na2 == " + a2 + "\na3 == " + a3);
		System.out.println(a1.equals(a2));	// false
		System.out.println(a1.equals(a3));	// true
		// now, let's initialize a data store for Admin a1 and have a1 manipulate the users in that data store
		Admins admins = new Admins();
		admins.push(a1);
		DataStore data = new DataStore(new Users(), users, new Users(), admins, new Accounts());
		a1.setDataStore(data);
		a1.promote(ryan);
		// Admin.activate() is going to remove the last user in users. Let's save a copy to that data.
		User nowActiveUser = users.get(0);
		a1.activate(users.get(0));
		System.out.println(data.getAdmins());
		/*Admin newAdmin = (Admin)ryan;	// throws ClassCastException
		System.out.println( newAdmin);	*/
		// let's now create UserMenu,AdminMenu and play around with those
		System.out.println("\n\nNow testing UserMenu,AdminMenu");
		UserMenu userMenu = new UserMenu(nowActiveUser);
		AdminMenu adminMenu = new AdminMenu(a1);
		System.out.println("First, UserMenu...\n\n");
		userMenu.display();
		userMenu.handleInput();
		System.out.println("Next, AdminMenu...\n\n");
		adminMenu.display();
		// now, let's test the findUserByName()...
		System.out.println("\n\nNow to test Admin.findUserByName()...");
		
		System.out.println(a1.findUserByName("Jimmbo"));
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "a");
		System.out.println(map.get(2));
	}

}
