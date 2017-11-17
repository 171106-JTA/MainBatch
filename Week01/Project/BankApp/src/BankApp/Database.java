package BankApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * @author Matthew
 * This class holds all of the data that needs to persist. It holds all
 * relevant User and Account information. It's implementation allows for
 * all of this information to be serialized and deserialized as needed - 
 * (this is what will enable persistance). 
 */
public class Database {
	
	private static final Logger log = Logger.getLogger("GLOBAL");
	
	ArrayList<Customer> customerList;
	ArrayList<Administrator> adminList;
	ArrayList<Account> accountList;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;	
	
	public Database(){
		customerList = new ArrayList<Customer>();
		adminList = new ArrayList<Administrator>();
		accountList = new ArrayList<Account>();
		
		if (!(new File("customerList").exists())){
			try{
				setupDefaultUsers();	
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		
		try{
			deserializeRecords("customerList");
			deserializeRecords("adminList");
			deserializeRecords("accountList");
			log.info("Database: Customer, Admin, and Account Lists successfully deserialized (loaded)");
		} catch(IOException e){
			log.error("Database: Error while deserializing (loading) Records", e);
		}
		
		// prints all customers		
		for( int i = 0; i < customerList.size(); i++){
			System.out.println(customerList.get(i).toString());
		}
		
		// prints all administrators		
		for( int i = 0; i < adminList.size(); i++){
			System.out.println(adminList.get(i).toString());
		}

		// prints all accounts		
		for( int i = 0; i < accountList.size(); i++){
			System.out.println(accountList.get(i).toString());
		}
		
	}

	/**
	 * This method will only be called if there are no serialized Records currently
	 * in the directory. It creates default users and accounts to be used for testing 
	 * purposes.
	 * @throws IOException
	 * The serializeRecords method is called within this method. The serializeRecords
	 *  method can throw the IOException.
	 */
	public void setupDefaultUsers() throws IOException{
		//System.out.println("In setupDefaultUsers()");
		Administrator defaultAdmin = new Administrator("admin1", "bankAppPW");
		Customer defaultCustomer1 = new Customer("John", "Smith", "123-12-3123", "jsmith", "qwerty");
		Customer defaultCustomer2 = new Customer("Jane", "Doe", "234-23-4234", "jdoe", "qwerty");
		Account defaultAccAdmin = new Account(11111109, 0.00);
		Account defaultAccUser = new Account(11111110, 0.00);
		defaultCustomer1.setAccount(defaultAccUser);

		adminList.add(defaultAdmin);
		customerList.add(defaultCustomer1);
		customerList.add(defaultCustomer2);
		accountList.add(defaultAccUser);
		accountList.add(defaultAccAdmin);
		
		try{ 
			serializeRecords("customerList");
			serializeRecords("adminList");
			serializeRecords("accountList");
		} catch (IOException e){
			log.error("Database: Error while setting up default users and accounts", e);
		}
	}
	
	/**
	 * This method serializes the Customer, Admin, and Account Records.
	 * @param filename
	 * This parameter is a String that indicates which ArrayList should be
	 *  serialized.
	 * @throws IOException
	 * This method can throw an IOException
	 */
	public void serializeRecords (String filename) throws IOException{
		try {
			switch (filename){
			case "customerList":
				oos = new ObjectOutputStream(new FileOutputStream("customerList"));
				oos.writeObject(customerList);
				log.info("Database: Updated " + filename);
				break;
			case "adminList":
				oos = new ObjectOutputStream(new FileOutputStream("adminList"));
				oos.writeObject(adminList);
				log.info("Database: Updated " + filename);
				break;
			case "accountList":
				oos = new ObjectOutputStream(new FileOutputStream("accountList"));
				oos.writeObject(accountList);
				log.info("Database: Updated " + filename);
				break;
			}
		} catch (IOException e) {
			log.error("Database: Error updating " + filename, e);
		}
	}
	
	
	/**
	 * This method deserializes the Customer, Admin, and Account list.
	 * @param filename
	 * This parameter is a String which indicates which list should be 
	 *  deserialized.
	 * @throws IOException
	 * This method can throw an IOException
	 */
	@SuppressWarnings("unchecked")
	public void deserializeRecords (String filename) throws IOException{
		try {
			ois = new ObjectInputStream(new FileInputStream(filename));
			switch (filename){
			case "customerList":
				customerList = (ArrayList<Customer>)ois.readObject();
				log.info("Database: Retrived " + filename);
				break;
			case "adminList":
				adminList = (ArrayList<Administrator>)ois.readObject();
				log.info("Database: Retrived " + filename);
				break;
			case "accountList":
				accountList = (ArrayList<Account>)ois.readObject();
				log.info("Database: Retrived " + filename);
				break;
			}
		} catch(IOException | ClassNotFoundException e){
			log.error("Database: Error Retriving " + filename, e);
		} finally {
			if(ois != null){
				ois.close();
			}
		}
	}
	
	/**
	 * This method ensures that every Account created has a unique Account Number
	 * @param amt
	 * This is a double. It is the dollar amount that the Account should be created
	 *  with
	 * @return
	 * This method returns the instance of Account that it was created
	 */
	public Account createAccount(double amt){
		int maxAcctNo = 0;
		for(Account acct: accountList){
			if(maxAcctNo<acct.acctNumber){
				maxAcctNo = acct.acctNumber;
			}
		}
		System.out.println("\n\nmaxAcctNo:\n" + maxAcctNo);
		System.out.println("\n\nAccountList:\n" + accountList);
		Account acct = new Account(maxAcctNo, amt);
		return acct;
	}
	
	/**
	 * This method verifies that userName is not already used by another customer
	 * in the Customer List
	 * @param userName
	 * String containing a potential username
	 * @return
	 * Returns true if username is unique
	 */
	public boolean uniqueUsername(String userName){
		boolean result = true;
		for(int i = 0; i < customerList.size(); i++){
			if (customerList.get(i).username.equals(userName)){
				result = false;
			}
		}
		return result;
	}
	
}