package simon;

import simon.command.Command;
import simon.model.TaskList;
import simon.storage.Storage;
import simon.ui.UI;
import simon.util.UiParser;

/**
 * Main class for the Simon application.
 * Handles initialization and the main application loop.
 * This class initializes the UI, storage, task list, and command parser.
 * It runs a loop to read user input, parse commands, and execute them.
 * @author Jolene Chong
 * @version v1.0
 *
 */
public class Simon {

    private static final String NAME = "Simon";
    private final UI ui;
    private final TaskList tasks;
    private final UiParser parser;
    private final Storage storage;

    /**
     * Constructs a Simon application instance.
     * Initializes the UI, storage, task list, and command parser.
     */
    public Simon() {
        this.ui = new UI();
        this.storage = new Storage(System.getProperty("user.home") + "/.simon/data/simon.txt");
        this.tasks = new TaskList(storage);
        this.parser = new UiParser();
    }

    /**
     * Runs the main application loop.
     * Reads user input, parses commands, and executes them until exit.
     */
    public void run() {
        ui.sayHi(NAME);

        boolean running = true;
        while (running) {

            String line = ui.readLine();
            if (line == null || line.isBlank()) {
                continue;
            }

            try {
                Command cmd = parser.parse(line);
                running = cmd.execute(tasks, ui);
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
