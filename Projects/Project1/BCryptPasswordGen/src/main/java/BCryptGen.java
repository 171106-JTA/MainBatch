
import org.mindrot.jbcrypt.*;

public class BCryptGen {

	public static void main(String[] args) {
		String salt;
		for(int i = 0; i < 8; i++) {
			salt = BCrypt.gensalt();
			System.out.println(BCrypt.hashpw("Pass1", salt) + "\n" + salt + "\n");
		}
	}
}
