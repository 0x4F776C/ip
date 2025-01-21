import java.util.Scanner;

public class TrashBot {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    private static void respond(String input) {
        String[] divider = input.split(" ", 2); // differentiate dInput and number (if exist)
        String dInput = divider[0].toLowerCase(); // get the mark/unmark part of the input

        if (dInput.equals("bye")) {
            System.out.println("____________________________________________________________\n" +
                    " Bye. Don't cross the road with your eyes closed!\n" +
                    "____________________________________________________________\n");
            System.exit(0);
        }
        else if (dInput.equals("list")) {
            if (taskCount == 0) {
                System.out.println("List is empty!");
                return;
            }
            System.out.println("______________________________________________________________");
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
            System.out.println("______________________________________________________________");
        }
        else if (dInput.equals("mark")) {
            if (divider.length < 2) {
                System.out.println("Please specify the task number to mark as mark!");
                return;
            }
            try {
                int taskNum = Integer.parseInt(divider[1]) - 1;
                if (taskNum >= 0 && taskNum < taskCount) {
                    tasks[taskNum].markAsDone();
                    System.out.println("Nice! I've marked this task as mark:");
                    System.out.println("  " + tasks[taskNum]);
                } else {
                    System.out.println("Invalid task number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid task number!");
            }
        }

        else if (dInput.equals("unmark")) {
            if (divider.length < 2) {
                System.out.println("Please specify the task number to mark as not mark!");
                return;
            }
            try {
                int taskNum = Integer.parseInt(divider[1]) - 1;
                if (taskNum >= 0 && taskNum < taskCount) {
                    tasks[taskNum].markAsNotDone();
                    System.out.println("Okay, I've marked this task as not mark:");
                    System.out.println("  " + tasks[taskNum]);
                } else {
                    System.out.println("Invalid task number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid task number!");
            }
        }
        else {
            if (taskCount < tasks.length) {
                tasks[taskCount] = new Task(input);
                System.out.println("Added: " + tasks[taskCount]);
                taskCount++;
            } else {
                System.out.println("List is full!");
            }
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