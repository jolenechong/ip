package simon;

import simon.command.Command;
import simon.model.TaskList;
import simon.storage.Storage;
import simon.ui.Ui;
import simon.util.UiParser;

/**
 * Main class for the Simon application.
 * Handles initialization and the main application loop.
 * This class initializes the UiParser, storage, task list, and command parser.
 * It runs a loop to read user input, parse commands, and execute them.
 * @author Jolene Chong
 * @version v1.1
 *
 */
public class Simon {

    private static final String NAME = "Simon";
    private final Ui ui;
    private final TaskList tasks;
    private final UiParser parser;
    private final Storage storage;

    /**
     * Constructs a Simon application instance.
     * Initializes the UiParser, storage, task list, and command parser.
     */
    public Simon() {
        this.ui = new Ui();
        this.storage = new Storage(System.getProperty("user.home") + "/.simon/data/simon.txt");
        this.tasks = new TaskList(storage);
        this.parser = new UiParser();
    }

    /**
     * Gets responses for GUI integration.
     *
     * @param input The user input command.
     * @return The response from executing the command.
     */
    public Response getResponse(String input) {
        if (input == null || input.isBlank()) {
            return new Response("", false);
        }

        try {
            Command cmd = parser.parse(input);
            StringBuilder output = new StringBuilder();
            final boolean[] exitRequested = {false};

            Ui tempUi = new Ui() {
                @Override
                public void printAll(String message, Object... args) {
                    output.append(String.format(message, args)).append("\n");
                }

                @Override
                public void printError(String message) {
                    output.append("Error: ").append(message).append("\n");
                }

                @Override
                public void sayBye() {
                    output.append("Bye. Hope to see you again soon!\n");
                    exitRequested[0] = true;
                }
            };
            cmd.execute(tasks, tempUi);

            String response = output.toString().trim();
            return new Response(response, exitRequested[0]);
        } catch (Exception e) {
            return new Response("Error: " + e.getMessage(), false);
        }
    }

    /**
     * Runs the main application loop.
     * Reads user input, parses commands, and executes them until exit.
     */
    public void run() {
        assert ui != null : "UI should be initialized";
        assert tasks != null : "TaskList should be initialized";
        assert parser != null : "UiParser should be initialized";

        ui.sayHi(NAME);

        boolean isRunning = true;
        while (isRunning) {

            String line = ui.readLine();
            if (line == null || line.isBlank()) {
                continue;
            }

            try {
                Command cmd = parser.parse(line);
                isRunning = cmd.execute(tasks, ui);
            } catch (Exception e) {
                ui.printError(e.getMessage());
            }
        }
    }

    /**
     * Main method to start the Simon application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Simon().run();
    }
}
