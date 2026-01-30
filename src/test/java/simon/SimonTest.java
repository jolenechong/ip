package simon;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Simon}.
 * This test verifies that the main method of the Simon application
 * correctly prints the greeting message and exits upon receiving the "bye" command.
 * It will use simon-test-home as temp user home directory and send "bye" as input.
 */
public class SimonTest {

    /**
     * Tests that the main method prints the greeting and exits on "bye" command.
     *
     * @throws Exception if any I/O error occurs during the test.
     */
    @Test
    void main_printsGreetingAndExitOnBye() throws Exception {
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        String originalUserHome = System.getProperty("user.home");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Path tempHome = Files.createTempDirectory("simon-test-home");
        System.setProperty("user.home", tempHome.toString());

        System.setIn(new ByteArrayInputStream("bye\n".getBytes())); // say bye

        try {
            Simon.main(new String[0]); // run main
            String printed = out.toString();

            // check what's printed
            assertTrue(printed.contains("Hello! I'm Simon!"), "Main should print the greeting with name Simon");
            assertTrue(printed.contains("Bye. Hope to see you again soon!"), "Main should print the farewell on exit");
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
            System.setProperty("user.home", originalUserHome);

            // cleanup temp directory
            try {
                Files.walk(tempHome)
                        .sorted((a, b) -> b.compareTo(a))
                        .forEach(p -> {
                            try {
                                Files.deleteIfExists(p);
                            } catch (Exception ignored) {
                               // ignored
                            }
                        });
            } catch (Exception ignored) {
                // ignored
            }
        }
    }
}
