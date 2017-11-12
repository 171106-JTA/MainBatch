package daxterix.bank.view;

public class WelcomePage extends Page{

    /**
     * see Page._run()
     *
     * @return
     */
    @Override
    protected Page _run() {
        String[] cmds = {"Register as a customer", "Login as a Customer", "Login as an Admin", "View instructions"};
        String[] codes = {"rc", "lc", "la", "help"};
        printCommands(cmds, codes);

        while(true){
            String cmd = InputUtils.readLine("command");
            switch (cmd) {
                case "rc":
                    return new RegisterCustomerPage();
                case "lc":
                    return new LoginCustomerPage();
                case "la":
                    return new LoginAdminPage();
                case "help":
                    printCommands(cmds, codes);
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
     * see Page._run()
     *
     * @return
     */
    @Override
    public String getTitle() {
        return "Welcome to the <insert suitable bank title> Bank";
    }

}
