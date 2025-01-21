import java.util.Scanner;

public class TrashBot {
    private static String[] tasks = new String[100]; // Task array
    private static int taskCount = 0;  // Keep track of number of tasks

    // Method for responding to user input
    private static void respond(String input) {
        if (input.equals("bye")) {
            System.out.println("____________________________________________________________\n" +
                    " Bye. Don't cross the road with your eyes closed!\n" +
                    "____________________________________________________________\n");
            System.exit(0);  // Exit the program when "bye" is entered
        }
        else if (input.equals("list")) {
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
        else {
            if (taskCount < tasks.length) {
                tasks[taskCount] = input;
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
                "____________________________________________________________\n"; // Variable for welcome string

        System.out.println(welcomeText); // Print out welcome string

        Scanner scanInput = new Scanner(System.in); // Create Scanner object

        while (true) {
            String userInput = scanInput.nextLine(); // Get user input
            if (userInput.isEmpty()) {
                continue;  // Skip empty inputs
            }
            respond(userInput); // Respond according to input match
        }
    }
}