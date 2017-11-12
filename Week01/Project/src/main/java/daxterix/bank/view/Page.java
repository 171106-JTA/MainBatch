package daxterix.bank.view;

public abstract class Page {
    protected abstract Page _run();
    private static final String DIVIDER = "______________________________________________________________________";

    public void run() {
        Page page = this;
        do {
            printPageTitle(page);
            page = page._run();
        }
        while(page != null);
    }

    public abstract String getTitle();


    private static void printDivider() {
        System.out.println(DIVIDER);
    }

    public static void printPageTitle(Page p) {
        System.out.print("\n\n");
        printDivider();
        System.out.println(p.getTitle());
        printDivider();
        System.out.println();
    }

    public static boolean checkQuit() {
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
