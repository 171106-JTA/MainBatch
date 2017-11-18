package daxterix.bank.model;

import java.time.LocalDateTime;

public class UserRequest2 {
    protected long id;
    protected String filerEmail;
    protected LocalDateTime fileDate;
    protected int type;

    public UserRequest2() {
    }

    public UserRequest2(long id, String filerEmail, LocalDateTime fileDate, int type) {
        this.id = id;
        this.filerEmail = filerEmail;
        this.fileDate = fileDate;
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
        if (!(o instanceof UserRequest2)) return false;

        UserRequest2 that = (UserRequest2) o;

        if (id != that.id) return false;
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
        return "UserRequest2{" +
                "id=" + id +
                ", filerEmail='" + filerEmail + '\'' +
                ", fileDate=" + fileDate +
                ", type=" + type +
                '}';
    }
}
