package org.trashbot.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {
    @Test
    void getDateTime_ValidFormat1() {
        Deadline deadline = new Deadline("deadline submit report /by Sep 11 2023 1:33am");
        assertEquals("Sep 11 2023 1:33am", deadline.getDateTime());
    }

    @Test
    void getDateTime_ValidFormat2() {
        Deadline deadline = new Deadline("deadline submit report /by 2023-09-11 0133");
        assertEquals("Sep 11 2023 1:33am", deadline.getDateTime());
    }

    @Test
    void getDateTime_InvalidFormat() {
        String invalidDateTime = "invalid-date-format";
        Deadline deadline = new Deadline("deadline submit report /by " + invalidDateTime);
        assertEquals(invalidDateTime, deadline.getDateTime());
    }

    @Test
    void getDateTime_EmptyDate() {
        Deadline deadline = new Deadline("deadline submit report /by ");
        assertEquals("", deadline.getDateTime());
    }

    @Test
    void getDateTime_PartialDate() {
        String partialDate = "Sep 11";
        Deadline deadline = new Deadline("deadline submit report /by " + partialDate);
        assertEquals(partialDate, deadline.getDateTime());
    }
}