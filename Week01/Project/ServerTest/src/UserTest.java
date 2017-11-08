import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.revature.businessobject.user.User;
import com.revature.businessobject.user.UserRole;

class UserTest extends Assert {

	@Test
	void shouldCreateUserWithAdminRole() {
		User user = new User(1314232, UserRole.ADMIN);
		assertTrue("User role should be admin", user.getRole() == UserRole.ADMIN);
	}
	
	@Test
	void shouldCreateUserWithCustomerRole() {
		User user = new User(1314232, UserRole.CUSTOMER);
		assertTrue("User role should be customer", user.getRole() == UserRole.CUSTOMER);
	}
	
	@Test
	void shouldGetCorrectUserId() {
		User user = new User(12322342, UserRole.ADMIN);
		assertTrue("User id should be 12322342", user.getId() == 12322342);
	}
}
