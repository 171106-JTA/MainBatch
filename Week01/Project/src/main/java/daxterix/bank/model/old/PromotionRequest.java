package daxterix.bank.model.old;

import java.time.format.DateTimeFormatter;

public class PromotionRequest extends UserRequest {
    private static final long serialVersionUID = 395311135212641805L;

    /**
     * represents a customer's request to be promoted to admin
     *
     * @param user
     */
    public PromotionRequest(User user) {
        super(user);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a MM/dd/yyyy");
        return String.format("(id %d) Promotion request filed by %s on %s", id, requester.getUsername(), time.format(formatter));
    }
}
