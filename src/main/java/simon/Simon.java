package simon;

import static simon.util.IntParser.parseIndex;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import simon.exception.InputErrorType;
import simon.exception.InputFormatException;
import simon.storage.Storage;
import simon.task.Task;
import simon.task.Todo;
import simon.task.Event;
import simon.task.Deadline;
import simon.util.DateParser;

/**
 * Main entry point for the Simon chatbot application.
 */
public class Simon {

    private static final String name = "Simon";
    private static ArrayList<Task> tasks;
    private static Storage storage;

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
        for (int i = 0; i < Simon.tasks.size(); i++) {
            System.out.println(i + 1 + "." + Simon.tasks.get(i));
        }
        System.out.println("____________________________________________________________\n");
    }

    private static void addToList(Task item) {
        Simon.tasks.add(item);
        System.out.printf("""
                ____________________________________________________________
                  Got it. I've added this task:
                    %s
                  Now you have %d tasks in the list.
                ____________________________________________________________
                %n""", item, Simon.tasks.size());
        persist();
    }

    private static void markAsCompleted(int num) {
        if (num == 0 || num > tasks.size()) {
            return;
        }
        Task toMark = Simon.tasks.get(num - 1);
        toMark.setCompleted(true);
        System.out.printf("""
                ____________________________________________________________
                 Nice! I've marked this task as done:
                   %s
                ____________________________________________________________
                %n""", toMark);
        persist();
    }

    private static void markAsUnCompleted(int num) {
        if (num == 0 || num > Simon.tasks.size()) {
            return;
        }
        Task toMark = Simon.tasks.get(num - 1);
        toMark.setCompleted(false);
        System.out.printf("""
                ____________________________________________________________
                 OK, I've marked this task as not done yet:
                   %s
                ____________________________________________________________
                %n""", toMark);
        persist();
    }

    private static void deleteFromList(int num) {
        if (num == 0 || num > Simon.tasks.size()) {
            return;
        }
        Task toDelete = Simon.tasks.remove(num - 1);
        System.out.printf("""
                ____________________________________________________________
                 Noted. I've removed this task:
                   %s
                 Now you have %d tasks in the list.
                ____________________________________________________________
                %n""", toDelete, Simon.tasks.size());
        persist();
    }

    private static void findDateOn(LocalDateTime date) {
        System.out.print("____________________________________________________________\n");
        System.out.println("Here are the tasks on " + date + ":");
        for (int i = 0; i < Simon.tasks.size(); i++) {
            Task task = Simon.tasks.get(i);
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getBy().toLocalDate().equals(date.toLocalDate())) {
                    System.out.println(i + 1 + "." + deadline);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.getFrom().toLocalDate().equals(date.toLocalDate())
                        || event.getTo().toLocalDate().equals(date.toLocalDate())) {
                    System.out.println(i + 1 + "." + event);
                }
            }
        }
        System.out.println("____________________________________________________________\n");
    }

    private static void persist() {
        try {
            storage.saveTasks(Simon.tasks);
        } catch (Exception e) {
            System.err.println("Unexpected error while saving tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Simon.tasks = new ArrayList<>();
        storage = new Storage("./data/simon.txt");
        tasks = storage.loadTasks();

        sayHi();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running && scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
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
                        if (parts.length <= 1) {
                            throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
                        }
                        markAsCompleted(parseIndex(parts[1]));
                        break;

                    case "unmark":
                        if (parts.length <= 1) {
                            throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
                        }
                        markAsUnCompleted(parseIndex(parts[1]));
                        break;
                    case "todo":
                        if (parts.length <= 1) {
                            throw new InputFormatException(InputErrorType.TODO_EMPTY);
                        }
                        addToList(new Todo(parts[1]));
                        break;
                    case "deadline":
                        if (parts.length <= 1) {
                            throw new InputFormatException(InputErrorType.DEADLINE_FORMAT);
                        }

                        String rest = parts[1];
                        int byIndex = rest.indexOf(" /by ");
                        if (byIndex == -1) {
                            throw new InputFormatException(InputErrorType.DEADLINE_FORMAT);
                        }
                        String desc = rest.substring(0, byIndex).trim();
                        String by = rest.substring(byIndex + 5).trim();
                        if (desc.isEmpty() || by.isEmpty()) {
                            throw new InputFormatException(InputErrorType.DEADLINE_FORMAT);
                        }

                        addToList(new Deadline(desc, by));
                        break;
                    case "event":
                        if (parts.length <= 1) {
                            throw new InputFormatException(InputErrorType.EVENT_FORMAT);
                        }

                        rest = parts[1];
                        int fromIndex = rest.indexOf(" /from ");
                        int toIndex = rest.indexOf(" /to ");
                        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                            throw new InputFormatException(InputErrorType.EVENT_FORMAT);
                        }
                        desc = rest.substring(0, fromIndex).trim();
                        String from = rest.substring(fromIndex + 7, toIndex).trim();
                        String to = rest.substring(toIndex + 5).trim();
                        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                            throw new InputFormatException(InputErrorType.EVENT_FORMAT);
                        }

                        addToList(new Event(desc, from, to));
                        break;
                    case "on":
                        if (parts.length <= 1) {
                            throw new InputFormatException(InputErrorType.EVENT_FORMAT);
                        }
                        String date = parts[1].trim();
                        findDateOn(DateParser.parse(date));
                        break;
                    case "delete":
                        if (parts.length <= 1) {
                            throw new InputFormatException(InputErrorType.NUMBER_RANGE);
                        }
                        deleteFromList(parseIndex(parts[1]));
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
}