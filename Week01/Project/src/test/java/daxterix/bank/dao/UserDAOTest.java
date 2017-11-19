package daxterix.bank.dao;

import daxterix.bank.model.Account;
import daxterix.bank.model.User;
import daxterix.bank.model.UserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {

    UserDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = DAOUtils.getTestUserDao();
    }

    @After
    public void tearDown() throws Exception {
        dao.deleteAll();
        dao = null;
    }

    @Test
    public void selectNonExistent() throws Exception {
        assertNull(dao.select("nonexistent"));
    }

    @Test
    public void select() throws Exception {
        User ref1 = new User("test.test@email.com", "unsecurepassword");
        dao.save(ref1);
        User saved = dao.select(ref1.getEmail());
        assertEquals(ref1, saved);
        assertEquals(ref1, dao.select(ref1.getEmail()));
    }

    @Test
    public void saveAndselectAll() throws Exception {
        List<User> accs = dao.selectAll();
        assertTrue(accs.isEmpty());

        User ref1 = new User("email1", "pass1");
        dao.save(ref1);
        accs = dao.selectAll();
        assertEquals(1, accs.size());
        assertTrue(accs.contains(ref1));

        User ref2 = new User("email2", "pass2");
        dao.save(ref2);
        accs = dao.selectAll();
        assertEquals(2, accs.size());
        assertTrue(accs.contains(ref1));
        assertTrue(accs.contains(ref2));

        User ref3 = new User("email3", "pass3");
        dao.save(ref3);
        accs = dao.selectAll();
        assertEquals(3, accs.size());
        assertTrue(accs.contains(ref1));
        assertTrue(accs.contains(ref2));
        assertTrue(accs.contains(ref3));

        User ref4 = new User("email4", "pass4");
        dao.save(ref4);
        accs = dao.selectAll();
        assertEquals(4, accs.size());
        assertTrue(accs.contains(ref1));
        assertTrue(accs.contains(ref2));
        assertTrue(accs.contains(ref3));
        assertTrue(accs.contains(ref4));
    }


    @Test
    public void update() throws Exception {
        User ref = new User("test.test@email.com", "unsecurepassword");
        dao.save(ref);
        List<User> allUsers = dao.selectAll();
        assertEquals(1, allUsers.size());
        User saved = allUsers.get(0);
        assertEquals(ref, saved);

        String newPassword = "changedPassword";
        ref.setPassword(newPassword);
        dao.update(ref);

        User updated = dao.select(ref.getEmail());
        assertNotEquals(saved, updated);
        assertEquals(newPassword, updated.getPassword());
        assertEquals(ref, updated);

    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void selectForAccount() throws Exception {
        AccountDAO accDao = DAOUtils.getTestAccountDao();
        try {
            User user1 = new User("email1", "pass1");
            User user2 = new User("email2", "pass2");
            dao.save(user1);
            dao.save(user2);

            int balance1 = 1, balance2 = 2, balance3 = 3, balance4 = 4;
            Account acc1 = new Account(user1.getEmail(), balance1);
            Account acc2 = new Account(user2.getEmail(), balance2);
            Account acc3 = new Account(user2.getEmail(), balance3);
            Account acc4 = new Account(user2.getEmail(), balance4);

            List<Account> accts = Arrays.asList(acc1, acc2, acc3, acc4);
            for (Account acc : accts)
                accDao.save(acc);

            List<Account> savedAccts = accDao.selectAll();
            assertEquals(accts.size(), savedAccts.size());
            for (Account savedAcc : savedAccts) {
                assertTrue(accts.contains(savedAcc));
                User queriedOwner = dao.selectForAccount(savedAcc.getNumber());
                if (savedAcc.getBalance() > balance1)
                    assertEquals(user2, queriedOwner);
                else
                    assertEquals(user1, queriedOwner);
            }
        }
        finally {
            accDao.deleteAll();
        }
    }

    @Test
    public void selectForRequest() throws Exception {
        RequestDAO reqDao = DAOUtils.getTestRequestDao();
        try {
            User user1 = new User("email1", "pass1");
            User user2 = new User("email2", "pass2");
            dao.save(user1);
            dao.save(user2);

            UserRequest req1 = new UserRequest(user1.getEmail(), UserRequest.PROMOTION);
            UserRequest req2 = new UserRequest(user2.getEmail(), UserRequest.CREATION);
            UserRequest req3 = new UserRequest(user2.getEmail(), UserRequest.CREATION);
            UserRequest req4 = new UserRequest(user2.getEmail(), UserRequest.CREATION);

            List<UserRequest> reqs = Arrays.asList(req1, req2, req3, req4);
            for (UserRequest req : reqs)
                reqDao.save(req);

            List<UserRequest> savedReqs = reqDao.selectAll();
            assertEquals(reqs.size(), savedReqs.size());
            for (UserRequest savedReq : savedReqs) {
                assertTrue(reqs.contains(savedReq));
                User queriedOwner = dao.selectForRequest(savedReq.getId());
                if (savedReq.getType() == UserRequest.CREATION)
                    assertEquals(user2, queriedOwner);
                else
                    assertEquals(user1, queriedOwner);
            }
            for (UserRequest savedReq : savedReqs)
                reqDao.delete(savedReq.getId());
        }
        finally {
            reqDao.deleteAll();
        }
    }

}