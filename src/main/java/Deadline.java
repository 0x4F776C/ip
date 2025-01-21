public class Deadline extends Task {
    protected String by;

    public Deadline(String input) {
        super(input.substring(9, input.indexOf("/by")).trim()); // get description
        this.by = input.substring(input.indexOf("/by") + 4).trim(); // get deadline
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}