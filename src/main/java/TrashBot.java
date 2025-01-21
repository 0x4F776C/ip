import java.util.Scanner;

public class TrashBot {
    // Method for responding to user input
    public static void respond(String input) {
        if (input.equals("bye")) {
            System.out.println("____________________________________________________________\n" +
                    " Bye. Don't cross the road with your eyes closed!\n" +
                    "____________________________________________________________\n");
        }
        else {
            System.out.println("____________________________________________________________\n" +
                    input +
                    "\n" +
                    "____________________________________________________________\n");
        }
    }

    public static void main(String[] args) {
        // Variable for welcome text
        String welcomeText = "____________________________________________________________\n" +
                " Hello! I'm TrashBot\n" +
                " What can I do you for?\n" +
                "____________________________________________________________\n";

        // Print out welcome text
        System.out.println(welcomeText);

        // Create Scanner object
        Scanner scanInput = new Scanner(System.in);
        // Get user input
        String userInput = scanInput.nextLine();

        // Get user input and respond accordingly
        respond(userInput);
    }
}