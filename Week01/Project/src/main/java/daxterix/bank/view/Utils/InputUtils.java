package daxterix.bank.view.Utils;

import daxterix.bank.view.Page;

import java.io.Console;
import java.util.Scanner;

public class InputUtils {
    public static String DIVIDER = "______________________________________________________________________";

    public static void printPrompt(String fieldName) {
        System.out.printf("enter %s: ", fieldName);
    }


    public static String readLine(String filedName) {
        printPrompt(filedName);
        Scanner in = new Scanner(System.in);
        return in.nextLine().trim();
    }

    public static int getInt(String fieldName) throws InvalidInputException {
        try {
            return Integer.parseInt(readLine(fieldName));
        }
        catch(NumberFormatException e) {
            throw(new InvalidInputException(String.format("invalid %s provided", fieldName)));
        }
    }

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
     * Note: does not work inside
     *
     * @param fieldName
     * @return
     */
    public static String readMasked(String fieldName) {
        printPrompt(fieldName);
        Console console = System.console();
        char[] masked = console.readPassword();
        return new String(masked);
    }

    public static void printDivider() {
        System.out.println(DIVIDER );
    }

    public static void printPageTitle(Page p) {
        printDivider();
        System.out.println(p.getTitle());
        printDivider();
    }
}
