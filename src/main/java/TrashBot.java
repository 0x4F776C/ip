import Exceptions.*;

import java.util.Scanner;

public class TrashBot {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    private static void drawBorder(String content) {
        System.out.println("____________________________________________________________");
        System.out.println(content);
        System.out.println("____________________________________________________________");
    }

    private static void respond(String input) throws DukeException {
        try {
            String[] divider = input.split(" ", 2); // differentiate dInput and number (if exist)
            String dInput = divider[0].toLowerCase(); // get the mark/unmark part of the input

            if (dInput.equals("bye")) {
                drawBorder(" Bye. Don't cross the road with your eyes closed!");
                System.exit(0);
            } else if (dInput.equals("list")) {
                if (taskCount == 0) {
                    System.out.println("List is empty!");
                    return;
                }
                StringBuilder output = new StringBuilder(" Here are the tasks in your list:\n");
                for (int i = 0; i < taskCount; i++) {
                    output.append(" ").append(i + 1).append(".").append(tasks[i]).append("\n"); // replace the use of plus (+) to connect variable(s) and content(s)
                }
                drawBorder(output.toString().trim()); // remove whitespaces
            } else if (dInput.equals("mark") || dInput.equals("unmark")) {
                if (divider.length < 2) {
                    throw new InvalidFormatException("Specify which task number to " + dInput);
                }
                try {
                    int taskNum = Integer.parseInt(divider[1]) - 1;
                    if (taskNum < 0 || taskNum >= taskCount) {
                        throw new InvalidFormatException("Task number must be between 1 and " + taskCount);
                    } else {
                        if (dInput.equals("mark")) {
                            tasks[taskNum].markAsDone();
                            drawBorder(" Nice! I've marked this task as done:\n  " + tasks[taskNum]);
                        } else {
                            tasks[taskNum].markAsNotDone();
                            drawBorder(" Okay, I've marked this task as not done:\n  " + tasks[taskNum]);
                        }
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidFormatException("Provide a valid task number");
                }
            } else if (dInput.equals("todo") || dInput.equals("deadline") || dInput.equals("event")) {
                if (divider.length < 2 || divider[1].trim().isEmpty()) {
                    throw new EmptyDescriptionException(dInput);
                }
                if (taskCount >= tasks.length) {
                    throw new DukeException("Task list is at max capacity: " + tasks.length);
                }
                if (dInput.equals("todo")) {
                    tasks[taskCount] = new Todo(input);
                } else if (dInput.equals("deadline")) {
                    if (!input.contains("/by")) {
                        throw new InvalidFormatException("Please use the format: deadline <task> /by <due>");
                    }
                    tasks[taskCount] = new Deadline(input);
                } else {
                    if (!input.contains("/from") || !input.contains("/to")) {
                        throw new InvalidFormatException("Please use the format: event /from <start> /to <end>");
                    }
                    tasks[taskCount] = new Event(input);
                }
                drawBorder(" Got it. I've added this task:\n  " + tasks[taskCount] +
                        "\n Now you have " + (taskCount + 1) + " tasks in the list.");
                taskCount++;
            } else {
                throw new UnknownInputException(dInput);
            }
        } catch (DukeException e) {
            drawBorder(" " + e.getMessage());
        }
    }

    public static void main(String[] args) throws DukeException {
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