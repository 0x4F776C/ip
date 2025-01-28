package org.trashbot.tasks;

public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String input) {
        super(input.substring(6, input.indexOf("/from")).trim()); // get description
        String timeInfo = input.substring(input.indexOf("/from")); // get time - overall
        this.from = timeInfo.substring(6, timeInfo.indexOf("/to")).trim(); // get from
        this.to = timeInfo.substring(timeInfo.indexOf("/to") + 4).trim(); // get to
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFrom() + " to: " + getTo() + ")";
    }
}
