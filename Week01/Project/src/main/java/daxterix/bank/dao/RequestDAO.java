package daxterix.bank.dao;

import daxterix.bank.model.UserRequest;

public class RequestDAO extends SerialIDObjectDAO<UserRequest> {
    /**
     * Manages CRUD operations for UserRequest entities
     *
     * @param saveDir
     */
    public RequestDAO(String saveDir) {
        super(saveDir);
    }

    /**
     * the id of a UserRequest is its id field
     *
     * @param req
     * @return
     */
    @Override
    public String getId(UserRequest req) {
        return String.valueOf(req.getId());
    }

    /**
     * sets the id of a given UserRequest. See {@Link daxterix.bank.dao.SerialIDObjectDAO#setId setId}
     *
     * @param req
     * @param newId
     */
    @Override
    public void setId(UserRequest req, String newId) {
        req.setId(Long.parseLong(newId));
    }
}
