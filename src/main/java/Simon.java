import exceptions.InputFormatException;
import tasks.Task;
import tasks.Todo;

import java.util.ArrayList;
import java.util.Scanner;

import static utils.Parser.parseIndex;

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

    public static void main(String[] args) throws InputFormatException {
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

            try {
                switch (cmd) {
                    case "bye":
                        sayBye();
                        running = false;
                        break;
                    case "list":
                        listAll();
                        break;
                    case "mark":
                        if (parts.length <= 1) throw InputFormatException.numberFormatError();
                        markAsCompleted(parseIndex(parts[1]));
                        break;

                    case "unmark":
                        if (parts.length <= 1) throw InputFormatException.numberFormatError();
                        markAsUnCompleted(parseIndex(parts[1]));
                        break;
                    case "todo":
                        if (parts.length <= 1) throw InputFormatException.todoDescriptionEmpty();
                        addToList(new Todo(parts[1]));
                        break;
                    case "deadline":
                        if (parts.length <= 1) throw InputFormatException.deadlineFormatError();

                        String rest = parts[1];
                        int byIndex = rest.indexOf(" /by ");
                        if (byIndex == -1) throw InputFormatException.deadlineFormatError();
                        String desc = rest.substring(0, byIndex).trim();
                        String by = rest.substring(byIndex + 5).trim();
                        if (desc.isEmpty() || by.isEmpty()) throw InputFormatException.deadlineFormatError();

                        addToList(new tasks.Deadline(desc, by));
                        break;
                    case "event":
                        if (parts.length <= 1) throw InputFormatException.eventFormatError();

                        rest = parts[1];
                        int fromIndex = rest.indexOf(" /from ");
                        int toIndex = rest.indexOf(" /to ");
                        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                            throw InputFormatException.eventFormatError();
                        }
                        desc = rest.substring(0, fromIndex).trim();
                        String from = rest.substring(fromIndex + 7, toIndex).trim();
                        String to = rest.substring(toIndex + 5).trim();
                        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                            throw InputFormatException.eventFormatError();
                        }

                        addToList(new tasks.Event(desc, from, to));
                        break;
                    default:
                        System.out.println("____________________________________________________________");
                        System.out.println("hUH what are you sAying");
                        System.out.println("____________________________________________________________\n");
                }
                // echo(input);
            } catch (InputFormatException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________\n");
            }
        }
    }
}