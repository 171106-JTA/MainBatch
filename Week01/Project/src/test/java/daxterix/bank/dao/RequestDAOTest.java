package daxterix.bank.dao;

import daxterix.bank.model.User;
import daxterix.bank.model.UserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class RequestDAOTest {
    RequestDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = DAOUtils.getTestRequestDao();
    }

    @After
    public void tearDown() throws Exception {
        dao.deleteAll();
        dao = null;
    }

    @Test
    public void selectNonExistent() throws Exception {
        assertNull(dao.select(0));  // id sequences start from 1
    }

    @Test
    public void saveAndselect() throws Exception {
        UserDAO userDao = DAOUtils.getTestUserDao();
        User testUser = new User("test@email.com", "pword");
        try {
            userDao.save(testUser);
            UserRequest ref1 = new UserRequest(testUser.getEmail(), UserRequest.CREATION);
            dao.save(ref1);
            List<UserRequest> allReqs = dao.selectAll();
            assertEquals(1, allReqs.size());

            UserRequest saved = allReqs.get(0);
            assertEquals(ref1, saved);
            assertEquals(ref1, dao.select(saved.getId()));
        }
        finally {
            try {userDao.delete(testUser.getEmail());} catch(Exception ignored){}
        }
    }

    @Test
    public void selectForUser() throws Exception {

        UserDAO userDao = DAOUtils.getTestUserDao();
        User testUser1 = new User("test@email.com", "pword");
        User testUser2 = new User("test2@email.com", "pword");
        try {
            userDao.save(testUser1);
            userDao.save(testUser2);

            UserRequest ref1 = new UserRequest(testUser1.getEmail(), UserRequest.CREATION);
            dao.save(ref1);
            UserRequest ref2 = new UserRequest(testUser1.getEmail(), UserRequest.PROMOTION);
            dao.save(ref2);
            UserRequest ref3 = new UserRequest(testUser2.getEmail(), UserRequest.CREATION);
            dao.save(ref3);
            UserRequest ref4 = new UserRequest(testUser2.getEmail(), UserRequest.PROMOTION);
            dao.save(ref4);

            List<UserRequest> user1Reqs = dao.selectForUser(testUser1.getEmail());
            assertEquals(2, user1Reqs.size());
            assertTrue(user1Reqs.contains(ref1));
            assertTrue(user1Reqs.contains(ref2));

            List<UserRequest> user2Reqs = dao.selectForUser(testUser2.getEmail());
            assertEquals(2, user2Reqs.size());
            assertTrue(user2Reqs.contains(ref3));
            assertTrue(user2Reqs.contains(ref4));
        }
        finally {
            try {userDao.delete(testUser1.getEmail());} catch(Exception ignored){}
            try {userDao.delete(testUser2.getEmail());} catch(Exception ignored){}
        }
    }

    @Test
    public void selectAll() throws Exception {
        UserDAO userDao = DAOUtils.getTestUserDao();
        User testUser1 = new User("test@email.com", "pword");
        User testUser2 = new User("test2@email.com", "pword");
        try {
            userDao.save(testUser1);
            userDao.save(testUser2);

            UserRequest ref1 = new UserRequest(testUser1.getEmail(), UserRequest.CREATION);
            dao.save(ref1);
            UserRequest ref2 = new UserRequest(testUser1.getEmail(), UserRequest.PROMOTION);
            dao.save(ref2);
            UserRequest ref3 = new UserRequest(testUser2.getEmail(), UserRequest.CREATION);
            dao.save(ref3);
            UserRequest ref4 = new UserRequest(testUser2.getEmail(), UserRequest.PROMOTION);
            dao.save(ref4);

            List<UserRequest> allReqs = dao.selectAll();
            assertEquals(4, allReqs.size());
            assertTrue(allReqs.contains(ref1));
            assertTrue(allReqs.contains(ref2));
            assertTrue(allReqs.contains(ref3));
            assertTrue(allReqs.contains(ref4));
        }
        finally {
            try {userDao.delete(testUser1.getEmail());} catch(Exception ignored){}
            try {userDao.delete(testUser2.getEmail());} catch(Exception ignored){}
        }
    }

    @Test
    public void update() throws Exception {
        UserDAO userDao = DAOUtils.getTestUserDao();
        User testUser = new User("test@email.com", "pword");
        try {
            userDao.save(testUser);
            UserRequest ref = new UserRequest(testUser.getEmail(), UserRequest.CREATION);
            dao.save(ref);
            List<UserRequest> allReqs = dao.selectAll();
            assertEquals(1, allReqs.size());
            UserRequest saved = allReqs.get(0);
            assertEquals(ref, saved);

            ref.setFileDate(LocalDateTime.now());
            ref.setId(saved.getId());
            dao.update(ref);

            UserRequest updated = dao.select(saved.getId());
            assertNotEquals(saved, updated);
            assertEquals(ref, updated);
        }
        finally {
            try {userDao.delete(testUser.getEmail());} catch(Exception ignored){}
        }
    }

    @Test
    public void delete() throws Exception {
        UserDAO userDao = DAOUtils.getTestUserDao();
        User testUser1 = new User("test@email.com", "pword");
        User testUser2 = new User("test2@email.com", "pword");
        try {
            userDao.save(testUser1);
            userDao.save(testUser2);

            UserRequest ref1 = new UserRequest(testUser1.getEmail(), UserRequest.CREATION);
            dao.save(ref1);
            UserRequest ref2 = new UserRequest(testUser1.getEmail(), UserRequest.PROMOTION);
            dao.save(ref2);
            UserRequest ref3 = new UserRequest(testUser2.getEmail(), UserRequest.CREATION);
            dao.save(ref3);
            UserRequest ref4 = new UserRequest(testUser2.getEmail(), UserRequest.PROMOTION);
            dao.save(ref4);

            List<UserRequest> allReqs = dao.selectAll();
            assertEquals(4, allReqs.size());

            UserRequest tobeDeleted = allReqs.get(0);
            dao.delete(tobeDeleted.getId());
            allReqs = dao.selectAll();
            assertEquals(3, allReqs.size());
            assertFalse(allReqs.contains(tobeDeleted));

            tobeDeleted = allReqs.get(0);
            dao.delete(tobeDeleted.getId());
            allReqs = dao.selectAll();
            assertEquals(2, allReqs.size());
            assertFalse(allReqs.contains(tobeDeleted));
        }
        finally {
            try {userDao.delete(testUser1.getEmail());} catch(Exception ignored){}
            try {userDao.delete(testUser2.getEmail());} catch(Exception ignored){}
        }

    }

    @Test
    public void deleteForUser() throws Exception {
        UserDAO userDao = DAOUtils.getTestUserDao();
        User testUser1 = new User("test@email.com", "pword");
        User testUser2 = new User("test2@email.com", "pword");
        try {
            userDao.save(testUser1);
            userDao.save(testUser2);

            UserRequest ref1 = new UserRequest(testUser1.getEmail(), UserRequest.CREATION);
            dao.save(ref1);
            UserRequest ref2 = new UserRequest(testUser1.getEmail(), UserRequest.PROMOTION);
            dao.save(ref2);
            UserRequest ref3 = new UserRequest(testUser2.getEmail(), UserRequest.CREATION);
            dao.save(ref3);
            UserRequest ref4 = new UserRequest(testUser2.getEmail(), UserRequest.PROMOTION);
            dao.save(ref4);

            List<UserRequest> allReqs = dao.selectAll();
            assertEquals(4, allReqs.size());

            dao.deleteForUser(testUser1.getEmail());
            allReqs = dao.selectAll();
            assertEquals(2, allReqs.size());
            assertFalse(allReqs.contains(ref1));
            assertFalse(allReqs.contains(ref2));
            assertTrue(allReqs.contains(ref3));
            assertTrue(allReqs.contains(ref4));

            dao.deleteForUser(testUser2.getEmail());
            allReqs = dao.selectAll();
            assertEquals(0, allReqs.size());
        }
        finally {
            try {userDao.delete(testUser1.getEmail());} catch(Exception ignored){}
            try {userDao.delete(testUser2.getEmail());} catch(Exception ignored){}
        }
    }

}