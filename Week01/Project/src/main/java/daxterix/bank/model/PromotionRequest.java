package daxterix.bank.model;

public class PromotionRequest extends UserRequest {
    private static final long serialVersionUID = 395311135212641805L;

    public PromotionRequest(User user) {
        super(user);
    }

    @Override
    public String toString() {
        return String.format("(id %d) Promotion request filed by %s on %s", id, requester.getUsername(), time);
    }
}
