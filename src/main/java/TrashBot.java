import Exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * TODO: Change if-else to switch-case when free
 */

public class TrashBot {
    private static List<Task> tasks = new ArrayList<>();

    private static void drawBorder(String content) {
        System.out.println("____________________________________________________________");
        System.out.println(content);
        System.out.println("____________________________________________________________");
    }

    private static void performActionBye() {
        drawBorder(" Bye. Don't cross the road with your eyes closed!");
        System.exit(0);
    }

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

    private static void performActionDelete(String[] divider) throws InvalidFormatException {
        if (divider.length < 2) {
            throw new InvalidFormatException("Specify which task number to delete");
        }
        try {
            int taskNum = Integer.parseInt(divider[1]) - 1;
            validateTaskNumber(taskNum);
            Task removedTask = tasks.remove(taskNum);
            drawBorder(" Noted. I've removed this task:\n  " + removedTask +
                    "\n Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Provide a valid task number");
        }
    }

    private static void performActionMarkUnmark(String[] divider, String command) throws InvalidFormatException {
        if (divider.length < 2) {
            throw new InvalidFormatException("Specify which task number to " + command);
        }
        try {
            int taskNum = Integer.parseInt(divider[1]) - 1;
            validateTaskNumber(taskNum);
            Task task = tasks.get(taskNum);

            if (command.equals("mark")) {
                task.markAsDone();
                drawBorder(" Nice! I've marked this task as done:\n  " + task);
            } else {
                task.markAsNotDone();
                drawBorder(" Okay, I've marked this task as not done:\n  " + task);
            }
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Provide a valid task number");
        }
    }

    private static void validateTaskNumber(int taskNum) throws InvalidFormatException {
        if (taskNum < 0 || taskNum >= tasks.size()) {
            throw new InvalidFormatException("Task number must be between 1 and " + tasks.size());
        }
    }

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

    private static void performActionTaskCreation(String[] divider, String taskType) throws DukeException {
        if (divider.length < 2 || divider[1].trim().isEmpty()) {
            throw new EmptyDescriptionException(taskType);
        }

        Task newTask = createTask(divider[0] + " " + divider[1], taskType);
        tasks.add(newTask);
        drawBorder(" Got it. I've added this task:\n  " + newTask +
                "\n Now you have " + tasks.size() + " tasks in the list.");
    }

    private static void respond(String input) {
        try {
            String[] divider = input.split(" ", 2);
            String dInput = divider[0].toLowerCase();

            if (dInput.equals("bye")) {
                performActionBye();
            } else if (dInput.equals("list")) {
                performActionList();
            } else if (dInput.equals("delete")) {
                performActionDelete(divider);
            } else if (dInput.equals("mark") || dInput.equals("unmark")) {
                performActionMarkUnmark(divider, dInput);
            } else if (dInput.equals("todo") || dInput.equals("deadline") || dInput.equals("event")) {
                performActionTaskCreation(divider, dInput);
            } else {
                throw new UnknownInputException(dInput);
            }
        } catch (DukeException e) {
            drawBorder(" " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        drawBorder(" Hello! I'm TrashBot\n" + " What can I do you for?");
        Scanner scanInput = new Scanner(System.in);
        while (true) {
            String userInput = scanInput.nextLine();
            if (userInput.isEmpty()) {
                continue;
            }
            respond(userInput);
        }
    }
}