package org.trashbot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.trashbot.exceptions.DukeException;

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
     * Keep cache copy of formatted datetime
     */
    private String finalDateTime = null;

    /**
     * Check if check has been done
     */
    private boolean isChecked = false;

    /**
     * Constructs a {@code Deadline} object by parsing the input string to extract the task description and deadline.
     *
     * @param input the input string containing the task description and deadline in the format
     *              "deadline &lt;description&gt; /by &lt;deadline&gt;".
     */
    public Deadline(String input) throws DukeException {
        super(input.substring(9, input.indexOf(STRING_BY)).trim());
        this.by = input.substring(input.indexOf(STRING_BY) + 4).trim();

        checkInputDateTime();
    }

    /**
     * Validates the datetime format.
     *
     * @throws DukeException if the datetime format is invalid
     */
    private void checkInputDateTime() throws DukeException {
        if (isChecked) {
            return;
        }

        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("MMM d yyyy h:mma"),
                DateTimeFormatter.ofPattern("MMM dd yyyy h:mma"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")
        };

        LocalDateTime dateTime = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                dateTime = LocalDateTime.parse(by, formatter);
                break;
            } catch (DateTimeParseException e) {
                // go to next formatter
            }
        }

        if (dateTime == null) {
            throw new DukeException("Invalid datetime format. Please check your input.");
        }

        finalDateTime = dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " "
                + dateTime.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase();
        isChecked = true;
    }

    /**
     * Gets the formatted datetime string.
     *
     * @return A formatted datetime string in the format "MMM dd yyyy h:mma" (e.g., "Sep 11 2001 1:33am")
     * @throws DukeException if the datetime format is invalid
     */
    public String getDateTime() throws DukeException {
        checkInputDateTime();
        return finalDateTime;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return A string in the format "[D]&lt;description&gt; (by: &lt;formatted deadline&gt;)".
     */
    @Override
    public String toString() {
        try {
            return "[D]" + super.toString() + " (by: " + getDateTime() + ")";
        } catch (DukeException e) {
            return "[D]" + super.toString() + " (by: ERROR - Invalid datetime format)";
        }
    }
}
