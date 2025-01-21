import java.util.Scanner;

public class TrashBot {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    private static void drawBorder(String content) {
        System.out.println("____________________________________________________________");
        System.out.println(content);
        System.out.println("____________________________________________________________");
    }

    private static void respond(String input) {
        String[] divider = input.split(" ", 2); // differentiate dInput and number (if exist)
        String dInput = divider[0].toLowerCase(); // get the mark/unmark part of the input

        if (dInput.equals("bye")) {
            drawBorder(" Bye. Don't cross the road with your eyes closed!");
            System.exit(0);
        }
        else if (dInput.equals("list")) {
            if (taskCount == 0) {
                System.out.println("List is empty!");
                return;
            }
            StringBuilder output = new StringBuilder(" Here are the tasks in your list:\n");
            for (int i = 0; i < taskCount; i++) {
                output.append(" ").append(i + 1).append(".").append(tasks[i]).append("\n"); // replace the use of plus (+) to connect variable(s) and content(s)
            }
            drawBorder(output.toString().trim()); // remove whitespaces
        }
        else if (dInput.equals("mark")) {
            if (divider.length < 2) {
                System.out.println("Please specify the task number to mark!");
                return;
            }
            try {
                int taskNum = Integer.parseInt(divider[1]) - 1;
                if (taskNum >= 0 && taskNum < taskCount) {
                    tasks[taskNum].markAsDone();
                    drawBorder(" Nice! I've marked this task as done:\n  " + tasks[taskNum]);
                } else {
                    System.out.println("Invalid task number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid task number!");
            }
        }
        else if (dInput.equals("unmark")) {
            if (divider.length < 2) {
                System.out.println("Please specify the task number to unmark!");
                return;
            }
            try {
                int taskNum = Integer.parseInt(divider[1]) - 1;
                if (taskNum >= 0 && taskNum < taskCount) {
                    tasks[taskNum].markAsNotDone();
                    drawBorder(" Okay, I've marked this task as not done:\n  " + tasks[taskNum]);
                } else {
                    System.out.println("Invalid task number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid task number!");
            }
        }
        else if (dInput.equals("todo") || dInput.equals("deadline") || dInput.equals("event")) {
            if (divider.length < 2) {
                System.out.println("Please specify the task details!");
                return;
            }
            if (taskCount < tasks.length) {
                try {
                    if (dInput.equals("todo")) {
                        tasks[taskCount] = new Todo(input);
                    } else if (dInput.equals("deadline")) {
                        tasks[taskCount] = new Deadline(input);
                    } else {
                        tasks[taskCount] = new Event(input);
                    }
                    drawBorder(" Got it. I've added this task:\n   " + tasks[taskCount] +
                            "\n Now you have " + (taskCount + 1) + " tasks in the list.");
                    taskCount++;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please check you input!");
                }
            } else {
                System.out.println("List is full!");
            }
        }
        else {
            System.out.println("Please use todo, deadline, event, mark, unmark, or list.");
        }
    }

    public static void main(String[] args) {
        String welcomeText = "____________________________________________________________\n" +
                " Hello! I'm TrashBot\n" +
                " What can I do you for?\n" +
                "____________________________________________________________\n";
        System.out.println(welcomeText);
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