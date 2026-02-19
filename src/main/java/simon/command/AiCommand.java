package simon.command;

import java.util.List;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents a command that interacts with an AI model to generate responses based on user input.
 */
public class AiCommand implements Command {
    public static final String CONFIRMATION_MESSAGE_REGEX = "Generated: (.+?)\\s+Do you want to execute "
            + "this command\\? \\(Y/n\\):";
    public static final String AI_COMMAND_CANCELLED_MESSAGE = "Command cancelled.";
    public static final String AI_COMMAND_PREFIX = "[AI_CMD] ";

    private static final String SYSTEM_PROMPT = "You are an assistant for a GUI task management "
            + "app. Based on the user's "
            + "request, generate a valid command that the app understands."
            + "Only respond with the command, nothing else.\n\n"
            + "Use the following date and time formats:\n"
            + "- Date & time (when time is required):\n"
            + "  ISO local date-time e.g. 2025-08-31T18:00\n"
            + "  Day/Month/Year with 24-hour time e.g. 31/8/2025 1800\n"
            + "  Day-Month-Year with 24-hour time e.g. 31-8-2025 1800\n"
            + "  Year/Month/Day with 24-hour time e.g. 2025/08/31 1800\n"
            + "- Date only (time defaults to start of day):\n"
            + "  ISO local date e.g. 2025-08-31\n"
            + "  Day/Month/Year e.g. 31/8/2025\n"
            + "  Day-Month-Year e.g. 31-8-2025\n"
            + "  Year/Month/Day e.g. 2025/08/31\n\n"
            + "1. todo <description> - Adds a Todo task.\n"
            + "2. deadline <description> /by <date/time> - Adds a Deadline task.\n"
            + "3. event <description> /from <start date/time> /to <end date/time> - Adds an Event task.\n"
            + "4. list - Displays all tasks in the list.\n"
            + "5. mark <index> - Marks the task at the specified index as done.\n"
            + "6. unmark <index> - Marks the task at the specified index as not done.\n"
            + "7. mark 1,3 or mark 2-4 - Marks multiple tasks as done.\n"
            + "8. unmark 1,3 or unmark 2-4 - Marks multiple tasks as not done.\n"
            + "9. delete <index> - Deletes the task at the specified index.\n"
            + "10. delete 1,3 or delete 2-4 - Deletes multiple tasks.\n"
            + "11. find <keyword> - Finds tasks containing the specified keyword.\n"
            + "12. on <date> - Lists tasks occurring on the specified date.\n"
            + "13. undo - Reverts the last executed command.\n"
            + "14. bye - Closes the application.\n"
            + "15. <command1> && <command2> - Executes multiple commands in sequence.";
    private static final String DID_NOT_UNDERSTAND_RESPONSE = "Sorry, I couldn't understand your request. "
            + "Please try again.";
    private static final String CONFIRMATION_MESSAGE_TEMPLATE = "[AI_CMD] Generated: %s \n"
            + "Do you want to execute this command? (Y/n): ";

    private final String userInput;
    private final ChatModel model;

    /**
     * Initializes the AI command with the necessary model configuration.
     */
    public AiCommand(String userInput, ChatModel model) {
        this.userInput = userInput;
        this.model = model;
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        String commandToRun = generateAiResponse(SYSTEM_PROMPT, userInput);
        if (commandToRun.equals(DID_NOT_UNDERSTAND_RESPONSE)) {
            ui.printAll(DID_NOT_UNDERSTAND_RESPONSE);
            return false;
        }

        ui.printAll(CONFIRMATION_MESSAGE_TEMPLATE, commandToRun);

        return true;
    }

    /**
     * Generates a response from the AI model based on the provided system and user prompts.
     *
     * @param systemPrompt The prompt that sets the context for the AI's response.
     * @param userPrompt   The user's input that the AI will respond to.
     * @return The AI-generated response as a string.
     */
    public String generateAiResponse(String systemPrompt, String userPrompt) {
        ChatRequest req = ChatRequest.builder()
                .messages(List.of(
                        SystemMessage.from(systemPrompt),
                        UserMessage.from(userPrompt)
                ))
                .build();

        ChatResponse res = model.chat(req);
        String command = res.aiMessage().text();

        if (!isValidCommand(command)) {
            return DID_NOT_UNDERSTAND_RESPONSE;
        }

        return command;
    }

    private boolean isValidCommand(String command) {
        String[] validCommands = {"todo", "deadline", "event", "list", "mark", "unmark",
            "delete", "find", "on", "undo", "bye"};
        for (String validCommand : validCommands) {
            if (command.startsWith(validCommand)) {
                return true;
            }
        }
        return false;
    }


}
