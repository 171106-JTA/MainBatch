package me.daxterix.trms.dao.exception;

public class NonExistentIdException extends Exception {
    public NonExistentIdException() {
        super("The given id does not exist");
    }

    public NonExistentIdException(String msg) {
        super(msg);
    }
}
