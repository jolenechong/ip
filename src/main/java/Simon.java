import java.util.ArrayList;
import java.util.Scanner;

public class Simon {

    private static String name;
    private static ArrayList<Task> list;

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
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            String completed = " ";
            if (list.get(i).isCompleted()) completed = "X";
            System.out.println(i + 1 + ".[" + completed + "] " + list.get(i));
        }
        System.out.println("____________________________________________________________\n");
    }

    private static void addToList(Task item) {
        Simon.list.add(item);
        System.out.printf("""
                ____________________________________________________________
                 added: %s
                ____________________________________________________________
                %n""", item);
    }

    public static void markAsCompleted(int num) {
        if (num == 0 || num > list.size()) {
            return;
        }
        Task toMark = Simon.list.get(num - 1);
        toMark.setCompleted(true);
        System.out.printf("""
                ____________________________________________________________
                 Nice! I've marked this task as done:
                   [X] %s
                ____________________________________________________________
                %n""", toMark);
    }

    public static void markAsUnCompleted(int num) {
        if (num == 0 || num > list.size()) {
            return;
        }
        Task toMark = Simon.list.get(num - 1);
        toMark.setCompleted(false);
        System.out.printf("""
                ____________________________________________________________
                 OK, I've marked this task as not done yet:
                   [ ] %s
                ____________________________________________________________
                %n""", toMark);
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
            } else if (input.startsWith("mark")) {
                int num = Integer.parseInt(input.split(" ")[1]);
                markAsCompleted(num);
            } else if (input.startsWith("unmark")) {
                int num = Integer.parseInt(input.split(" ")[1]);
                markAsUnCompleted(num);
            } else {
                addToList(new Task(input));
            }
            // echo(input);
        }
    }
}