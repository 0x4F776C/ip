package org.trashbot.commands;

import java.util.List;

import org.trashbot.core.DataPersistence;
import org.trashbot.tasks.Task;

/**
 * The ByeCommand class represents a command that terminates the application.
 * It implements the {@link Command} interface and provides the behavior for
 * exiting the program when executed.
 */
public class ByeCommand implements Command {
    /**
     * Executes the command, which terminates the program.
     * <p>
     * This method calls {@link System#exit(int)} with a status code of 0,
     * indicating a normal termination of the application.
     * </p>
     *
     * @param tasks the current list of tasks (not used in this command)
     * @param storage the data persistence object (not used in this command)
     * @return String containing the command's output message
     */
    @Override
    public String execute(List<Task> tasks, DataPersistence storage) {
        return "END_PROGRAM";
    }
}
