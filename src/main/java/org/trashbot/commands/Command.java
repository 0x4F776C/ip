package org.trashbot.commands;

import java.io.IOException;
import java.util.List;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.DukeException;
import org.trashbot.tasks.Task;

/**
 * Provides abstract method "execute" to the implementing classes
 */
public interface Command {
    /**
     * Provide an abstract method to execute different command
     *
     * @param tasks   list of tasks to operate on
     * @param storage storage with save/load operation
     * @throws DukeException if a custom error occurs during execution
     * @throws IOException   if an I/O error occurs during file operation
     */
    void execute(List<Task> tasks, DataPersistence storage) throws DukeException, IOException;
}
