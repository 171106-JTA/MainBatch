package com.revature.model;

import java.io.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Level;

import com.revature.model.account.*;
import com.revature.model.request.*;
import com.revature.throwable.*;
import com.revature.view.Console;

/**
 *   Class: Model
 *   
 *   The core logic of the banking app
 *   implements a server-side simulation of a client-server relation
 *   between the main driver and the logic level
 *   The Model is responsible for managing all procedures: processing
 *   requests, loans, and maintaining a cache of user and other data
 */

/**
 * @author Vaeth
 *
 */
public class Model implements Serializable, Observer {

	private static final long serialVersionUID = 7115763857114460208L;

	private static final String letterRegex = "[^a-zA-Z]";
	private static final String numberRegex = "[^0-9]";
	private static final String specCharRegex = "[a-zA-Z0-9 ]";

	private static Model m;
	private static SecretKeySpec sks;

	private static Set<String> sessions = Collections.synchronizedSet(new HashSet<String>());
	private static String modelFile = Properties.FILENAME;
	private static String ivFile = Properties.IVFILE;
	private Map<String, UserAccount> users;
	private Map<String, UserAccount> lockedAccounts;
	private Map<String, UserAccount> unlockedAccounts;
	private Map<String, AdminAccount> admins;
	private Map<String, UserAccount> rejectedAccounts;
	private Map<String, UserAccount> pendingAccounts;
	private Queue<Request> requests;
	private Map<Integer, Request> requestSet;
	private Set<String> admUsers;

	private Model() {
		users = new Hashtable<String, UserAccount>();
		admins = new Hashtable<String, AdminAccount>();
		lockedAccounts = new Hashtable<String, UserAccount>();
		unlockedAccounts = new Hashtable<String, UserAccount>();
		rejectedAccounts = new Hashtable<String, UserAccount>();
		pendingAccounts = new Hashtable<String, UserAccount>();
		requestSet = new Hashtable<Integer, Request>();
		requests = new LinkedList<Request>();
		admUsers = new HashSet<String>();

		queryUsers(Properties.PROT_ADD, Properties.ROOT_ADMIN_USER, RootUserAccount.class, Properties.ROOT_ADMIN_USER,
				Properties.ROOT_ADMIN_PASS, Properties.ROOT_USER_BAL);
		queryAdmins(Properties.PROT_ADD, Properties.ROOT_ADMIN_USER, AdminAccount.class, Properties.ROOT_ADMIN_USER,
				Properties.ROOT_ADMIN_PASS, users.get(Properties.ROOT_ADMIN_USER));
		synchronized (users) {
			users.get(Properties.ROOT_ADMIN_USER).setStatus(Properties.ACCT_STAT_GOOD);
		}
	}

	/**
	 * Gets the Singleton instance of Model
	 */

	public synchronized static Model getInstance() throws DeserializationError {
		File file = new File(modelFile);
		file.setWritable(true);
		file.setReadable(true);
		try {
			if (file.exists() && new File(ivFile).exists()) {
				deserialize();
			}
		} catch (IOException | ClassNotFoundException | InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
				| InvalidAlgorithmParameterException e) {
			Throwable t = new DeserializationError(e);
			Console.print(System.err, t, Level.FATAL_INT);
		} finally {
			if (m == null) {
				m = new Model();
			} else {
				Model.initialize();
			}
		}
		return m;
	}

	// Query methods
	/**
	 * Queries user collection accesses in a thread-safe manner
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param user:
	 *            the user key for map
	 * @param args:
	 *            option Object arguments
	 */
	public synchronized Object queryUsers(int prot, String user, Object... args) {
		return queryList(prot, users, user, UserAccount.class, args);
	}

	/**
	 * Queries admin collection accesses in a thread-safe manner
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param user:
	 *            the user key for map
	 * @param args:
	 *            option Object arguments
	 */
	public synchronized Object queryAdmins(int prot, String user, Object... args) {
		synchronized (users) {
			return queryList(prot, admins, user, AdminAccount.class, args);
		}
	}

	/**
	 * Queries request collection accesses in a thread-safe manner
	 * 
	 * @param cprot:
	 *            the collection procedural protocol
	 * @param qprot:
	 *            the queue procedural protocol this allows for more specific
	 *            operations
	 * @param r:
	 *            the Request to process for map
	 * @param args:
	 *            optional Object arguments
	 */
	public synchronized Object queryRequests(int cProt, Request r, Object... args) {
		int qProt = cProt;
		cProt = cProt == Properties.PROT_APPROVE ? Properties.PROT_REMOVE
				: cProt == Properties.PROT_REJECT ? Properties.PROT_REMOVE : cProt;
		return queryQueue(cProt, qProt, requests, r, args);
	}

	/**
	 * Queries locked account collection accesses in a thread-safe manner
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param user
	 *            the user key for map
	 */
	public synchronized Object queryLockedAccounts(int prot, String user) {
		if (prot == Properties.PROT_GET)
			return queryList(prot, lockedAccounts, null, null);
		return queryList(prot, lockedAccounts, user, users.get(user));
	}

	/**
	 * Queries unlocked account collection accesses in a thread-safe manner
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param user
	 *            the user key for map
	 */
	public synchronized Object queryUnlockedAccounts(int prot, String user) {
		if (prot == Properties.PROT_GET)
			return queryList(prot, unlockedAccounts, null, null);
		return queryList(prot, unlockedAccounts, user, users.get(user));
	}

	/**
	 * Queries rejected account collection accesses in a thread-safe manner
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param user
	 *            the user key for map
	 */
	public synchronized Object queryRejectedAccounts(int prot, String user) {
		if (prot == Properties.PROT_GET)
			return queryList(prot, rejectedAccounts, null, null);
		return queryList(prot, rejectedAccounts, user, users.get(user));
	}

	/**
	 * Queries pending account collection accesses in a thread-safe manner
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param user
	 *            the user key for map
	 */
	public synchronized Object queryPendingAccounts(int prot, String user) {
		if (prot == Properties.PROT_GET)
			return queryList(prot, pendingAccounts, null, null);
		return queryList(prot, pendingAccounts, user, users.get(user));
	}

	/**
	 * Queries maps for the procedure listed in the individual query methods
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param map,
	 *            the generic map
	 * @param key
	 *            the generic key
	 * @param val
	 *            the generic value
	 */
	private <K, V> Object queryList(int prot, Map<K, V> map, K key, V val) {
		synchronized (map) {
			switch (prot) {
			case Properties.PROT_GET:
				return getMap(map);
			case Properties.PROT_ADD:
				return addToMap(map, key, val);
			case Properties.PROT_REMOVE:
				return removeFromMap(map, key);
			}
		}
		return null;
	}

	/**
	 * Queries maps for the procedure listed in the individual query methods
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param map,
	 *            the generic map
	 * @param key
	 *            the generic key
	 * @param val
	 *            the generic value
	 */
	private <K, V> Object queryList(int prot, Map<K, V> map, K key, Class<V> c, Object... args) {
		synchronized (map) {
			switch (prot) {
			case Properties.PROT_GET:
				return getMap(map);
			case Properties.PROT_ADD:
				return createNewInstance(map, key, c, args);
			case Properties.PROT_REMOVE:
				return removeFromMap(map, key);
			case Properties.PROT_CONVERT:
				return convertUser(map, key, args);
			}
		}
		return null;
	}

	/**
	 * Queries queue for the procedure listed in the individual query methods
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param qprot:
	 *            the queue procedural protocol
	 * @param queue,
	 *            the request map
	 * @param key
	 *            the generic key
	 * @param val
	 *            the generic value
	 */
	private Object queryQueue(int prot, int qProt, Queue<Request> queue, Request r, Object... args) {
		synchronized (queue) {
			switch (prot) {
			case Properties.PROT_GET:
				return getQueue(queue);
			case Properties.PROT_ADD:
				if (r instanceof CancelRequest)
					requestSet.put(((CancelRequest) r).getTransId(), r);
				return queue.offer(r);
			case Properties.PROT_REMOVE:
				return removeFromQueue(qProt, queue);
			case Properties.PROT_POLL:
				synchronized (users) {
					int i = 0;
					while (r == null && !queue.isEmpty() && i < queue.size()) {
						if ((r = queue.poll()) != null) {
							if (!users.containsKey(r.getUser()))
								r = null;
							if (r.getUser().equals((String) args[0])) {
								queue.offer(r);
								r = null;
							}
						}
						i++;
					}
					return r;
				}
			}
		}
		return false;
	}

	/**
	 * Converts user accounts to different user accounts, maintaining all relevant
	 * account information
	 * 
	 * @param map,
	 *            the generic map
	 * @param key:
	 *            the generic key
	 * @param args:
	 *            optional additional arguments
	 */
	private <K, V> UserAccount convertUser(Map<K, V> map, K key, Object[] args) {
		Object o;
		UserAccount from, to = null;
		try {
			o = ((Class<?>) args[0]).newInstance();
			from = (UserAccount) map.get(key);
			if (o instanceof BasicUserAccount)
				to = new BasicUserAccount(from);
			if (o instanceof SeniorUserAccount)
				to = new SeniorUserAccount(from);
			if (o instanceof PremiumUserAccount)
				to = new PremiumUserAccount(from);
			users.put((String) key, to);
		} catch (InstantiationException | IllegalAccessException e) {
			Console.print(System.err, e);
		}
		return to;
	}

	/**
	 * Returns Request queue
	 * 
	 */
	private Object getQueue(Queue<Request> queue) {
		return queue;
	}

	/**
	 * Returns the specified map
	 * 
	 * @param map:
	 *            the generic map
	 */
	private <K, V> Map<K, V> getMap(Map<K, V> map) {
		return map;
	}

	/**
	 * Creates a new account after confirming account will be created in a
	 * synchronized environment
	 * 
	 * @param map:
	 *            the generic map
	 * @param key
	 *            the generic key
	 * @param val
	 *            the class object of the generic value to instantiate the new
	 *            account to
	 * @param args:
	 *            an optional array of arguments
	 * 
	 */
	private <K, V> boolean createNewInstance(Map<K, V> map, K key, Class<V> val, Object[] args) {
		Object o = null;
		Class<?> c;
		if (map.containsKey(key))
			return false;

		try {
			c = (Class<?>) args[0];
			o = c.newInstance();
			if (o instanceof UserAccount) {
				((UserAccount) o).setUser((String) args[1]);
				((UserAccount) o).setPass((String) args[2]);
				((UserAccount) o).addObserver(this);
			}
			if (o instanceof AdminAccount) {
				((AdminAccount) o).setUser((String) args[1]);
				((AdminAccount) o).setPass((String) args[2]);
				((AdminAccount) o).setAssociatedAccount((UserAccount) args[3]);
				admUsers.add(users.get(((AdminAccount) o).getAssociatedAccount().getUser()).getUser());
			}

			addToMap(map, key, val.cast(o));

			return true;

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
			Console.print(System.err, e);
			return false;
		}
	}

	/**
	 * Adds generic <K, V> pair to the proper map
	 * 
	 * @param map,
	 *            the generic map
	 * @param key
	 *            the generic key
	 * @param val
	 *            the generic value
	 */
	private <K, V> boolean addToMap(Map<K, V> map, K key, V val) {
		if (map.containsKey(key))
			return false;

		map.put(key, val);
		return true;
	}

	/**
	 * Queries removes generic T from queue
	 * 
	 * @param prot:
	 *            the procedural protocol
	 * @param queue:
	 *            the generic queue
	 */
	private <T> boolean removeFromQueue(int prot, Queue<T> queue) {
		Request r;
		UserAccount u;
		r = (Request) queue.poll();
		if (r == null)
			return false;
		u = users.get(r.getUser());
		if (u == null)
			throw new NoSuchUserException(r.getUser());
		u.addToSet(prot, r);

		return true;
	}

	/**
	 * Queries removes generic key K from map
	 * 
	 * @param map,
	 *            the generic map
	 * @param key
	 *            the generic key
	 */
	private <K, V> boolean removeFromMap(Map<K, V> map, K key) {
		return map.remove(key) != null;
	}

	/**
	 * Write the Model to a serialized file.Also encrypts the file using
	 * AES/CBC/PKCS5Padding algorithms and writes an iv to a txt file
	 * 
	 */
	public void serialize() throws IOException, ClassNotFoundException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidParameterSpecException,
			InvalidAlgorithmParameterException {

		Console.print(Properties.ENCRYPT, Level.INFO_INT);
		FileOutputStream fos = new FileOutputStream(new File(modelFile));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		sks = new SecretKeySpec(Properties.SECRET_KEY.getBytes(), "AES");

		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		IvParameterSpec spec = new IvParameterSpec(iv);
		c.init(Cipher.ENCRYPT_MODE, sks, spec);

		SealedObject so = new SealedObject(this, c);
		CipherOutputStream cos = new CipherOutputStream(oos, c);
		ObjectOutputStream out = new ObjectOutputStream(cos);

		Console.print(Properties.SERIALIZE, Level.INFO_INT);
		try {
			out.writeObject(so);

			writeIV(iv);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
			if (cos != null) {
				cos.flush();
				cos.close();
			}
			if (oos != null) {
				oos.flush();
				oos.close();
			}
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
	}

	/**
	 * Stop all running timers and flag all running user threads to stop
	 * 
	 */
	public void cleanup() {
		// kill all threads too
		synchronized (users) {
			for (UserAccount u : users.values()) {
				u.stopTimer();
				u.tryStopThread();
			}
		}
	}

	/**
	 * Writes an initialization vector relating to CBC encrypting to file
	 * 
	 * @param iv:
	 *            a byte array containing the iv
	 */
	private void writeIV(byte[] iv) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(ivFile)));
		try {
			oos.write(iv);
		} finally {
			oos.close();
		}

	}

	/**
	 * Deserializes a serialized file and restores a readable object of Model
	 * Resolves Singleton issue with deserialization Decrypts file using
	 * AES/CBC/PKCS5Padding. File will not be decryptable without the iv file
	 * 
	 */
	public synchronized static Model deserialize()
			throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ObjectInputStream in = null;

		Console.print(Properties.DESERIALIZE, Level.INFO_INT);
		Console.print(Properties.DECRYPT, Level.INFO_INT);

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		sks = new SecretKeySpec(Properties.SECRET_KEY.getBytes(), "AES");
		try {

			SealedObject so;
			byte[] iv = getIV();
			fis = new FileInputStream(modelFile);
			ois = new ObjectInputStream(fis);
			IvParameterSpec spec = new IvParameterSpec(iv);

			c.init(Cipher.DECRYPT_MODE, sks, spec);
			CipherInputStream cis = new CipherInputStream(ois, c);
			in = new ObjectInputStream(cis);

			so = (SealedObject) in.readObject();
			m = (Model) so.getObject(c);

		} finally {
			if (in != null)
				in.close();
			if (ois != null)
				ois.close();
			if (fis != null)
				fis.close();

		}
		return m;
	}

	/**
	 * Auxillary method to retrieve iv initialization vector related to a CBC
	 * procedure
	 */
	public static byte[] getIV() throws FileNotFoundException, IOException {
		byte[] iv = new byte[16];
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ivFile));
		try {
			ois.read(iv);
		} finally {
			ois.close();
		}
		return iv;
	}

	/**
	 * Initializes all threads to be running for users
	 */
	public static void initialize() {
		synchronized (m.users) {
			for (UserAccount u : m.users.values()) {
				u.setTimer();
				u.startThread();
			}
		}
	}

	/**
	 * Log a connected user
	 */
	public boolean beginSession(String user) {
		return sessions.add(user);
	}

	/**
	 * Remove a connected lose with connection is ended
	 */
	public boolean endSession(String user) {
		return sessions.remove(user);
	}

	/**
	 * Authenticate login credentials
	 * 
	 * @param user:
	 *            the inputed username
	 * @param pass:
	 *            the inputed password
	 */
	public boolean checkAuth(String user, String pass, boolean admin) {
		Map<?, ?> map;
		map = admin ? (Map<?, ?>) admins : (Map<?, ?>) users;

		synchronized (map) {
			return map.containsKey(user) && ((Account) map.get(user)).getPass().equals(pass);
		}
	}

	/**
	 * Gets user account status synchronously
	 * 
	 * @param user:
	 *            the user to access
	 */
	public int getUserStatus(String user) {
		synchronized (users) {
			return users.get(user).getStatus();
		}
	}

	/**
	 * Begins a poll procedure on the request query method to retrieve the next
	 * request
	 * 
	 * @param size:
	 *            an array to return the size of the request queue
	 * @param user:
	 *            The user account asociated to the request
	 */
	public Request getRequest(int[] size, String user) {
		Request req;
		size[0] = requests.size();
		req = (Request) queryRequests(Properties.PROT_POLL, null, user);
		return req;
	}

	/**
	 * Get a user account. This is not thread-safe
	 * 
	 * @param user:
	 *            the user to get
	 */
	public UserAccount getUser(String user) {
		return users.get(user);
	}

	/**
	 * Attempt to register a new user account after preliminary checksums
	 * 
	 * @param user:
	 *            the unique username
	 * @param pass:
	 *            the password
	 * @param age:
	 *            the user's age
	 * @param ss:
	 *            the user's social security number
	 */
	public boolean attemptRegistration(String user, String pass, int age, int ssn) throws RegistrationException {
		String lettersUser, numbersUser, specCharsUser;
		String lettersPass, numbersPass, specCharsPass;
		Class<?> clazz;
		lettersUser = user.replaceAll(letterRegex, "");
		numbersUser = user.replaceAll(numberRegex, "");
		specCharsUser = user.replaceAll(specCharRegex, "");
		lettersPass = pass.replaceAll(letterRegex, "");
		numbersPass = pass.replaceAll(numberRegex, "");
		specCharsPass = pass.replaceAll(specCharRegex, "");
		if (user.length() < Properties.MIN_SIZE_USER || user.length() > Properties.MAX_SIZE_USER
				|| lettersUser.length() < Properties.MIN_LETTER_USER
				|| numbersUser.length() < Properties.MIN_NUMBER_USER
				|| specCharsUser.length() < Properties.MIN_SPEC_CHAR_USER) {
			throw new InvalidUserNameException(user);
		}

		if (pass.length() < Properties.MIN_SIZE_PASS || pass.length() > Properties.MAX_SIZE_PASS
				|| lettersPass.length() < Properties.MIN_LETTER_PASS
				|| numbersPass.length() < Properties.MIN_NUMBER_PASS
				|| specCharsPass.length() < Properties.MIN_SPEC_CHAR_PASS) {
			throw new InvalidPasswordException(pass);
		}
		if (users.containsKey(user))
			throw new UsernameNotAvailableException(user);
		if (age >= Properties.SENIOR_MIN_AGE)
			clazz = SeniorUserAccount.class;
		else
			clazz = BasicUserAccount.class;
		if ((boolean) queryUsers(Properties.PROT_ADD, user, clazz, user, pass)) {
			queryPendingAccounts(Properties.PROT_ADD, user);
			queryRequests(Properties.PROT_ADD, new AccountRequest(user));
		}
		return true;
	}

	/**
	 * Useful toString method for human readable strings
	 */
	@Override
	public String toString() {
		return "Model [users=" + users + ", lockedAccounts=" + lockedAccounts + ", unlockedAccounts=" + unlockedAccounts
				+ ", admins=" + admins + ", rejectedAccounts=" + rejectedAccounts + ", requests=" + requests + "]";
	}

	/**
	 * Process requests in a thread-safe environment
	 * 
	 * @param r:
	 *            the new Request
	 * @param prot:
	 *            the procedural protocol
	 * @param the
	 *            admin account viewing requests. This keeps admins from abusing
	 *            privileges to accept their own requests
	 */
	public void processRequest(Request r, int prot, String admin) throws NoSuchUserException {
		UserAccount u;
		Map<?, ?> map;
		if (prot == Properties.PROT_CANCEL)
			queryRequests(Properties.PROT_ADD, r);

		if (r.getUser().equals(admins.get(admin).getUser()))
			try {
				throw new IllegalAdminException();
			} catch (IllegalAdminException e) {
				queryRequests(Properties.PROT_ADD, r);
				Console.print(System.err, e);
			}
		synchronized (users) {
			u = users.get(r.getUser());
			if (u == null)
				throw new NoSuchUserException(r.getUser());
			u.addToSet(prot, r);
		}
		map = ((Map<?, ?>) queryUsers(Properties.PROT_GET, null));
		synchronized (map) {
			if (map == null)
				throw new NoSuchUserException(r.getUser());
			if (r instanceof AccountRequest) {
				queryPendingAccounts(Properties.PROT_REMOVE, r.getUser());
				if (prot == Properties.PROT_APPROVE) {
					((UserAccount) map.get(r.getUser())).setStatus(Properties.ACCT_STAT_GOOD);
					queryUnlockedAccounts(Properties.PROT_ADD, r.getUser());
				}
				if (prot == Properties.PROT_REJECT) {
					((UserAccount) map.get(r.getUser())).setStatus(Properties.ACCT_STAT_REJECTED);
					queryRejectedAccounts(Properties.PROT_ADD, r.getUser());
				}
			}
			if (r instanceof LoanRequest) {
				if (prot == Properties.PROT_APPROVE) {
					((UserAccount) map.get(r.getUser())).processLoan(((LoanRequest) r).getAmount(),
							Properties.ACCPTED_LOAN_MSG);
				}
			}
			if (r instanceof CancelRequest) {
				((UserAccount) map.get(r.getUser())).cancelTransaction(((CancelRequest) r).getTransId());
			}
		}
	}

	/**
	 * Gets a user from a collection of maps in a thread-safe environment
	 * 
	 * @param prot:
	 *            the procedural protocol
	 */
	public Map<?, ?> getUsers(int prot) {
		Map<?, ?> map;
		switch (prot) {
		case Properties.PROT_LOCK:
			return (Map<?, ?>) m.queryLockedAccounts(Properties.PROT_GET, null);
		case Properties.PROT_UNLOCK:
			return (Map<?, ?>) m.queryUnlockedAccounts(Properties.PROT_GET, null);
		case Properties.PROT_PROMOTE:
			synchronized (users) {
				map = new Hashtable<String, UserAccount>(users);
			}
			map.keySet().removeAll(pendingAccounts.keySet());
			map.keySet().removeAll(admUsers);
			map.keySet().removeAll(rejectedAccounts.keySet());
			return map;

		case Properties.PROT_REJECT:
			return (Map<?, ?>) m.queryRejectedAccounts(Properties.PROT_GET, null);
		default:
			return map = (Map<?, ?>) m.queryUsers(Properties.PROT_GET, null);
		}
	}

	/**
	 * Try to lock an account from user input.
	 * 
	 * @param token:
	 *            the String read in from user
	 */
	public void tryLock(String token) throws NoSuchUserException, DuplicateAdminException, RestrictedAdminException {
		Set<String> ads;
		synchronized (admins) {
			ads = new HashSet<String>(admins.keySet());
		}
		ads.retainAll(users.keySet());
		if (ads.contains(token))
			throw new RestrictedAdminException(token);
		if (!(boolean) queryUnlockedAccounts(Properties.PROT_REMOVE, token))
			throw new NoSuchUserException(token);
		if (!(boolean) queryLockedAccounts(Properties.PROT_ADD, token))
			throw new InvalidUserNameException(token);
		users.get(token).setStatus(Properties.ACCT_STAT_LOCKED);
	}

	/**
	 * Try to unlock an account from user input.
	 * 
	 * @param token:
	 *            the String read in from user
	 */
	public void tryUnlock(String token, String admin) throws NoSuchUserException, IllegalAdminException {
		if (token == admins.get(admin).getUser())
			throw new IllegalAdminException();
		if (!(boolean) queryLockedAccounts(Properties.PROT_REMOVE, token))
			throw new NoSuchUserException(token);
		if (!(boolean) queryUnlockedAccounts(Properties.PROT_ADD, token))
			throw new InvalidUserNameException(token);
		users.get(token).setStatus(Properties.ACCT_STAT_GOOD);
	}

	/**
	 * Try to promote an account to admin from user input.
	 * 
	 * @param token:
	 *            the String read in from user
	 */
	public boolean tryPromote(String token) throws DuplicateAdminException {
		UserAccount ua;
		Set<String> ads;
		synchronized (admins) {
			ads = new HashSet<String>(admins.keySet());
		}
		ads.retainAll(users.keySet());
		Map<?, ?> map = getUsers(Properties.PROT_PROMOTE);
		if (!map.containsKey(token))
			throw new NoSuchUserException(token);
		if (ads.contains(token))
			throw new DuplicateAdminException(token);
		ua = (UserAccount) queryUsers(Properties.PROT_CONVERT, token, PremiumUserAccount.class);
		if (ua != null) {
			queryAdmins(Properties.PROT_ADD, token, AdminAccount.class, ua.getUser(), ua.getPass(), ua);
			return true;
		}
		return false;
	}

	/**
	 * Try to restore a user account from the rejected list from user input.
	 * 
	 * @param token:
	 *            the String read in from user
	 */
	public boolean tryRestore(String token) {
		if (!(boolean) queryRejectedAccounts(Properties.PROT_REMOVE, token)
				|| !(boolean) queryUnlockedAccounts(Properties.PROT_ADD, token))
			return false;
		synchronized (users) {
			users.get(token).setStatus(Properties.ACCT_STAT_GOOD);
		}
		return true;
	}

	/**
	 * Try to close out a user account from user input.
	 * 
	 * @param token:
	 *            the String read in from user
	 */

	public boolean tryClose(String token) throws CloseAdminException {
		Set<String> ads;
		synchronized (admins) {
			ads = new HashSet<String>(admins.keySet());
		}
		ads.retainAll(users.keySet());
		if (ads.contains(token))
			throw new CloseAdminException();
		return (boolean) queryUsers(Properties.PROT_REMOVE, token);
	}

	/**
	 * Process a transaction to a given user account. Also issues overdraft
	 * penalties
	 * 
	 * @param user:
	 *            the user associated to the transaction
	 * @param amount:
	 *            the amount of the transaction
	 * @param msg:
	 *            a description of the transaction
	 * @param b:
	 *            override flag for issuing overdraft penalties
	 */

	public Transaction processTransaction(String user, double amount, String msg, boolean b) {
		UserAccount ua;
		Map<?, ?> map = (Map<?, ?>) queryUsers(Properties.PROT_GET, null);
		synchronized (users) {
			try {
				return ((UserAccount) map.get(user)).processTransaction(amount, msg, b);
			} catch (AccountOverdraftException e) {
				ua = (UserAccount) map.get(user);
				Console.print(System.err, e);
				if (ua instanceof BasicUserAccount)
					amount = Properties.BASIC_USER_PENALTY;
				if (ua instanceof PremiumUserAccount)
					amount = Properties.PREMIUM_USER_PENALTY;
				if (ua instanceof SeniorUserAccount)
					amount = Properties.SENIOR_USER_PENALTY;
				if (ua instanceof RootUserAccount)
					amount = Properties.ROOT_USER_PENALTY;
				return ((UserAccount) map.get(user)).processTransaction(-amount, Properties.ERR_ACCT_OVERDRAFT_PENALTY,
						true);
			}
		}
	}

	/**
	 * Process a special transaction for loan payment and dispersal. Also issues
	 * overdraft penalties
	 * 
	 * @param user:
	 *            the user associated to the transaction
	 * @param id:
	 *            the loan id
	 * @param amount:
	 *            the amount of the transaction
	 * @param msg:
	 *            a description of the transaction
	 * @param b:
	 *            an override flag for issuing overdraft penalties
	 */

	public Transaction processLoan(String user, int id, double amount, String msg, boolean b) {
		UserAccount ua = users.get(user);
		try {

			return ua.processTransaction(id, amount, msg, false);
		} catch (AccountOverdraftException e) {
			if (ua instanceof BasicUserAccount)
				amount = Properties.BASIC_USER_PENALTY;
			if (ua instanceof PremiumUserAccount)
				amount = Properties.PREMIUM_USER_PENALTY;
			if (ua instanceof SeniorUserAccount)
				amount = Properties.SENIOR_USER_PENALTY;
			if (ua instanceof RootUserAccount)
				amount = Properties.ROOT_USER_PENALTY;
		}
		return ua.processTransaction(-amount, Properties.ERR_ACCT_OVERDRAFT_PENALTY, true);
	}

	/**
	 * Queues a transaction rollback request for processing by an admin
	 * 
	 * @param user:
	 *            the user associated with the request
	 * @param id:
	 *            the request id
	 */

	public boolean sendRequest(String user, int id) {
		if (!users.get(user).getTransactions().keySet().contains(id))
			throw new InvalidInputException();
		if (requestSet.containsKey(id))
			throw new DuplicateRequestException(id);
		return (boolean) queryRequests(Properties.PROT_ADD, new CancelRequest(user, id));
	}

	/**
	 * Queues a loan request for processing by an admin
	 * 
	 * @param user:
	 *            the user account associated to the request
	 * @param amount:
	 *            the amount of the loan
	 */
	public boolean sendRequest(String user, double amount) {
		if (users.get(user).getBalance() < 0)
			throw new DebtException();
		if (users.get(user).getLoans().size() == 1)
			throw new OutstandingLoanException();
		return (boolean) queryRequests(Properties.PROT_ADD, new LoanRequest(user, amount));
	}

	/**
	 * Gets a user's collection of transactions. Not thread-safe
	 * 
	 * @param user:
	 *            the user key to get transactions
	 */
	public Map<Integer, Transaction> getTransactions(String user) {
		return ((UserAccount) ((Map<?, ?>) queryUsers(Properties.PROT_GET, null)).get(user)).getTransactions();
	}

	/**
	 * Gets a user's collection of loans. Not thread-safe
	 * 
	 * @param token:
	 *            the user key to get loans
	 */
	public Map<Integer, Loan> getLoans(String user) {
		return users.get(user).getLoans();
	}

	/**
	 * Converts a usr account to a new account type by age . Currently only handles
	 * SeniorUserAccounts
	 * 
	 * @param o:
	 *            the Account object to cast
	 * @param args:
	 *            the observed variable that fired off the update
	 */
	@Override
	public void update(Observable o, Object arg) {
		if ((boolean) arg) {
			if (o instanceof UserAccount) {
				queryUsers(Properties.PROT_CONVERT, ((UserAccount) o).getUser(), SeniorUserAccount.class,
						(UserAccount) o);
			}
		}
	}
}