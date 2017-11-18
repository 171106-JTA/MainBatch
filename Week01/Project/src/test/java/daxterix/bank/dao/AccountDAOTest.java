package daxterix.bank.dao;

import daxterix.bank.model.Account;
import daxterix.bank.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountDAOTest {
    AccountDAO dao;
    UserDAO userDao;
    User testUser, testUser2;



    @Before
    public void setUp() throws Exception {
        dao = DAOUtils.getTestAccountDao();
        userDao = DAOUtils.getTestUserDao();
        testUser = new User("test@email.com", "pword");
        testUser2 = new User("test@email2.com", "pword");
        userDao.save(testUser);
        userDao.save(testUser2);
    }

    @After
    public void tearDown() throws Exception {
        dao.deleteAll();
        userDao.delete(testUser.getEmail());
        userDao.delete(testUser2.getEmail());
        dao = null;
    }

    @Test
    public void selectNonExistent() throws Exception {
        assertNull(dao.select(0));  // id sequences start from 1
    }

    @Test
    public void saveAndselect() throws Exception {
        Account ref1 = new Account(testUser.getEmail(), 0);
        dao.save(ref1);
        List<Account> allAccs = dao.selectAll();
        assertEquals(1, allAccs.size());

        Account saved = allAccs.get(0);
        assertEquals(ref1, saved);
        assertEquals(ref1, dao.select(saved.getNumber()));
    }

    @Test
    public void selectAll() throws Exception {
        Account ref1 = new Account(testUser.getEmail(), 0);
        dao.save(ref1);
        Account ref2 = new Account(testUser.getEmail(), 0);
        dao.save(ref2);
        Account ref3 = new Account(testUser2.getEmail(), 0);
        dao.save(ref3);
        Account ref4 = new Account(testUser2.getEmail(), 0);
        dao.save(ref4);

        List<Account> user1Accs = dao.selectForUser(testUser.getEmail());
        assertEquals(2, user1Accs.size());
        assertTrue(user1Accs.contains(ref1));
        assertTrue(user1Accs.contains(ref2));

        List<Account> user2Accs = dao.selectForUser(testUser2.getEmail());
        assertEquals(2, user2Accs.size());
        assertTrue(user2Accs.contains(ref3));
        assertTrue(user2Accs.contains(ref4));
    }

    @Test
    public void update() throws Exception {
        Account ref = new Account(testUser.getEmail(), 0);
        dao.save(ref);
        List<Account> allAccs = dao.selectAll();
        assertEquals(1, allAccs.size());
        Account saved = allAccs.get(0);
        assertEquals(ref, saved);

        double newBalance = 20000;
        ref.setBalance(newBalance);
        ref.setNumber(saved.getNumber());
        dao.update(ref);

        Account updated = dao.select(saved.getNumber());
        assertNotEquals(saved, updated);
        assertTrue(newBalance - updated.getBalance() == 0);
        assertEquals(ref, updated);
    }

    @Test
    public void delete() throws Exception {
        Account ref1 = new Account(testUser.getEmail(), 0);
        dao.save(ref1);
        Account ref2 = new Account(testUser.getEmail(), 0);
        dao.save(ref2);
        Account ref3 = new Account(testUser2.getEmail(), 0);
        dao.save(ref3);
        Account ref4 = new Account(testUser2.getEmail(), 0);
        dao.save(ref4);

        List<Account> allAccs = dao.selectAll();
        assertEquals(4, allAccs.size());

        Account tobeDeleted = allAccs.get(0);
        dao.delete(tobeDeleted.getNumber());
        allAccs = dao.selectAll();
        assertEquals(3, allAccs.size());
        assertFalse(allAccs.contains(tobeDeleted));

        tobeDeleted = allAccs.get(0);
        dao.delete(tobeDeleted.getNumber());
        allAccs = dao.selectAll();
        assertEquals(2, allAccs.size());
        assertFalse(allAccs.contains(tobeDeleted));
    }

    @Test
    public void deleteForUser() throws Exception {
        Account ref1 = new Account(testUser.getEmail(), 0);
        dao.save(ref1);
        Account ref2 = new Account(testUser.getEmail(), 0);
        dao.save(ref2);
        Account ref3 = new Account(testUser2.getEmail(), 0);
        dao.save(ref3);
        Account ref4 = new Account(testUser2.getEmail(), 0);
        dao.save(ref4);

        List<Account> allAccs = dao.selectAll();
        assertEquals(4, allAccs.size());

        dao.deleteForUser(testUser.getEmail());
        allAccs = dao.selectAll();
        assertEquals(2, allAccs.size());
        assertFalse(allAccs.contains(ref1));
        assertFalse(allAccs.contains(ref2));
        assertTrue(allAccs.contains(ref3));
        assertTrue(allAccs.contains(ref4));

        dao.deleteForUser(testUser2.getEmail());
        allAccs = dao.selectAll();
        assertEquals(0, allAccs.size());
    }

}