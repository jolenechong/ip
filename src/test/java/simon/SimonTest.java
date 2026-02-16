package simon;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

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
        // High-level test: run Simon with "bye" and assert expected lines are printed.
        String printed = runSimonAndCaptureOutput();

        assertTrue(printed.contains("Hello! I'm Simon!"), "Main should print the greeting with name Simon");
        assertTrue(printed.contains("Bye. Hope to see you again soon!"), "Main should print the farewell on exit");
    }

    /**
     * Run Simon.main with the "bye" stdin text and capture stdout, while isolating
     * the user home to a temporary directory. Cleans up and restores global
     * state before returning the captured output.
     */
    private String runSimonAndCaptureOutput() throws Exception {
        TestEnv env = setupTestEnv();
        try {
            Simon.main(new String[0]);
            return env.out.toString();
        } finally {
            cleanupEnv(env);
        }
    }

    /**
     * Prepare the test environment: capture stdout, set stdin to `bye`, and
     * set a temporary user.home. Returns a TestEnv holder with originals and
     * resources to be cleaned up by `cleanupEnv`.
     */
    private TestEnv setupTestEnv() throws Exception {
        final String input = "bye\n";
        TestEnv e = new TestEnv();

        e.originalOut = System.out;
        e.originalIn = System.in;
        e.originalUserHome = System.getProperty("user.home");

        e.out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(e.out));

        e.tempHome = Files.createTempDirectory("simon-test-home");
        System.setProperty("user.home", e.tempHome.toString());

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        return e;
    }

    /**
     * Restore global state and delete the temporary home directory.
     */
    private void cleanupEnv(TestEnv e) throws Exception {
        // restore globals
        System.setOut(e.originalOut);
        System.setIn(e.originalIn);
        System.setProperty("user.home", e.originalUserHome);

        // cleanup temp directory: let IOExceptions propagate to the caller
        try (java.util.stream.Stream<Path> s = Files.walk(e.tempHome)) {
            java.util.List<Path> paths = s.sorted(java.util.Comparator.reverseOrder()).toList();
            for (Path p : paths) {
                Files.deleteIfExists(p);
            }
        }
    }

    /**
     * Small holder for test environment resources and originals.
     */
    private static class TestEnv {
        private Path tempHome;
        private PrintStream originalOut;
        private java.io.InputStream originalIn;
        private String originalUserHome;
        private ByteArrayOutputStream out;
    }
}
