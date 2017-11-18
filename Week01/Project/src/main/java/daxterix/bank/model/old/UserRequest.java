package daxterix.bank.model.old;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class UserRequest implements Serializable {
    private static final long serialVersionUID = -2431727016053886785L;

    protected LocalDateTime time;
    protected User requester;
    protected long id;

    /**
     * Stores information about a request, such as a promotion
     * request or an account creation request
     *
     * @param user
     */
    public UserRequest(User user) {
        super();
        requester = user;
        time = LocalDateTime.now();
    }

    /**
     * get time of request creation
     *
     * @return
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * get User that filed the request
     *
     * @return
     */
    public User getRequester() {
        return requester;
    }

    /**
     * set the id of the request. To be used by DAOUtils
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof UserRequest))
            return false;
        UserRequest req = (UserRequest) obj;
        return time.equals(req.time) && requester.equals(req.requester) && id == req.id;
    }
}
