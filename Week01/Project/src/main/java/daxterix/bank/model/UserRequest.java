package daxterix.bank.model;

import java.time.LocalDateTime;

public class UserRequest {
    public static final char CREATION = 0;
    public static final char PROMOTION = 1;

    protected long id = -1;
    protected String filerEmail;
    protected LocalDateTime fileDate;
    protected int type;

    public UserRequest() {
        fileDate = LocalDateTime.now();
    }

    public UserRequest(String filerEmail, int type) {
        this.filerEmail = filerEmail;
        this.fileDate = LocalDateTime.now();
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilerEmail() {
        return filerEmail;
    }

    public void setFilerEmail(String filerEmail) {
        this.filerEmail = filerEmail;
    }

    public LocalDateTime getFileDate() {
        return fileDate;
    }

    public void setFileDate(LocalDateTime fileDate) {
        this.fileDate = fileDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRequest)) return false;

        UserRequest that = (UserRequest) o;
        // ignore ids if one id is -1 -- useful for testing
        if (id != that.id && !(id == -1 || that.id == -1)) return false;
        if (type != that.type) return false;
        if (!filerEmail.equals(that.filerEmail)) return false;
        return fileDate.equals(that.fileDate);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + filerEmail.hashCode();
        result = 31 * result + fileDate.hashCode();
        result = 31 * result + type;
        return result;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "id=" + id +
                ", filerEmail='" + filerEmail + '\'' +
                ", fileDate=" + fileDate +
                ", type=" + type +
                '}';
    }
}
