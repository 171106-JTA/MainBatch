package daxterix.bank.model;

public class CreationRequest extends UserRequest{
    private static final long serialVersionUID = 6043802262589664464L;

    public CreationRequest(Customer customer) {
        super(customer);
    }

    @Override
    public String toString() {
        return String.format("(id %d) Creation request filed by %s", id, requester.getUsername());
    }
}
