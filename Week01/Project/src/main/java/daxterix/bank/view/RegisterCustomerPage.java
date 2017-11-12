package daxterix.bank.view;

import daxterix.bank.model.CreationRequest;
import daxterix.bank.model.Customer;
import daxterix.bank.presistence.PersistUtils;
import daxterix.bank.presistence.RequestDAO;

public class RegisterCustomerPage extends Page {
    @Override
    protected Page _run() {
        String username = InputUtils.readLine("username");
        String password = InputUtils.readAndConfirm(()-> InputUtils.readMasked("password"));
        System.out.printf("user %s created\n", username);

        RequestDAO dao = PersistUtils.getRequestDao();
        if  (dao.save(new CreationRequest(new Customer(username, password))))
            System.out.println("Success!");
        else
            System.out.println("Error: request creation failed. Please try again later.");

        return new WelcomePage();
    }

    @Override
    public String getTitle() {
        return "To register, fill in the fields below";
    }
}
