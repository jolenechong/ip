package simon.ui;

import java.util.Scanner;

public class UI {

    private final Scanner scanner = new Scanner(System.in);

    public void sayHi(String name) {
        System.out.println();
        System.out.printf("""
                ____________________________________________________________
                 Hello! I'm %s!
                 What can I do for you?
                ____________________________________________________________
                %n""", name);
    }

    public void sayBye() {
        System.out.print("""
                ____________________________________________________________ 
                Bye. Hope to see you again soon!
                ____________________________________________________________ 
                """);
    }

    public void println(String msg) {
        System.out.println(msg);
    }

    public void printAll(String msg, Object... args) {
        System.out.println("____________________________________________________________");
        System.out.printf(msg, args);
        System.out.println("\n____________________________________________________________\n");
    }

    public void printError(String msg) {
        System.out.println("____________________________________________________________");
        System.out.printf(msg);
        System.out.println("\n____________________________________________________________\n");
    }

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
