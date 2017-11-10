package daxterix.bank.view.Utils;

import daxterix.bank.view.Page;

import java.io.Console;
import java.util.Scanner;
import java.util.function.Supplier;

public class InputUtils {
    private static String DIVIDER = "______________________________________________________________________";

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


    /**
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

    private static void printDivider() {
        System.out.println(DIVIDER );
    }

    public static void printPageTitle(Page p) {
        printDivider();
        System.out.println(p.getTitle());
        printDivider();
        System.out.println();
    }
}
