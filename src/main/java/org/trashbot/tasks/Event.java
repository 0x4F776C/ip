package org.trashbot.tasks;

/**
 * Represents a time-bound event in the task management system.
 * This class extends {@link Task} to include start and end times for events
 * that occur during a specific time frame.
 *
 * <p>The class parses input strings in the format "event &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;"
 * to create event tasks. Both start and end times are stored as strings to support
 * flexible time format inputs.</p>
 *
 * <p>Example usage:
 * <pre>
 * Event meeting = new Event(
 *   "event eat churros at clarke quay before drinking at red bridge
 *   + club /from 8pm /to 10pm");
 * System.out.println(meeting); // Outputs: [E][] eat churros at
 *   clarke quay before drinking at red bridge
 *   + club (from: 8pm to: 10pm)
 * </pre>
 * </p>
 *
 * @see Task
 */
public class Event extends Task {
    /**
     * The string value for /from
     */
    private static final String STRING_FROM = "/from";

    /**
     * The string value for /to
     */
    private static final String STRING_TO = "/to";

    /**
     * The start time
     */
    private final String from;

    /**
     * The end time
     */
    private final String to;

    /**
     * Constructs a new Event by parsing the input string to extract description
     * and time details.
     *
     * @param input the raw input string in the format "event &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;"
     */
    public Event(String input) {
        super(input.substring(6, input.indexOf(STRING_FROM)).trim());

        assert input
                .contains(STRING_FROM)
                && input
                .contains(STRING_TO)
                :
                "Event must contain both /from and /to time specifications";

        String timeInfo = input.substring(input.indexOf(STRING_FROM));
        this.from = timeInfo.substring(6, timeInfo.indexOf(STRING_TO)).trim();
        this.to = timeInfo.substring(timeInfo.indexOf(STRING_TO) + 4).trim();
    }

    /**
     * Gets the start time of the event.
     *
     * @return The event's start time as a string
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets the start time of the event.
     *
     * @return The event's end time as a string
     */
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFrom() + " to: " + getTo() + ")";
    }
}
