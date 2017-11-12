package daxterix.bank.model;

public class CreationRequest extends UserRequest{
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
        return String.format("(id %d) Creation request filed by %s on %s", id, requester.getUsername(), time);
    }
}
