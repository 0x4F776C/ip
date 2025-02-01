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
     * The deadline for the task in string format.
     */
    protected final String by;

    /**
     * Constructs a {@code Deadline} object by parsing the input string to extract the task description and deadline.
     *
     * @param input the input string containing the task description and deadline in the format
     *              "deadline &lt;description&gt; /by &lt;deadline&gt;".
     */
    public Deadline(String input) {
        super(input.substring(9, input.indexOf("/by")).trim()); // get description
        this.by = input.substring(input.indexOf("/by") + 4).trim(); // get deadline
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
                    DateTimeFormatter.ofPattern("MMM dd yyyy h:mma"), // SEP 11 2001 1:33am
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm") // 2001-09-11 0133
            };

            LocalDateTime dateTime = null;
            for (DateTimeFormatter formatter : inputFormatters) { // try all datetime format
                try {
                    dateTime = LocalDateTime.parse(by, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    // I give up
                }
            }

            if (dateTime == null) { // if the format don't match, datetime will still be null and die
                throw new DateTimeParseException("Unable to parse date", by, 0);
            }

            // if all goes well, return the datetime :D
            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                    + " "
                    + dateTime.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return by; // use the original input if ALL ELSE FAILS
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
