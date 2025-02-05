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
     * Execute the command and return a String containing the command's output
     *
     * @param tasks   list of tasks to operate on
     * @param storage storage with save/load operation
     * @return String containing the command's output message
     * @throws DukeException if a custom error occurs during execution
     * @throws IOException   if an I/O error occurs during file operation
     */
    String execute(List<Task> tasks, DataPersistence storage) throws DukeException, IOException;
}
