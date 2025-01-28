package org.trashbot.storage;

import org.junit.jupiter.api.Test;
import org.trashbot.tasks.Deadline;
import org.trashbot.tasks.Event;
import org.trashbot.tasks.Task;
import org.trashbot.tasks.Todo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class FileStorageTest {
    private FileStorage storage = new FileStorage("test.txt");

    @Test
    void convertStringToTask_ValidTodo() {
        String input = "T | 1 | eat mee hoon kway";
        Task task = storage.convertStringToTask(input);

        assertNotNull(task);
        assertTrue(task instanceof Todo);
        assertTrue(task.isDone());
        assertEquals("eat mee hoon kway", task.getDescription());
    }

    @Test
    void convertStringToTask_ValidDeadline() {
        String input = "D | 0 | watch netflix | Sep 11 2020 11:59pm";
        Task task = storage.convertStringToTask(input);

        assertNotNull(task);
        assertTrue(task instanceof Deadline);
        assertFalse(task.isDone());
        assertEquals("watch netflix", task.getDescription());
        assertEquals("Sep 11 2020 11:59pm", ((Deadline) task).getDateTime());
    }

    @Test
    void convertStringToTask_ValidEvent() {
        String input = "E | 1 | dance with barney | 2pm | 4pm";
        Task task = storage.convertStringToTask(input);

        assertNotNull(task);
        assertTrue(task instanceof Event);
        assertTrue(task.isDone());
        assertEquals("dance with barney", task.getDescription());
        assertEquals("2pm", ((Event) task).getFrom());
        assertEquals("4pm", ((Event) task).getTo());
    }

    @Test
    void convertStringToTask_InvalidFormat() {
        String input = "X | invalid | format";
        Task task = storage.convertStringToTask(input);
        assertNull(task);
    }

    @Test
    void convertStringToTask_EmptyString() {
        String input = "";
        Task task = storage.convertStringToTask(input);
        assertNull(task);
    }

    @Test
    void convertStringToTask_MissingFields() {
        String input = "D | 1 | die"; // Missing deadline
        Task task = storage.convertStringToTask(input);
        assertNull(task);
    }
}
