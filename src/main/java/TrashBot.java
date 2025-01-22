import features.*;
import exceptions.*;
import disk.TrashBotFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A task management application that handles todo items, deadlines, and events.
 * Supports persistent storage and basic CRUD operations through a command-line interface.
 */
public class TrashBot {
    /**
     * Handles file operations for saving and loading tasks.
     */
    private static TrashBotFile trashBotFile = new TrashBotFile("./data/TrashBot.sav");

    /**
     * Stores the list of tasks currently being managed.
     */
    private static List<Task> tasks = new ArrayList<>();

    /**
     * Prints content within a formatted border.
     * @param content The text to display
     */
    private static void drawBorder(String content) {
        System.out.println("____________________________________________________________");
        System.out.println(content);
        System.out.println("____________________________________________________________");
    }

    /**
     * Saves current tasks and exits the application.
     * @throws IOException If saving tasks fails
     */
    private static void performActionBye() throws IOException {
        drawBorder(" Bye. Don't cross the road with your eyes closed!");
        trashBotFile.save(tasks);
        System.exit(0);
    }

    /**
     * Displays all tasks currently in the list.
     */
    private static void performActionList() {
        if (tasks.isEmpty()) {
            System.out.println("List is empty!");
            return;
        }
        StringBuilder output = new StringBuilder(" Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            output.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        drawBorder(output.toString().trim());
    }

    /**
     * Removes a task at the specified index.
     * @param divider Command arguments containing the task number
     * @throws InvalidFormatException If task number is invalid or missing
     * @throws IOException If saving after deletion fails
     */
    private static void performActionDelete(String[] divider) throws InvalidFormatException, IOException {
        if (divider.length < 2) {
            throw new InvalidFormatException("Specify which task number to delete");
        }
        try {
            int taskNum = Integer.parseInt(divider[1]) - 1;
            validateTaskNumber(taskNum);
            Task removedTask = tasks.remove(taskNum);
            trashBotFile.save(tasks);
            drawBorder(" Noted. I've removed this task:\n  " + removedTask +
                    "\n Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Provide a valid task number");
        }
    }

    /**
     * Toggles the completion status of a task.
     * @param divider Command arguments containing the task number
     * @param command Either "mark" or "unmark"
     * @throws InvalidFormatException If task number is invalid or missing
     * @throws IOException If saving after status change fails
     */
    private static void performActionMarkUnmark(String[] divider, String command) throws InvalidFormatException, IOException {
        if (divider.length < 2) {
            throw new InvalidFormatException("Specify which task number to " + command);
        }
        try {
            int taskNum = Integer.parseInt(divider[1]) - 1;
            validateTaskNumber(taskNum);
            Task task = tasks.get(taskNum);

            if (command.equals("mark")) {
                task.markAsDone();
                trashBotFile.save(tasks);
                drawBorder(" Nice! I've marked this task as done:\n  " + task);
            } else {
                task.markAsNotDone();
                trashBotFile.save(tasks);
                drawBorder(" Okay, I've marked this task as not done:\n  " + task);
            }
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Provide a valid task number");
        }
    }

    /**
     * Validates if a task number is within valid range.
     * @param taskNum The task index to validate
     * @throws InvalidFormatException If task number is out of bounds
     */
    private static void validateTaskNumber(int taskNum) throws InvalidFormatException {
        if (taskNum < 0 || taskNum >= tasks.size()) {
            throw new InvalidFormatException("Task number must be between 1 and " + tasks.size());
        }
    }

    /**
     * Creates a new task based on type and input.
     * @param input The task description and metadata
     * @param taskType The type of task (todo, deadline, or event)
     * @return The created Task object
     * @throws DukeException If task creation fails due to invalid format
     */
    private static Task createTask(String input, String taskType) throws DukeException {
        if (taskType.equals("todo")) {
            return new Todo(input);
        } else if (taskType.equals("deadline")) {
            if (!input.contains("/by")) {
                throw new InvalidFormatException("Please use the format: deadline <task> /by <due>");
            }
            return new Deadline(input);
        } else {
            if (!input.contains("/from") || !input.contains("/to")) {
                throw new InvalidFormatException("Please use the format: event /from <start> /to <end>");
            }
            return new Event(input);
        }
    }

    /**
     * Handles creation of new tasks.
     * @param divider Command arguments containing task details
     * @param taskType The type of task to create
     * @throws DukeException If task creation fails
     * @throws IOException If saving new task fails
     */
    private static void performActionTaskCreation(String[] divider, String taskType) throws DukeException, IOException {
        if (divider.length < 2 || divider[1].trim().isEmpty()) {
            throw new EmptyDescriptionException(taskType);
        }

        Task newTask = createTask(divider[0] + " " + divider[1], taskType);
        tasks.add(newTask);
        trashBotFile.save(tasks);
        drawBorder(" Got it. I've added this task:\n  " + newTask +
                "\n Now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Processes user input and executes corresponding commands.
     * @param input The user's command string
     */
    private static void respond(String input) {
        try {
            String[] divider = input.split(" ", 2);
            String dInput = divider[0].toLowerCase();

            switch (dInput) {
                case "bye":
                    performActionBye();
                    break;
                case "list":
                    performActionList();
                    break;
                case "delete":
                    performActionDelete(divider);
                    break;
                case "mark":
                case "unmark":
                    performActionMarkUnmark(divider, dInput);
                    break;
                case "todo":
                case "deadline":
                case "event":
                    performActionTaskCreation(divider, dInput);
                    break;
                default:
                    throw new UnknownInputException(dInput);
            }
        } catch (DukeException e) {
            drawBorder(" " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Application entry point. Initializes the bot and starts command processing loop.
     * @param args Command line arguments (not used)
     * @throws IOException If initialization fails
     */
    public static void main(String[] args) throws IOException{
        try {
            tasks = trashBotFile.load();

            drawBorder(" Hello! I'm TrashBot\n" + " What can I do you for?");
            Scanner scanInput = new Scanner(System.in);

            while (true) {
                String userInput = scanInput.nextLine();
                if (userInput.isEmpty()) {
                    continue;
                }
                respond(userInput);
            }
        } catch (IOException e) {
            System.out.println("Can't access file: " + e.getMessage());
            System.exit(1);
        }
    }
}