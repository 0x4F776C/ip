package org.trashbot.commands;

import org.trashbot.tasks.Task;
import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.DukeException;

import java.util.List;
import java.io.IOException;

/**
 * Provide an abstract method for executing different command
 */
public interface Command {
    /**
     * Provide an abstract method to execute different command
     * @param tasks list of tasks to operate on
     * @param storage storage with save/load operation
     * @throws DukeException if a custom error occurs during execution
     * @throws IOException if an I/O error occurs during file operation
     */
    void execute(List<Task> tasks, DataPersistence storage) throws DukeException, IOException;
}
