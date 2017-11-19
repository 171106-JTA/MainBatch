package daxterix.bank.view.page;

import daxterix.bank.view.InputUtils;
import daxterix.bank.view.OutputUtils;

public class WelcomePage extends Page{

    /**
     * see Page._run()
     * user to app, giving them options to register or login as customers or login as admins
     *
     * @return
     */
    @Override
    protected Page _run() {
        String[] cmds = {"Register as a customer", "Login as a Customer", "Login as an Admin", "View instructions"};
        String[] codes = {"rc", "lc", "la", "help"};
        OutputUtils.printCommands(cmds, codes);

        while(true){
            String cmd = InputUtils.readLine("command");
            switch (cmd) {
                case "":
                    break;
                case "rc":
                    return new RegisterCustomerPage();
                case "lc":
                    return new LoginCustomerPage();
                case "la":
                    return new LoginAdminPage();
                case "help":
                    OutputUtils.printCommands(cmds, codes);
                    break;
                case "Quit":
                    System.out.println("Be seeing ya!");
                    return null;
                default:
                    System.out.println("Invalid command. Try again.");
                    break;
            }
        }
    }

    /**
     * get the title of the page. see Page.getTitle
     *
     * @return
     */
    @Override
    public String getTitle() {
        return "Welcome to the Nameless Bank";
    }

}
