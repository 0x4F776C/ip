package org.trashbot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.trashbot.exceptions.DukeException;

class DeadlineTest {
    @Test
    void testGetDateTimeWithValidFormat1() throws DukeException {
        Deadline deadline = new Deadline("deadline submit report /by Sep 11 2023 1:33am");
        assertEquals("Sep 11 2023 1:33am", deadline.getDateTime());
    }

    @Test
    void testGetDateTimeWithValidFormat2() throws DukeException {
        Deadline deadline = new Deadline("deadline submit report /by 2023-09-11 0133");
        assertEquals("Sep 11 2023 1:33am", deadline.getDateTime());
    }

    @Test
    void testGetDateTimeWithInvalidFormat() {
        String invalidDateTime = "invalid-date-format";
        assertThrows(DukeException.class, () -> {
            new Deadline("deadline submit report /by " + invalidDateTime);
        });
    }

    @Test
    void testGetDateTimeWithEmptyDate() {
        assertThrows(DukeException.class, () -> {
            new Deadline("deadline submit report /by ");
        });
    }

    @Test
    void testGetDateTimeWithPartialDate() {
        String partialDate = "Sep 11";
        assertThrows(DukeException.class, () -> {
            new Deadline("deadline submit report /by " + partialDate);
        });
    }

    @Test
    void testConstructorValidation() {
        assertThrows(DukeException.class, () -> {
            new Deadline("deadline submit report /by something completely invalid");
        });
    }
}
