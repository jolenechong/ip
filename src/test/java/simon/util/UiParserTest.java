package simon.util;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import simon.command.AddCommand;
import simon.command.ByeCommand;
import simon.command.Command;
import simon.command.CommandInvoker;
import simon.command.CompositeCommand;
import simon.command.DeleteCommand;
import simon.command.FindCommand;
import simon.command.ListCommand;
import simon.command.MarkCommand;
import simon.command.MultiDelete;
import simon.command.MultiMark;
import simon.command.OnCommand;
import simon.command.UndoCommand;
import simon.exception.InputFormatException;

/**
 * Tests for {@link UiParser}.
 */
class UiParserTest {

    private UiParser parser;
    private CommandInvoker invoker;

    @BeforeEach
    void setUp() {
        parser = new UiParser();
        invoker = new CommandInvoker();
    }

    // =========================
    // Valid command tests
    // =========================

    @Test
    void parse_todo_success() throws Exception {
        Command cmd = parser.parse("todo Read book", invoker);
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void parse_deadline_success() throws Exception {
        Command cmd = parser.parse(
                "deadline Submit report /by 31/8/2025 1800",
                invoker
        );
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void parse_event_success() throws Exception {
        Command cmd = parser.parse(
                "event Meeting /from 31/8/2025 1000 /to 31/8/2025 1200",
                invoker
        );
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void parse_list_success() throws Exception {
        Command cmd = parser.parse("list", invoker);
        assertInstanceOf(ListCommand.class, cmd);
    }

    @Test
    void parse_find_success() throws Exception {
        Command cmd = parser.parse("find book", invoker);
        assertInstanceOf(FindCommand.class, cmd);
    }

    @Test
    void parse_markSingleIndex_success() throws Exception {
        Command cmd = parser.parse("mark 2", invoker);
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    void parse_unmarkSingleIndex_success() throws Exception {
        Command cmd = parser.parse("unmark 3", invoker);
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    void parse_markMultipleIndexes_success() throws Exception {
        Command cmd = parser.parse("mark 1,3-5", invoker);
        assertInstanceOf(MultiMark.class, cmd);
    }

    @Test
    void parse_deleteSingleIndex_success() throws Exception {
        Command cmd = parser.parse("delete 4", invoker);
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    @Test
    void parse_deleteMultipleIndexes_success() throws Exception {
        Command cmd = parser.parse("delete 2-4", invoker);
        assertInstanceOf(MultiDelete.class, cmd);
    }

    @Test
    void parse_on_success() throws Exception {
        Command cmd = parser.parse("on 1/1/2026", invoker);
        assertInstanceOf(OnCommand.class, cmd);
    }

    @Test
    void parse_undo_success() throws Exception {
        Command cmd = parser.parse("undo", invoker);
        assertInstanceOf(UndoCommand.class, cmd);
    }

    @Test
    void parse_bye_success() throws Exception {
        Command cmd = parser.parse("bye", invoker);
        assertInstanceOf(ByeCommand.class, cmd);
    }

    @Test
    void parse_chainedCommands_success() throws Exception {
        Command cmd = parser.parse(
                "todo Read book && list && bye",
                invoker
        );
        assertInstanceOf(CompositeCommand.class, cmd);
    }

    // =========================
    // Invalid command tests
    // =========================

    @Test
    void parse_unknownCommand_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse("meow", invoker)
        );
    }

    @Test
    void parse_todoMissingDescription_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse("todo", invoker)
        );
    }

    @Test
    void parse_deadlineMissingBy_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse("deadline Submit report", invoker)
        );
    }

    @Test
    void parse_deadlineEmptyBy_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse("deadline Submit report /by ", invoker)
        );
    }

    @Test
    void parse_eventMissingFrom_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse(
                        "event Meeting /to 31/8/2025 1200",
                        invoker
                )
        );
    }

    @Test
    void parse_eventMissingTo_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse(
                        "event Meeting /from 31/8/2025 1000",
                        invoker
                )
        );
    }

    @Test
    void parse_eventInvalidOrder_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse(
                        "event Meeting /to 31/8/2025 1200 /from 31/8/2025 1000",
                        invoker
                )
        );
    }

    @Test
    void parse_markInvalidRange_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse("mark 1--3", invoker)
        );
    }

    @Test
    void parse_deleteInvalidRange_throwsException() {
        assertThrows(InputFormatException.class, () ->
                parser.parse("delete 2-", invoker)
        );
    }
}
