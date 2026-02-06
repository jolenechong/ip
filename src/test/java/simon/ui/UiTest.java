package simon.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the UiParser class.
 */
public class UiTest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    /**
     * Restores original System.out and System.in after each test.
     */
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /**
     * Tests that sayHi prints the correct greeting message with the provided name.
     */
    @Test
    void sayHi_printsGreetingWithName() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        System.setIn(new ByteArrayInputStream(new byte[0])); // avoid blocking scanner
        Ui ui = new Ui();
        ui.sayHi("Tester");

        String printed = out.toString();
        assertTrue(printed.contains("Hello! I'm Tester!"), "sayHi should include the provided name");
        assertTrue(printed.contains("What can I do for you?"), "sayHi should include the prompt");
    }

    /**
     * Tests that sayBye prints the correct farewell message.
     */
    @Test
    void sayBye_printsFarewell() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Ui ui = new Ui();
        ui.sayBye();

        String printed = out.toString();
        assertTrue(printed.contains("Bye. Hope to see you again soon!"), "sayBye should print farewell message");
    }

    /**
     * Tests that println, printAll, and printError work as expected.
     */
    @Test
    void printlnAllError_workAsExpected() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Ui ui = new Ui();
        ui.println("simple line");
        ui.printAll("formatted %s", "text");
        ui.printError("error occurred");

        String printed = out.toString();
        assertTrue(printed.contains("simple line"), "println should print the message");
        assertTrue(printed.contains("formatted text"), "printAll should format and print the message");
        assertTrue(printed.contains("error occurred"), "printError should print the error message");
        assertTrue(printed.contains("____________________________________________________________"),
                "printAll/printError should include separators");
    }

    /**
     * Tests that readLine correctly reads and trims input.
     */
    @Test
    void readLine_trimsAndReturnsInput() {
        String input = "   hello world   \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Ui ui = new Ui();
        String line = ui.readLine();

        assertEquals("hello world", line, "readLine should trim surrounding whitespace");
    }

}
