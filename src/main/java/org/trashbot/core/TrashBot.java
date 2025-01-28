package org.trashbot.core;

import org.trashbot.commands.Command;
import org.trashbot.commands.TodoCommand;
import org.trashbot.commands.DeadlineCommand;
import org.trashbot.commands.EventCommand;
import org.trashbot.commands.ListCommand;
import org.trashbot.commands.DeleteCommand;
import org.trashbot.commands.MarkCommand;
import org.trashbot.exceptions.DukeException;
import org.trashbot.exceptions.UnknownInputException;
import org.trashbot.storage.FileStorage;
import org.trashbot.tasks.Task;
import org.trashbot.ui.TrashBotUI;

import java.io.IOException;
import java.util.List;

public class TrashBot {
    private List<Task> tasks;
    private FileStorage storage;

    public TrashBot(String storageFilePath) throws IOException {
        this.storage = new FileStorage(storageFilePath);
        this.tasks = storage.load();
    }

    public void processCommand(String input) {
        try {
            Command command = parseCommand(input);
            command.execute(tasks, storage);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Command parseCommand(String input) throws UnknownInputException {
        String[] parts = input.split(" ", 2);
        String commandType = parts[0].toLowerCase();

        switch (commandType) {
            case "todo":
                return new TodoCommand(input);
            case "deadline":
                return new DeadlineCommand(input);
            case "event":
                return new EventCommand(input);
            case "list":
                return new ListCommand();
            case "delete":
                return new DeleteCommand(Integer.parseInt(parts[1]) - 1);
            case "mark":
                return new MarkCommand(Integer.parseInt(parts[1]) - 1, true);
            case "unmark":
                return new MarkCommand(Integer.parseInt(parts[1]) - 1, false);
            case "bye":
                System.exit(0);
            default:
                throw new UnknownInputException(commandType);
        }
    }

    public static void main(String[] args) {
        try {
            TrashBot trashBot = new TrashBot("./data/TrashBot.sav");
            TrashBotUI ui = new TrashBotUI(trashBot);
            ui.run();
        } catch (IOException e) {
            System.out.println("Initialization failed: " + e.getMessage());
            System.exit(1);
        }
    }
}