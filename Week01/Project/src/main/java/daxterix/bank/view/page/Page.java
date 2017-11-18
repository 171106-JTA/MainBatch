package daxterix.bank.view.page;

import daxterix.bank.view.InputUtils;
import daxterix.bank.view.OutputUtils;

public abstract class Page {
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
            OutputUtils.printTitle(page);
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
}
