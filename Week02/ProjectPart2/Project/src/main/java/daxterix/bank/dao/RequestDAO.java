package daxterix.bank.dao;

import daxterix.bank.model.UserRequest;

import java.sql.SQLException;
import java.util.List;

public interface RequestDAO {

    /**
     * select request record for given id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    UserRequest select(long id) throws SQLException;

    /**
     * select all requests filed by given user
     *
     * @param email
     * @return
     * @throws SQLException
     */
    List<UserRequest> selectForUser(String email) throws SQLException;

    /**
     * select all persisted requests
     *
     * @return
     * @throws SQLException
     */
    List<UserRequest> selectAll() throws SQLException;

    /**
     * save the given request, auto assigns an id to the request (given id is ignored)
     *
     * @param req
     * @return
     * @throws SQLException
     */
    int save(UserRequest req) throws SQLException;

    /**
     * update a request with given info, given id must match that of the request to be changed
     *
     * @param info
     * @return
     * @throws SQLException
     */
    int update(UserRequest info) throws SQLException;

    /**
     * delete given request (with ON DELETE CASCADE)
     *
     * @param id
     * @return
     * @throws SQLException
     */
    int delete(long id) throws SQLException;

    /**
     * delete all requests filed by the given user (with ON DELETE CASCADE)
     *
     * @param email
     * @return
     * @throws SQLException
     */
    int deleteForUser(String email) throws SQLException;

    /**
     * delete all persisted requests (with ON DELETE CASCADE)
     *
     * @return
     * @throws SQLException
     */
    int deleteAll() throws SQLException;
}
