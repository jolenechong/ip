package simon;

import static simon.command.AiCommand.AI_COMMAND_CANCELLED_MESSAGE;
import static simon.command.AiCommand.AI_COMMAND_PREFIX;
import static simon.command.AiCommand.CONFIRMATION_MESSAGE_REGEX;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import simon.command.Command;
import simon.command.CommandInvoker;
import simon.model.TaskList;
import simon.storage.Storage;
import simon.ui.Ui;
import simon.util.UiParser;

/**
 * Main class for the Simon application.
 * Handles initialization and the main application loop.
 * This class initializes the UiParser, storage, task list, and command parser.
 * It runs a loop to read user input, parse commands, and execute them.
 *
 * @author Jolene Chong
 * @version v1.1
 *
 */
public class Simon {

    private static final String DATA_FILE_PATH =
            System.getProperty("user.home") + "/.simon/data/simon.txt";
    private static final String BYE_MESSAGE = "Bye. Hope to see you again soon!";
    private static final String YES_RESPONSE_REGEX = "(?i)^(y|yes)$";
    private static final String LLM_API_KEY_ENV = "LLM_API_KEY";
    private static final String BASE_URL = "https://api.groq.com/openai/v1";
    private static final String MODEL_NAME = "llama-3.3-70b-versatile";

    private static final String NAME = "Simon";
    private final Ui ui;
    private final TaskList tasks;
    private final UiParser parser;
    private final Storage storage;
    private final CommandInvoker commandInvoker;
    private String pendingAiCommand = null;

    /**
     * Constructs a Simon application instance.
     * Initializes the UiParser, storage, task list, and command parser.
     */
    public Simon() {
        this.ui = new Ui();
        this.storage = new Storage(DATA_FILE_PATH);
        this.tasks = new TaskList(storage);

        if (System.getenv(LLM_API_KEY_ENV) != null) {
            ChatModel model = OpenAiChatModel.builder()
                    .apiKey(System.getenv(LLM_API_KEY_ENV))
                    .baseUrl(BASE_URL)
                    .modelName(MODEL_NAME)
                    .build();
            this.parser = new UiParser(model);
        } else {
            this.parser = new UiParser();
        }

        this.commandInvoker = new CommandInvoker();
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
            if (pendingAiCommand != null) {
                return handleAiConfirmation(input);
            }

            Command cmd = parser.parse(input, commandInvoker);
            StringBuilder output = new StringBuilder();
            ExitStatus exitStatus = new ExitStatus();
            Ui tempUi = initUi(output, exitStatus);

            commandInvoker.execute(cmd, tasks, tempUi);
            String response = output.toString().trim();

            return handleAiSuggestion(response, exitStatus);

        } catch (Exception e) {
            return new Response("Error: " + e.getMessage(), false);
        }
    }

    private Response handleAiConfirmation(String input) {
        boolean confirmed = input.matches(YES_RESPONSE_REGEX);
        String executedCommand = pendingAiCommand;
        pendingAiCommand = null;

        if (confirmed) {
            String aiCommand = extractAiCommand(executedCommand);
            return getResponse(aiCommand);
        } else {
            return new Response(AI_COMMAND_CANCELLED_MESSAGE, false);
        }
    }

    private String extractAiCommand(String executedCommand) {
        Pattern pattern = Pattern.compile(CONFIRMATION_MESSAGE_REGEX);
        Matcher matcher = pattern.matcher(executedCommand);
        return matcher.find() ? matcher.group(1) : null;
    }

    private Response handleAiSuggestion(String response, ExitStatus exitStatus) {
        if (response.startsWith(AI_COMMAND_PREFIX)) {
            pendingAiCommand = response.substring(9);
            return new Response(pendingAiCommand, false);
        }
        return new Response(response, exitStatus.isExitRequested());
    }


    private Ui initUi(StringBuilder output, ExitStatus exitStatus) {
        return new Ui() {
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
                output.append(BYE_MESSAGE);
                exitStatus.requestExit();
            }
        };
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
                Command cmd = parser.parse(line, commandInvoker);
                isRunning = cmd.execute(tasks, ui);
            } catch (Exception e) {
                ui.printError(e.getMessage());
            }
        }
    }

    private static class ExitStatus {
        private boolean isExitRequested;

        public boolean isExitRequested() {
            return isExitRequested;
        }

        public void requestExit() {
            this.isExitRequested = true;
        }
    }

    /**
     * Runs the Simon application. This is the entry point of the CLI program.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Simon().run();
    }
}
