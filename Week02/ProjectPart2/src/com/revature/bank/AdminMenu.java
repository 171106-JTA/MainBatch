package com.revature.bank;

import com.revature.DAO.AdminDAO;
import com.revature.DAO.AdminDAOImpl;
import com.revature.DAO.UserDAO;
import com.revature.DAO.UserDAOImpl;

public class AdminMenu extends UserMenu implements Menu {
	private static final int[] MENU_SELECTIONS = { 1, 2 , 3 , 4 };
	private static final String[] MENU_SELECTIONS_TEXT = {
		"Activate a User",
		"Lock a User",
//		"Flag a User",
		"Ban a User",
		"Promote a User to Admin"
	};
	/**
	 * The constant corresponding to an invalid name
	 */
	private static final String INVALID_NAME = "";
	
	private Admin _admin;
	
	private UserDAO _userDao;
	private AdminDAO _adminDao;
	
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
		// initialize the DAOs
		_userDao = new UserDAOImpl();
		_adminDao = new AdminDAOImpl();
	}

	@Override
	public void display() {
		// if there are not any users to do anything with, we're done
		if (_userDao.getAllBaseUsers().isEmpty()) {
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
		if (_userDao.getAllBaseUsers().isEmpty()) return;
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
			// if that text contains "promote"
			else if (userChoiceText.contains("promote"))
			{
				// bring them to promote user prompt
				promoteToAdmin();
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
			String username = fetchUserNameFromAdmin(User.LOCKED);
			if (username.equals(INVALID_NAME))
			{
				System.out.println("Invalid entry. Please try again.");
			}
			else
			{
				try
				{
					// get the user specified by username and activate them
					_userDao.activate(_userDao.getAllUsers().getByName(username));
					System.out.println("User has been activated!");
					// if there are more users to be activated
					if (!_userDao.getUsersByState(User.LOCKED).isEmpty())
					{
						// ask _admin if they want to activate any more Users
						keepGoing = userEnteredYes("There are still more Users who are in the locked state. Activate more Users?");
					}
					// otherwise, we're done here
					else {
						keepGoing = false;
					}
				}
				catch (NullPointerException e)
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
			String username = fetchUserNameFromAdmin(User.ACTIVE);
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
					_userDao.lock(_userDao.getAllUsers().getByName(username));
					System.out.println("User has been successfully locked");
					// finally, if there are more Users
					if (!_userDao.getUsersByState(User.ACTIVE).isEmpty())
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
				catch(NullPointerException e)
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
			String username = fetchUserNameFromAdmin(User.ACTIVE, User.LOCKED);
			if (username.equals(INVALID_NAME))
			{
				System.out.println("Invalid entry. Please try again.");
			}
			else
			{
				try
				{
					// try to ban the user specified by username and notify the admin of the successful ban
					_userDao.ban(_userDao.getAllUsers().getByName(username));
					System.out.println("User successfully banned");
					// if there are more active,locked users
					if ((!_userDao.getUsersByState(User.ACTIVE).isEmpty()) || 
						(!_userDao.getUsersByState(User.LOCKED).isEmpty())) 
					{
						// ask the admin if they want to ban any more of them
						keepGoing = userEnteredYes("Ban any more active or locked users?");
					}
					else keepGoing = false;
				}
				// if it failed because the user entered the wrong string for the user name
				catch (NullPointerException e)
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
	
	// TODO: black-box test this.
	private void promoteToAdmin()
	{
		boolean keepGoing = false;
		do
		{
			System.out.println("Promote User prompt");
			System.out.println(Application.LINE_SEPARATOR + "\n");
			// get the user name
			String username = fetchUserNameFromAdmin(User.ACTIVE);
			try 
			{
				// try to promote this user
				_adminDao.promoteToAdmin(_userDao.getAllBaseUsers().getByName(username));
				System.out.println("User successfully promoted");
				// if there are any more promotable users
				if (!_userDao.getUsersByState(User.ACTIVE).isEmpty())
				{
					// ask the Admin if they want to promote any more of them
					keepGoing = userEnteredYes("Promote any more users?");
				}
				else {
					keepGoing = false;
				}
			}
			catch (NullPointerException e)
			{
				// if it failed, prompt the user for permission to try another user
				System.out.println(e.getMessage());
				keepGoing = userEnteredYes("Try again?");
			}
		}
		while (keepGoing);
	}

	private String fetchUserNameFromAdmin(String... userStates)
	{
		// output a list of user names to choose from, based on user state (the following logic should probably be offloaded to a Map)
		System.out.println("Users : ");
		for (String userState : userStates)
		{
			// for right now, we see if userState is a key in the UserDAOImpl...
			if (UserDAOImpl.actions.containsKey(userState))
			{
				System.out.println(_userDao.getUsersByState(userState));
			}
		}
		System.out.println("Enter the user name: ");
		String username = scanner.nextLine();
		// look for user by that name using _userDao
		// TODO: actually implement that findByUsername
		User foundUser = _userDao.getAllUsers().getByName(username);
		if (foundUser != null) return foundUser.getName();
		return INVALID_NAME;
	}
}
