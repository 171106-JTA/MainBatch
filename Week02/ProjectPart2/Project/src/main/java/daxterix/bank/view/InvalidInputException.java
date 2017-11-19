package daxterix.bank.view;

public class InvalidInputException extends Exception {
    /**
     * represents an invalid input by user
     */
    public InvalidInputException(){
        super();
    }

    public InvalidInputException(String msg){
        super(msg);
    }
}
