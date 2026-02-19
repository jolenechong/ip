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
public class AiQueryCommand implements Command {
    private static final String SYSTEM_PROMPT = "You are helping users of a GUI text-based app. Answer the user's query about the features of the app, based on the app's commands given below. Limit your answer to one sentence.\n\n"
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

    private final String userInput;
    private final ChatModel model;

    /**
     * Initializes the AI command with the necessary model configuration.
     */
    public AiQueryCommand(String userInput, ChatModel model) {
        this.userInput = userInput;
        this.model = model;
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        String aiResponse = generateAiResponse(SYSTEM_PROMPT, userInput);
        ui.printAll(aiResponse);
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
        return res.aiMessage().text();
    }


}
