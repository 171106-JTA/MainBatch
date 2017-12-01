import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// import com.revature.log.LogUtil;

/**
 * Handles (de)serialization.
 */
public class Cereal {

	public static ObjectInputStream ois;
	public static ObjectOutputStream oos;
	private List<User> userList = new ArrayList<>();

	/**
	 * Deserializes existing .ser file and returns non-empty user arraylist.
	 *
	 * @param accountFile
	 * @return
	 * @throws Exception
	 *             about FileNotFound
	 */
	@SuppressWarnings("unchecked") // cast to List<User>
	List<User> deserialize(String accountFile) throws Exception {
		ois = new ObjectInputStream(new FileInputStream(accountFile));
		userList = (List<User>) ois.readObject();
		ois.close();
		return userList;
	}

	/**
	 * Persists data by serializing list of User objects and storing in
	 * created/overwritten file.
	 *
	 * @param userList
	 */
	void serialize(List<User> userList) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream("accounts.ser"));
			oos.writeObject(userList);
			oos.close();
		} catch (IOException e) {
			// LogUtil.log.warn("can't create/write to .ser file", e);
		}
	}
}
