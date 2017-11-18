package daxterix.bank.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static daxterix.bank.TestUtils.assertMatchingContents;
import static org.junit.Assert.*;

public class ObjectDAOTest {

    /*
    static final String adminSaveDir = (new File(System.getProperty("user.home"),"Desktop\\testUserInfo\\admin")).getAbsolutePath();

    AdminDAO adminDao;


    @Before
    public void setUp() throws Exception {
        adminDao = new AdminDAO(adminSaveDir);
    }

    @After
    public void tearDown() throws Exception {
        adminDao.dropDatabase();
        adminDao = null;
    }

    @Test
    public void readById() throws Exception {
        Admin ref = new Admin("user", "pass");
        adminDao.save(ref);
        Admin fetched = adminDao.readById(ref.getUsername());
        assertEquals(ref.getUsername(), fetched.getUsername());
    }

    @Test
    public void readNonExistentID() throws Exception {
        Admin ref = new Admin("user", "pass");
        adminDao.save(ref);
        Admin fetched = adminDao.readById(ref.getUsername());
        assertEquals(ref.getUsername(), fetched.getUsername());

        assertEquals(null, adminDao.readById("nonExistentID"));
    }

    @Test
    public void readAll() throws Exception {
        List<Admin> ref = new ArrayList<>();

        for (int i = 0; i < 4; ++i)
            ref.add(new Admin("user" + i, "password"));

        for (Admin a: ref)
            adminDao.save(a);

        List<Admin> fetched = adminDao.readAll();

        assertEquals(ref.size(), fetched.size());
        for (Admin f: fetched)
            assertTrue(ref.contains(f));
    }

    @Test
    public void update() throws Exception {

        // here to make sure other records are not affected
        Admin neverModified = new Admin("p", "p");
        adminDao.save(neverModified);

        String password = "pass";
        String username = "user";
        Admin orig = new Admin(username, password);
        adminDao.save(orig);

        Admin unmodifiedFetch = adminDao.readById(orig.getUsername());
        assertEquals(orig, unmodifiedFetch);

        String newPassword = password + password;
        Admin modified = new Admin(username, newPassword);
        adminDao.update(modified);

        List<Admin> allSaved = adminDao.readAll();
        assertEquals(2, allSaved.size());
        assertFalse(allSaved.contains(orig));
        assertTrue(allSaved.contains(modified));
        assertTrue(allSaved.contains(neverModified));
    }

    @Test
    public void updateNonExistent() throws Exception {
        // here to make sure other records are not affected
        Admin neverModified = new Admin("p", "p");
        adminDao.save(neverModified);

        String password = "pass";
        String username = "user";
        Admin orig = new Admin(username, password);
        adminDao.save(orig);

        Admin unmodifiedFetch = adminDao.readById(orig.getUsername());
        assertEquals(orig, unmodifiedFetch);

        String newUsername = username + username;
        Admin nonExistentIDAdmin = new Admin(newUsername, newUsername);
        assertFalse(adminDao.update(nonExistentIDAdmin));

        List<Admin> allSaved = adminDao.readAll();
        assertEquals(2, allSaved.size());
        assertFalse(allSaved.contains(nonExistentIDAdmin));
        assertTrue(allSaved.contains(orig));
        assertTrue(allSaved.contains(neverModified));

    }

    @Test
    public void deleteById() throws Exception {
        List<Admin> ref = new ArrayList<>();
        for (int i = 0; i < 5; ++i)
            ref.add(new Admin("user" + i, "password"));
        for (Admin a: ref)
            adminDao.save(a);

        // make sure things properly saved
        List<Admin> fetched = adminDao.readAll();
        assertMatchingContents(ref, fetched);

        // pop off the ref, and remove it from the db
        Admin removedUser1 = ref.remove(ref.size() - 1);
        adminDao.deleteById(removedUser1.getUsername());

        // make sure contents is same as ref
        List<Admin> fetchedPostDelete = adminDao.readAll();
        assertMatchingContents(ref, fetchedPostDelete);
    }


    @Test
    public void deleteNonExistent() throws Exception {
        List<Admin> ref = new ArrayList<>();
        for (int i = 0; i < 5; ++i)
            ref.add(new Admin("user" + i, "password"));
        for (Admin a: ref)
            adminDao.save(a);

        // make sure things properly saved
        List<Admin> fetched = adminDao.readAll();
        assertMatchingContents(ref, fetched);

        // remove a user with non existent id and should fail, with
        // database contents untouched
        assertFalse(adminDao.deleteById("nonExistentID"));
        List<Admin> fetchedAgain = adminDao.readAll();
        assertMatchingContents(ref, fetchedAgain);
    }
    */
}