package daxterix.bank.model.old;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = -2872564267620672334L;

    protected String username;
    protected String password;

    /**
     * models an account that can log into the application,
     * with which the user can interact with the app
     * this includes the Customer as well as the Admin accounts
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof User))
            return false;

        User user = (User) obj;
        return username.equals(user.username) && password.equals(user.password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
