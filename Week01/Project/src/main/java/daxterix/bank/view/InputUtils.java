package daxterix.bank.view;

import java.io.Console;
import java.util.Scanner;
import java.util.function.Supplier;

import daxterix.bank.view.OutputUtils;
import daxterix.bank.view.OutputUtils.AnsiColor;

public class InputUtils {
    /**
     * as title suggests
     *
     * @param fieldName
     */
    public static void printPrompt(String fieldName) {
        OutputUtils.printfColor(AnsiColor.ANSI_YELLOW, "enter %s: ", fieldName);
    }

    /**
     * read a line of input from user
     *
     * @param filedName
     * @return
     */
    public static String readLine(String filedName) {
        printPrompt(filedName);
        Scanner in = new Scanner(System.in);
        return in.nextLine().trim();
    }

    /**
     * read a line of input from user
     *
     * @param filedName
     * @return
     */
    public static String readNonEmptyLine(String filedName) {
        String line = "";
        while(line.isEmpty()) {
            printPrompt(filedName);
            Scanner in = new Scanner(System.in);
            line = in.nextLine().trim();
            if (line.isEmpty())
                System.out.println("input cannot be empty");
        }
        return line;
    }

    /**
     * read an integer from user
     *
     * @param fieldName
     * @return
     * @throws InvalidInputException
     */
    public static int getInt(String fieldName) throws InvalidInputException {
        try {
            return Integer.parseInt(readLine(fieldName));
        }
        catch(NumberFormatException e) {
            throw(new InvalidInputException(String.format("invalid %s provided", fieldName)));
        }
    }

    /**
     * get user input until user inputs a valid integer
     *
     * @param fieldName
     * @return
     */
    public static int readValidInt(String fieldName) {
        while(true) {
            try {
                return getInt(fieldName);
            }
            catch (InvalidInputException e){
                System.out.println(String.format("invalid %s provided, please try again", fieldName));
            }
        }
    }

    /**
     * Hides input as user enters it. useful to inputting passwords
     * Note: does not work in IDE consoles
     *
     * @param fieldName
     * @return
     */
    public static String readMasked(String fieldName) {
        Console console = System.console();
        if (console == null)
            return readLine(fieldName);

        printPrompt(fieldName);
        char[] masked = console.readPassword();
        return new String(masked);
    }

    /**
     * gets user input and confirms it. i.e. reads it twice and verifies
     * that the input is the same both times
     *
     * @return
     */
    public static <T> T readAndConfirm(Supplier<T> inputSupply) {
        T firstEntry;
        T secondEntry;

        do {
            firstEntry = inputSupply.get();
            System.out.println("Confirm");
            secondEntry = inputSupply.get();
            if (!secondEntry.equals(firstEntry))
                System.out.println("both entries must be the same; please try again");
        }
        while(!firstEntry.equals(secondEntry));
        return firstEntry;
    }


    /**
     * obtain user input on whether to quit
     *
     * @return - true if user indicates intent to quit; false otherwise
     */
    public static boolean checkQuit() {
       String cmd = readLine("'quit' to quit or anything else to try-again");
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
     * obtain user input on whether to quit
     *
     * @return - true if user indicates intent to quit; false otherwise
     */
    public static boolean confirmDecision() {
       System.out.print("Are you sure ('YES' to proceed, anything else to abort)?: ");
       Scanner in = new Scanner(System.in);
       String decision = in.nextLine().trim();
       return (decision.equals("YES"));
    }

}
