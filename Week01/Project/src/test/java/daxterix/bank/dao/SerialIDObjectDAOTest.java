package daxterix.bank.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class SerialIDObjectDAOTest {
    /*
    static final String requestSaveDir = (new File(System.getProperty("user.home"),"Desktop\\testUserInfo\\request")).getAbsolutePath();

    RequestDAO requestDao;

    @Before
    public void setUp() throws Exception {
        requestDao = new RequestDAO(requestSaveDir);
    }

    @After
    public void tearDown() throws Exception {
        requestDao.dropDatabase();
    }

    @Test
    public void getNextId() throws Exception {
        UserRequest u = new PromotionRequest(new Customer("user", "password"));
        long orig = requestDao.getNextId();

        for (int i = 0; i < 20; ++i) {
            long next = requestDao.getNextId();;
            assertTrue(next > orig);
            orig = next;
        }

        // fetches a greater id even across objects.
        // also does it across program runs if database is not dropped,
        // but can't easily test that here as there is not good way to
        // test that its not a static class
        RequestDAO newRequestDao = new RequestDAO(requestSaveDir);
        long indepentFetch = newRequestDao.getNextId();
        assertTrue(indepentFetch > orig);
    }

    @Test
    public void saveAndRead() throws Exception {
        UserRequest ref = new CreationRequest(new Customer("user", "pass"));
        List<UserRequest> fetched = requestDao.readAll();
        assertTrue(fetched.isEmpty());

        requestDao.save(ref);
        fetched = requestDao.readAll();
        assertEquals(1, fetched.size());

        UserRequest saved = fetched.get(0);
        // don't know the id, so can't use .equals
        assertEquals(ref.getRequester(), saved.getRequester());
        assertEquals(ref.getTime(), saved.getTime());
    }

    @Test
    public void readNonExistent() throws Exception {
        UserRequest ref = new CreationRequest(new Customer("user", "pass"));
        List<UserRequest> fetched = requestDao.readAll();
        assertTrue(fetched.isEmpty());

        requestDao.save(ref);
        fetched = requestDao.readAll();
        assertEquals(1, fetched.size());

        UserRequest nonExistent = requestDao.readById("-1");
        assertNull(nonExistent);
    }

    @Test
    public void update() throws Exception {

        // here to make sure other records are not affected
        PromotionRequest neverModified = new PromotionRequest(new Customer("p", "p"));
        requestDao.save(neverModified);

        String password = "pass";
        String username = "user";
        PromotionRequest orig = new PromotionRequest(new Customer(username, password));
        requestDao.save(orig);

        long origId = orig.getId();
        PromotionRequest unmodifiedFetch = (PromotionRequest) requestDao.readById(""+origId);
        assertEquals(orig, unmodifiedFetch);

        String newPassword = password + password;
        PromotionRequest modified = new PromotionRequest(new Customer(username, newPassword)); // now modified because the times fields are different
        modified.setId(origId);
        requestDao.update(modified);

        List<UserRequest> allSaved = requestDao.readAll();
        assertEquals(2, allSaved.size());
        assertTrue(allSaved.contains(modified));
        assertFalse(allSaved.contains(orig));
        assertTrue(allSaved.contains(neverModified));
    }

    @Test
    public void delete() throws Exception {
        UserRequest ref1 = new CreationRequest(new Customer("user", "pass"));
        UserRequest toBeDeleted = new CreationRequest(new Customer("user", "pass"));
        List<UserRequest> fetched = requestDao.readAll();
        assertTrue(fetched.isEmpty());

        assertTrue(requestDao.save(ref1));
        assertTrue(requestDao.save(toBeDeleted));
        fetched = requestDao.readAll();
        assertNotNull(fetched);
        assertEquals(2, fetched.size());

        UserRequest notDeleted = fetched.get(0);
        UserRequest deleted = fetched.get(1);

        assertTrue(requestDao.deleteById("" + deleted.getId()));
        fetched = requestDao.readAll();
        assertNotNull(fetched);

        assertEquals(1, fetched.size());
        assertEquals(fetched.get(0), notDeleted);
    }
    */
}