package org.trashbot.commands;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.DukeException;
import org.trashbot.tasks.Task;

import java.util.List;
import java.io.IOException;

/**
 * Provides abstract method "execute" to the implementing classes
 */
public interface Command {
    void execute(List<Task> tasks, DataPersistence storage) throws DukeException, IOException;
}
