package org.trashbot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * Extends the {@link Task} class and includes a deadline by which the task must be completed.
 */
public class Deadline extends Task {
    /**
     * The string value for /by.
     */
    private static final String STRING_BY = "/by";

    /**
     * This variable will store the actual value of /by.
     */
    protected final String by;

    /**
     * Constructs a {@code Deadline} object by parsing the input string to extract the task description and deadline.
     *
     * @param input the input string containing the task description and deadline in the format
     *              "deadline &lt;description&gt; /by &lt;deadline&gt;".
     */
    public Deadline(String input) {
        super(input.substring(9, input.indexOf(STRING_BY)).trim());
        this.by = input.substring(input.indexOf(STRING_BY) + 4).trim();
    }

    /**
     * Attempts to parse and format the deadline using multiple predefined datetime patterns.
     *
     * @return A formatted datetime string in the format "MMM dd yyyy h:mma" (e.g., "Sep 11 2001 1:33am"),
     *     or the original deadline string if parsing fails.
     */
    public String getDateTime() {
        try {
            DateTimeFormatter[] inputFormatters = {
                    DateTimeFormatter.ofPattern("MMM d yyyy h:mma"),
                    DateTimeFormatter.ofPattern("MMM dd yyyy h:mma"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")
            };

            LocalDateTime dateTime = null;
            for (DateTimeFormatter formatter : inputFormatters) {
                try {
                    dateTime = LocalDateTime.parse(by, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    continue;
                }
            }

            if (dateTime == null) {
                throw new DateTimeParseException("Unable to parse date", by, 0);
            }

            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                    + " "
                    + dateTime.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return by;
        }
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return A string in the format "[D]&lt;description&gt; (by: &lt;formatted deadline&gt;)".
     */
    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: " + getDateTime()
                + ")";
    }
}
