package simon.ui;

import java.util.Scanner;

/**
 * UI class handles all interactions with the user via the console.
 */
public class UI {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Greets the user with a welcome message.
     * @param name The name of the application.
     */
    public void sayHi(String name) {
        System.out.println();
        System.out.printf("""
                ____________________________________________________________
                 Hello! I'm %s!
                 What can I do for you?
                ____________________________________________________________
                %n""", name);
    }

    /**
     * Bids farewell to the user.
     */
    public void sayBye() {
        System.out.print("""
                ____________________________________________________________ 
                Bye. Hope to see you again soon!
                ____________________________________________________________ 
                """);
    }

    /**
     * Prints a message to the console.
     * @param msg The message to be printed.
     */
    public void println(String msg) {
        System.out.println(msg);
    }

    /**
     * Prints a formatted message to the console, enclosed in lines for emphasis.
     * @param msg The message format string.
     * @param args The arguments to be formatted into the message.
     */
    public void printAll(String msg, Object... args) {
        System.out.println("____________________________________________________________");
        System.out.printf(msg, args);
        System.out.println("\n____________________________________________________________\n");
    }

    /**
     * Prints an error message to the console, enclosed in lines for emphasis.
     * @param msg The error message to be printed.
     */
    public void printError(String msg) {
        System.out.println("____________________________________________________________");
        System.out.printf(msg);
        System.out.println("\n____________________________________________________________\n");
    }

    /**
     * Reads a line of input from the user.
     * @return The trimmed line of input.
     */
    public String readLine() {
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    // not in use
    private static void echo(String input) {
        System.out.printf("""
                ____________________________________________________________
                 %s
                ____________________________________________________________
                %n""", input);


    }

}
