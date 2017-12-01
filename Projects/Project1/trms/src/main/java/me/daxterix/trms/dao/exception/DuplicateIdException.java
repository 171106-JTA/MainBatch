package me.daxterix.trms.dao.exception;

public class DuplicateIdException extends Exception {
    public DuplicateIdException() {
        super("A record with the given id already exists");
    }

    public DuplicateIdException(String msg) {
        super(msg);
    }
}
