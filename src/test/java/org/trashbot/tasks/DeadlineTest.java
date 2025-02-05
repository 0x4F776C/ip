package org.trashbot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeadlineTest {
    @Test
    void testGetDateTimeWithValidFormat1() {
        Deadline deadline = new Deadline("deadline submit report /by Sep 11 2023 1:33am");
        assertEquals("Sep 11 2023 1:33am", deadline.getDateTime());
    }

    @Test
    void testGetDateTimeWithValidFormat2() {
        Deadline deadline = new Deadline("deadline submit report /by 2023-09-11 0133");
        assertEquals("Sep 11 2023 1:33am", deadline.getDateTime());
    }

    @Test
    void testGetDateTimeWithInvalidFormat() {
        String invalidDateTime = "invalid-date-format";
        Deadline deadline = new Deadline("deadline submit report /by " + invalidDateTime);
        assertEquals(invalidDateTime, deadline.getDateTime());
    }

    @Test
    void testGetDateTimeWithEmptyDate() {
        Deadline deadline = new Deadline("deadline submit report /by ");
        assertEquals("", deadline.getDateTime());
    }

    @Test
    void testGetDateTimeWithPartialDate() {
        String partialDate = "Sep 11";
        Deadline deadline = new Deadline("deadline submit report /by " + partialDate);
        assertEquals(partialDate, deadline.getDateTime());
    }
}
