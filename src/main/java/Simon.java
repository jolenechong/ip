import tasks.Task;
import tasks.Todo;

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
        System.out.print("""
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
            System.out.println(i + 1 + "." + list.get(i));
        }
        System.out.println("____________________________________________________________\n");
    }

    private static void addToList(Task item) {
        Simon.list.add(item);
        System.out.printf("""
                ____________________________________________________________
                  Got it. I've added this task:
                    %s
                  Now you have %d tasks in the list.
                ____________________________________________________________
                %n""", item, Simon.list.size());
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
                   %s
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
                   %s
                ____________________________________________________________
                %n""", toMark);
    }

    public static void main(String[] args) {
        Simon.name = "Simon";
        Simon.list = new ArrayList<>();

        sayHi();
        Scanner scanner = new Scanner(System.in);
        String input;

        boolean running = true;

        while (running) {
            input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String cmd = parts[0].toLowerCase();

            switch (cmd) {
                case "bye":
                    sayBye();
                    running = false;
                    break;
                case "list":
                    listAll();
                    break;
                case "mark": {
                    if (parts.length > 1) {
                        markAsCompleted(Integer.parseInt(parts[1]));
                    }
                    break;
                }
                case "unmark": {
                    if (parts.length > 1) {
                        try {
                            markAsUnCompleted(Integer.parseInt(parts[1]));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    break;
                }
                case "todo":
                    if (parts.length > 1) {
                        addToList(new Todo(parts[1]));
                    }
                    break;
                case "deadline":
                    if (parts.length > 1) {
                        String[] deadlineParts = parts[1].split(" /by ", 2);
                        if (deadlineParts.length > 1) {
                            addToList(new tasks.Deadline(deadlineParts[0], deadlineParts[1]));
                        }
                    }
                    break;
                case "event":
                    if (parts.length > 1) {
                        String[] eventParts = parts[1].split(" /from | /to ", 3);
                        if (eventParts.length > 2) {
                            addToList(new tasks.Event(eventParts[0], eventParts[1], eventParts[2]));
                        }
                    }
                    break;
                default:
                    System.out.println("____________________________________________________________");
                    System.out.println("hUH what are you sAying");
                    System.out.println("____________________________________________________________\n");
            }
            // echo(input);
        }
    }
}