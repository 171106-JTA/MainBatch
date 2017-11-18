package daxterix.bank.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestDAOTest {
    RequestDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = DAOUtils.getTestRequestDao();
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }


    @Test
    public void selectNonExistent() throws Exception {
        assertNull(dao.select(0));  // id sequences start from 1
    }

    @Test
    public void select() throws Exception {
    }

    @Test
    public void selectForUser() throws Exception {
    }

    @Test
    public void selectAll() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void deleteForUser() throws Exception {
    }

}