package simon;

/**
 * Represents a simple result object returned to the GUI.
 */
public class Response {
    private final String message;
    private final boolean isExitRequested;

    /**
     * Creates a Response.
     *
     * @param message The textual response to display.
     * @param isExitRequested True if the GUI should exit after displaying the response.
     */
    public Response(String message, boolean isExitRequested) {
        this.message = message;
        this.isExitRequested = isExitRequested;
    }

    public String getMessage() {
        return message;
    }

    public boolean isExitRequested() {
        return isExitRequested;
    }
}
