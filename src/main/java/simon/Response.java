package simon;

/**
 * Simple result object returned to the GUI.
 */
public class Response {
    private final String message;
    private final boolean exitRequested;

    /**
     * Creates a Response.
     *
     * @param message The textual response to display.
     * @param exitRequested True if the GUI should exit after displaying the response.
     */
    public Response(String message, boolean exitRequested) {
        this.message = message;
        this.exitRequested = exitRequested;
    }

    public String getMessage() {
        return message;
    }

    public boolean isExitRequested() {
        return exitRequested;
    }
}
