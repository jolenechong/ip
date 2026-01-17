import java.util.ArrayList;
import java.util.Scanner;

public class Simon {

    private static String name;
    private static ArrayList<String> list;

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

    private static void listAll() {
        System.out.print("____________________________________________________________\n");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i));
        }
        System.out.println("____________________________________________________________\n");
    }

    private static void addToList(String item) {
        Simon.list.add(item);
        System.out.printf("""
                ____________________________________________________________
                 added: %s
                ____________________________________________________________
                %n""", item);
    }

    public static void main(String[] args) {
        Simon.name = "Simon";
        Simon.list = new ArrayList<>();

        sayHi();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                sayBye();
                break;
            } else if (input.equalsIgnoreCase("list")) {
                listAll();
            } else {
                addToList(input);
            }
            // echo(input);

        }
    }
}