package daxterix.bank.view;

import daxterix.bank.dao.DAOUtils;
import daxterix.bank.model.old.CreationRequest;
import daxterix.bank.model.old.Customer;
import daxterix.bank.dao.old.RequestDAO;

public class RegisterCustomerPage extends Page {
    /**
     * see Page._run()
     *
     * @return
     */
    @Override
    protected Page _run() {
        String username = InputUtils.readNonEmptyLine("username");
        String password = InputUtils.readAndConfirm(()-> InputUtils.readMasked("password"));
        System.out.printf("user %s created\n", username);

        RequestDAO dao = DAOUtils.getRequestDao();
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
