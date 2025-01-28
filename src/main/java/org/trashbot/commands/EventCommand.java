package org.trashbot.commands;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.InvalidFormatException;
import org.trashbot.tasks.Event;
import org.trashbot.tasks.Task;

import java.io.IOException;
import java.util.List;

public class EventCommand implements Command {
    private String input;

    public EventCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(List<Task> tasks, DataPersistence storage) throws InvalidFormatException, IOException {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new InvalidFormatException("Please use the format: event /from <start> /to <end>");
        }
        Task newTask = new Event(input);
        tasks.add(newTask);
        storage.save(tasks);
        System.out.println(" Got it. I've added this task:\n  " + newTask
                + "\n Now you have "
                + tasks.size()
                + " tasks in the list.");
    }
}
