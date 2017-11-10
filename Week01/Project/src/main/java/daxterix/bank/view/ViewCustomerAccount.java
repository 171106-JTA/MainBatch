package daxterix.bank.view;

import daxterix.bank.model.Customer;
import daxterix.bank.view.Utils.InputUtils;

public class ViewCustomerAccount extends Page {
    private Customer customer;

    public ViewCustomerAccount(Customer customer) {
        this.customer = customer;
    }

    @Override
    protected Page _run() {
        printAccountInfo();
        printAccountOptions();

        String command = InputUtils.readLine("option");
        switch(command) {
            case "request": {

            }
            default: {
                System.err.println("invalid request");
            }
        }
        return null;
    }

    private void printAccountInfo() {
    }

    private void printAccountOptions() {
    }

    @Override
    public String getTitle() {
        return "Account Balance";
    }
}
