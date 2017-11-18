package daxterix.bank.model.old;

import java.time.format.DateTimeFormatter;

public class CreationRequest extends UserRequest {
    private static final long serialVersionUID = 6043802262589664464L;

    /**
     * one of these is created every time a user registered for an account
     * this is part of the approval process where an admin approves
     * a request for a new customer's account
     *
     * @param customer
     */
    public CreationRequest(Customer customer) {
        super(customer);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss MM/dd/yyyy");
        return String.format("(id %d) Creation request filed by %s on %s", id, requester.getUsername(), time.format(formatter));
    }
}
