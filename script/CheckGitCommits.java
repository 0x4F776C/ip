import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CheckGitCommits {
    // Minimum words required in a commit message
    private static final int MIN_WORDS = 5;
    // Common undescriptive commit messages
    private static final List<String> BAD_PATTERNS = Arrays.asList("fix", "update", "change", "edit", "temp", "test");

    public static void main(String[] args) {
        List<String> commits = getLastCommits(5);

        if (commits.isEmpty() || commits.size() < 5) {
            System.out.println("Error: Less than 5 commits found. Please ensure at least 5 commits exist.");
            return;
        }

        System.out.println("Checking last 5 commit messages...\n");
        boolean allValid = true;

        for (int i = 0; i < commits.size(); i++) {
            String message = commits.get(i);
            String validation = checkCommitMessage(message);
            boolean isValid = validation.equals("Valid");
            System.out.printf("%d. %s -> %s%n", i + 1, message, isValid ? "Pass" : "Fail (" + validation + ")");
            if (!isValid) {
                allValid = false;
            }
        }

        if (allValid) {
            System.out.println("\nAll commit messages meet the standard!");
        } else {
            System.out.println("\nSome commit messages need improvement.");
        }
    }

    /**
     * Executes the Git command to retrieve the last 'n' commit messages.
     */
    private static List<String> getLastCommits(int count) {
        try {
            Process process = new ProcessBuilder("git", "log", "-n", String.valueOf(count), "--pretty=format:%s")
                    .start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            return reader.lines().toList();
        } catch (IOException e) {
            System.out.println("Error: Could not retrieve commit logs. Ensure you are in a Git repository.");
            return List.of();
        }
    }

    /**
     * Validates a commit message based on specific rules.
     */
    private static String checkCommitMessage(String message) {
        String[] words = message.split("\\s+");

        if (words.length < MIN_WORDS) {
            return "Too short";
        }

        if (!Character.isUpperCase(message.charAt(0))) {
            return "Should start with an uppercase letter";
        }

        if (BAD_PATTERNS.contains(words[0].toLowerCase())) {
            return "Too generic";
        }

        if (message.matches("^[^a-zA-Z0-9]+$")) {
            return "Contains only special characters";
        }

        return "Valid";
    }
}
