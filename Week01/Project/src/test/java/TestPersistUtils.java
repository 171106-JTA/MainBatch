import daxterix.bank.model.Customer;
import daxterix.bank.model.PromotionRequest;
import daxterix.bank.model.UserRequest;

public class TestPersistUtils {
    public static void main(String[] args) {
        UserRequest req = new PromotionRequest(new Customer("p", "p"));
        System.out.println(req);
    }
}
