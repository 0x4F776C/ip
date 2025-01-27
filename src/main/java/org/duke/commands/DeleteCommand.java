package org.trashbot.commands;

import org.trashbot.tasks.Task;
import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.InvalidFormatException;

import java.util.List;
import java.io.IOException;

public class DeleteCommand implements Command {
    private int taskId;

    public DeleteCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(List<Task> tasks, DataPersistence storage) throws InvalidFormatException, IOException {
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new InvalidFormatException("Task number must be between 1 and " + tasks.size());
        }

        Task removeTask = tasks.remove(taskId);
        storage.save(tasks);
        System.out.println(" Got it. I've rmeoved this task: \n " + removeTask + "\n Now you have " + tasks.size() + " tasks in the list.");
    }
}
