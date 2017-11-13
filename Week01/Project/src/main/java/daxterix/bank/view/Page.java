package daxterix.bank.view;

public abstract class Page {
    private static final String DIVIDER = "______________________________________________________________________";
    /**
     * Defines the page's  interaction with the user, and what page it leads
     * to be run next depending on user's interaction
     *
     * @return - the next page to be _run()
     */
    protected abstract Page _run();

    /**
     * Runs the given page to completion.
     * Each page is a link in a chain, after running, each page returns the next page to be run,
     * This continues until the page being run returns null, marking an exit to the application
     */
    public void run() {
        Page page = this;
        do {
            page.printTitle();
            page = page._run();
        }
        while(page != null);
    }

    /**
     * get title of page as determined by implementing class
     *
     * @return
     */
    public abstract String getTitle();


    private static void printDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * print the title
     */
    public void printTitle() {
        System.out.print("\n\n");
        printDivider();
        System.out.println(getTitle());
        printDivider();
        System.out.println();
    }

    /**
     * obtain user input on whether to quit
     *
     * @return - true if user indicates intent to quit; false otherwise
     */
    public boolean checkQuit() {
       String cmd = InputUtils.readLine("'quit' to quit or anything else to try-again");
       switch(cmd) {
            case "q":
                return true;
            case "quit":
            case "exit":
                return true;
            default:
                return false;
       }
    }

    /**
     * given a set of available operations (cmds), and their corresponding
     * codes (which triggers said operations), prints then in formatted columns
     *
     * @param cmds -  available operations
     * @param cmdCodes - triggering commands
     */
    public void printCommands(String[] cmds, String[] cmdCodes) {
        System.out.println("You can do one of the following:");

        for (int i = 0; i+1 < cmds.length; i += 2) {
            String left = String.format("(%s) %s", cmdCodes[i], cmds[i]);
            String right = String.format("(%s) %s", cmdCodes[i+1], cmds[i+1]);
            System.out.printf("%-50s\t%s\n", left, right);
        }
        System.out.println();
        System.out.println();
    }
}
