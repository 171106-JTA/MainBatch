package daxterix.bank.view.Utils;

public class InvalidInputException extends Exception {
    public InvalidInputException(){
        super();
    }

    public InvalidInputException(String msg){
        super(msg);
    }
}
