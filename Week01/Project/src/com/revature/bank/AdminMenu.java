package com.revature.bank;

public class AdminMenu extends UserMenu implements Menu {
	private static final int[] MENU_SELECTIONS = { 1, 2 , 3 };//, 4 };
	private static final String[] MENU_SELECTIONS_TEXT = {
		"Activate a User",
		"Lock a User",
//		"Flag a User",
		"Ban a User"
	};
	/**
	 * The constant corresponding to an invalid name
	 */
	private static final String INVALID_NAME = "";
	
	private Admin _admin;
	
	/**
	 * Creates AdminMenu with an Admin and default menu options
	 * @param a : the Admin to operate this AdminMenu
	 */
	public AdminMenu(Admin a) {
		this(a, MENU_SELECTIONS, MENU_SELECTIONS_TEXT);
	}
	
	/**
	 * Creates AdminMenu with the options whose inputs are specified by selections and texts are specified by selectionsText
	 * @param a : the Admin that will operate this AdminMenu
	 * @param selections : the selections the Admin has to choose from
	 * @param selectionsText : the text for those selections to choose from
	 */
	public AdminMenu(Admin a, int[] selections,  String[] selectionsText)
	{
		// Invoke base constructor, that has the logic for handling the menu options
		super(a, selections, selectionsText);
		// Base constructor does not initialize Admins. Let's do that here. 
		_admin = a;
	}

	@Override
	public void display() {
		// if there are not any users to do anything with, we're done
		if (!_admin.anyBaseUsers()) {
			System.out.println("There are currently no base users in this system. Goodbye!");
			return;
		}
		super.display();
	}

	@Override
	public void handleInput() {
		// Just like with the corresponding method in super, we only run this iff User is active and until they 
		//	decide to quit....
		if (!_admin.getState().equals(Admin.ACTIVE)) return;
		// ... and if there are any base Users in the system
		if (!_admin.anyBaseUsers()) return;
		boolean keepGoing = true;
		while (keepGoing)
		{
			// get integer input from user
			int userInput = Integer.parseInt(scanner.nextLine());
			// get the text for that choice from the user, to lower case
			String userChoiceText = options.get(userInput).toLowerCase();
			// if that text contains "activate"
			if (userChoiceText.contains("activate")) {
				// bring them to the activate user prompt
				activateUserPrompt();
			}
			// if that text contains "lock"
			else if (userChoiceText.contains("lock")) {
				// bring them to the lock user prompt
				lockUserPrompt();
			}
			// if that text contains "ban"
			else if (userChoiceText.contains("ban"))
			{
				// bring them to the ban user prompt
				banUserPrompt();
			}
			// otherwise, look for any exit keywords for user choice text
			else 
			{
				for (String stop : EXIT_KEYWORDS)
					if (userChoiceText.contains(stop)) keepGoing = false;
			}
			// if we have chose to keep going, display the menu again
			if (keepGoing) display();
		}
		System.out.println("Thank you for using the Revature banking app! Goodbye!");
	}
	
	private void activateUserPrompt()
	{
		boolean keepGoing = false;
		do
		{
			System.out.println("Activate User Prompt");
			System.out.println(Application.LINE_SEPARATOR + "\n");
			// get the user name 
			String username = fetchUserNameFromAdmin();
			if (username.equals(INVALID_NAME))
			{
				System.out.println("Invalid entry. Please try again.");
			}
			else
			{
				try
				{
					// get the user specified by username and activate them
					_admin.activate(username);
					System.out.println("User has been activated!");
					// if there are more users to be activated
					if (_admin.anyLockedUsers())
					{
						// ask _admin if they want to activate any more Users
						keepGoing = userEnteredYes("There are still more Users who are in the locked state. Activate more Users?");
					}
					// otherwise, we're done here
					else {
						keepGoing = false;
					}
				}
				catch (IllegalArgumentException e)
				{
					System.out.println(e.getMessage());
					// ask for confirmation to retry
					System.out.println("Try again? ");
					keepGoing = userEnteredYes();
				}
			}
		}
		while (keepGoing);
	}
	
	private void lockUserPrompt()
	{
		boolean keepGoing = false;
		do
		{
			System.out.println("Lock User Prompt");
			System.out.println(Application.LINE_SEPARATOR + "\n");
			// get the user name 
			String username = fetchUserNameFromAdmin();
			// if we have invalid name from user, tell the user to retry
			if (username.equals(INVALID_NAME))
			{
				System.out.println("Invalid entry. Please try again.");
			}
			// otherwise...
			else
			{
				try
				{
					//...we try to lock the User specified by username, and signal that that User has been locked
					_admin.lock(username);
					System.out.println("User has been successfully locked");
					// finally, if there are more Users
					if (_admin.anyActiveUsers())
					{
						//...we ask admin if they want to lock any more of them
						keepGoing = userEnteredYes("There are more active users. Lock more of them?");
						
					}
					// otherwise, we're done here
					else
					{
						keepGoing = false;
					}
				}
				// if the lock attempt failed because the username was invalid
				catch(IllegalArgumentException e)
				{
					// tell the admin
					System.out.println(e.getMessage());
					// ask for confirmation to retry
					keepGoing = userEnteredYes("Try again?");
					
				}
			}
		}
		while (keepGoing);
	}
	
	private void banUserPrompt()
	{
		boolean keepGoing = false;
		do
		{
			System.out.println("Ban User Prompt");
			System.out.println(Application.LINE_SEPARATOR + "\n");
			// get the user name 
			String username = fetchUserNameFromAdmin();
			if (username.equals(INVALID_NAME))
			{
				System.out.println("Invalid entry. Please try again.");
			}
			else
			{
				try
				{
					// try to ban the user specified by username and notify the admin of the successful ban
					_admin.ban(username);
					System.out.println("User successfully banned");
					// if there are more active,locked users
					if ((_admin.anyActiveUsers()) || (_admin.anyLockedUsers())) 
					{
						// ask the admin if they want to ban any more of them
						keepGoing = userEnteredYes("Ban any more active or locked users?");
					}
				}
				// if it failed because the user entered the wrong string for the user name
				catch (IllegalArgumentException e)
				{
					// tell the user that
					System.out.println(e.getMessage());
					// ask for confirmation to retry
					keepGoing = userEnteredYes("Try again?");
				}
			}
		}
		while (keepGoing);
	}

	private String fetchUserNameFromAdmin()
	{
		System.out.println("Enter the user name: ");
		String username = scanner.nextLine();
		// look for user by that name in the admin's DataStore
		User foundUser = _admin.findUserByName(username);
		if (foundUser != null) return foundUser.getName();
		return INVALID_NAME;
	}
}
