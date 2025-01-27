package org.trashbot.ui;

import org.trashbot.core.TrashBot;

import java.util.Scanner;

public class TrashBotUI {
    private TrashBot trashBot;

    public TrashBotUI(TrashBot trashBot) {
        this.trashBot = trashBot;
    }

    public void run() {
        try {
            drawBorder(" Hello! I'm TrashBot\n What can I do you for?");
            Scanner scanInput = new Scanner(System.in);

            while (true) {
                String userInput = scanInput.nextLine();
                if (userInput.isEmpty()) {
                    continue;
                }
                trashBot.processCommand(userInput);
            }
        } catch (Exception e) {
            System.out.println("Can't access file: " + e.getMessage());
            System.exit(1);
        }
    }

    private void drawBorder(String content) {
        System.out.println("____________________________________________________________");
        System.out.println(content);
        System.out.println("____________________________________________________________");
    }
}
