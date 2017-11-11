package daxterix.bank.presistence;

import daxterix.bank.model.UserRequest;

public class RequestDAO extends SerialIDObjectDAO<UserRequest> {
    public RequestDAO(String saveDir) {
        super(saveDir);
    }

    @Override
    public String getId(UserRequest req) {
        return String.valueOf(req.getId());
    }

    @Override
    public void setId(UserRequest req, String newId) {
        req.setId(Long.parseLong(newId));
    }
}
