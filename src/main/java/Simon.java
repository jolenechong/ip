import java.util.Scanner;

public class Simon {

    private static String name;

    private static void sayHi() {
        System.out.println();
        System.out.printf("""
                ____________________________________________________________
                 Hello! I'm %s!
                 What can I do for you?
                ____________________________________________________________
                %n""", name);
    }

    private static void sayBye() {
        System.out.printf("""
                ____________________________________________________________ 
                Bye. Hope to see you again soon!
                ____________________________________________________________ 
                """);
    }

    private static void echo(String input) {
        System.out.printf("""
                ____________________________________________________________
                 %s
                ____________________________________________________________
                %n""", input);
    }

    public static void main(String[] args) {
        name = "Simon";
        sayHi();
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                sayBye();
                break;
            }
            echo(input);

        }
    }
}