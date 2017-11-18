package daxterix.bank.view;

import static daxterix.bank.view.OutputUtils.AnsiColor.ANSI_RESET;

public class OutputUtils {
    private static final String DIVIDER = "____________________________________________________________________________________________________";

    /**
     * given a set of available operations (cmds), and their corresponding
     * codes (which triggers said operations), prints then in formatted columns
     *
     * @param cmds -  available operations
     * @param cmdCodes - triggering commands
     */
    public static void printCommands(String[] cmds, String[] cmdCodes) {
        System.out.println("You can do one of the following:");

        for (int i = 0; i+1 < cmds.length; i += 2) {
            String left = String.format("(%s) %s", cmdCodes[i], cmds[i]);
            String right = String.format("(%s) %s", cmdCodes[i+1], cmds[i+1]);
            System.out.printf("%-50s\t%s\n", left, right);
        }
        System.out.println();
        System.out.println();
    }

    /**
     * as name suggests
     */
    private static void printDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * print the title
     */
    public static void printTitle(Page page) {
        System.out.print("\n\n");
        printDivider();
        System.out.println(page.getTitle());
        printDivider();
        System.out.println();
    }

    public static enum AnsiColor {
        ANSI_RESET("\u001B[0m"),
        ANSI_BLACK("\u001B[30m"),
        ANSI_RED("\u001B[31m"),
        ANSI_GREEN("\u001B[32m"),
        ANSI_YELLOW("\u001B[33m"),
        ANSI_BLUE("\u001B[34m"),
        ANSI_PURPLE("\u001B[35m"),
        ANSI_CYAN("\u001B[36m"),
        ANSI_WHITE("\u001B[37m");

        private AnsiColor(String color) {
            value = color;
        }

        private final String value;

        public String getValue() {
            return value;
        }
    }

    public static void printColor(AnsiColor c, String text) {
        System.out.println(c.getValue() + text + ANSI_RESET);
    }

    public static void printfColor(AnsiColor c, String formatString, String ...formatArgs) {
        System.out.printf(c.getValue() + formatString + ANSI_RESET.getValue(), formatArgs);
    }

    public static void printflnColor(AnsiColor c, String formatString, String ...formatArgs) {
        System.out.printf(c.getValue() + formatString + ANSI_RESET.getValue() + "\n", formatArgs);
    }
}
