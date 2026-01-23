package simon;

import simon.command.Command;
import simon.model.TaskList;
import simon.storage.Storage;
import simon.ui.UI;
import simon.util.UIParser;

/**
 * Main entry point for the Simon chatbot application.
 */
public class Simon {

    private static final String NAME = "Simon";
    private final UI ui;
    private final TaskList tasks;
    private final UIParser parser;
    private final Storage storage;

    public Simon() {
        this.ui = new UI();
        this.storage = new Storage(System.getProperty("user.home") + "/.simon/data/simon.txt");
        this.tasks = new TaskList(storage);
        this.parser = new UIParser();
    }

    public void run() {
        ui.sayHi(NAME);

        boolean running = true;
        while (running) {
            String line = ui.readLine();
            if (line == null || line.isBlank()) continue;
            try {
                Command cmd = parser.parse(line);
                running = cmd.execute(tasks, ui);
            } catch (Exception e) {
                ui.printError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Simon().run();
    }
}