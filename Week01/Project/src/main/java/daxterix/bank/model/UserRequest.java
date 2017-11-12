package daxterix.bank.model;

import java.time.LocalDateTime;

public class UserRequest extends Model {
    private static final long serialVersionUID = -2431727016053886785L;

    protected LocalDateTime time;
    protected User requester;
    protected long id;

    public UserRequest(User user) {
        super();
        requester = user;
        time = LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public User getRequester() {
        return requester;
    }

    /**
     * set the id of the request. To be used by PersistUtils
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
