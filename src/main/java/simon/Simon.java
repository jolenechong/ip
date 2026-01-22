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

    public static void main(String[] args) {
        UI ui = new UI();
        Storage storage = new Storage(System.getProperty("user.home") + "/.simon/data/simon.txt");
        TaskList tasks = new TaskList(storage);
        UIParser parser = new UIParser();

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
}