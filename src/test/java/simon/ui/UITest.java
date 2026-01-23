package simon.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UITest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void sayHi_printsGreetingWithName() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        System.setIn(new ByteArrayInputStream(new byte[0])); // avoid blocking scanner
        UI ui = new UI();
        ui.sayHi("Tester");

        String printed = out.toString();
        assertTrue(printed.contains("Hello! I'm Tester!"), "sayHi should include the provided name");
        assertTrue(printed.contains("What can I do for you?"), "sayHi should include the prompt");
    }

    @Test
    void sayBye_printsFarewell() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        UI ui = new UI();
        ui.sayBye();

        String printed = out.toString();
        assertTrue(printed.contains("Bye. Hope to see you again soon!"), "sayBye should print farewell message");
    }

    @Test
    void println_and_printAll_and_printError_workAsExpected() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        UI ui = new UI();
        ui.println("simple line");
        ui.printAll("formatted %s", "text");
        ui.printError("error occurred");

        String printed = out.toString();
        assertTrue(printed.contains("simple line"), "println should print the message");
        assertTrue(printed.contains("formatted text"), "printAll should format and print the message");
        assertTrue(printed.contains("error occurred"), "printError should print the error message");
        assertTrue(printed.contains("____________________________________________________________"), "printAll/printError should include separators");
    }

    @Test
    void readLine_trimsAndReturnsInput() {
        String input = "   hello world   \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UI ui = new UI();
        String line = ui.readLine();

        assertEquals("hello world", line, "readLine should trim surrounding whitespace");
    }

}
