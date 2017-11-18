package daxterix.bank.model;

public class User2 {
    protected String email;
    protected String password;
    protected boolean isAdmin;
    protected boolean isLocked;

    /**
     * models an account that can log into the application,
     * with which the user can interact with the app
     * this includes the Customer as well as the Admin accounts
     *
     * @param email
     * @param password
     */
    public User2(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User2() {}

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User2)) return false;

        User2 user2 = (User2) o;

        if (isAdmin != user2.isAdmin) return false;
        if (isLocked != user2.isLocked) return false;
        if (!email.equals(user2.email)) return false;
        return password.equals(user2.password);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (isAdmin ? 1 : 0);
        result = 31 * result + (isLocked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User2{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", isLocked=" + isLocked +
                '}';
    }
}
