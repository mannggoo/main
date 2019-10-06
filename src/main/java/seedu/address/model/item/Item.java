package seedu.address.model.item;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import seedu.address.model.tag.Tag;

/**
 * A stub class for the items.
 */
public class Item {
    private String description;
    private Optional<Task> task;
    private Optional<Event> event;
    private Optional<Reminder> reminder;
    private Set<Tag> tags;

    public Item (String description, Optional<Task> task, Optional<Event> event, Optional<Reminder> reminder) {
        this(description, task, event, reminder, new TreeSet<>());
    }

    public Item (String description, Optional<Task> task, Optional<Event> event, Optional<Reminder> reminder,
                 Set<Tag> tag) {
        this.description = description;
        this.task = task;
        this.event = event;
        this.reminder = reminder;
        this.tags = tag;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isTask() {
        return this.task.isPresent();
    }

    public boolean isEvent() {
        return this.event.isPresent();
    }

    public boolean isReminder() {
        return this.reminder.isPresent();
    }

    public Task getTask() {
        return task.get();
    }

    public Event getEvent() {
        return event.get();
    }

    public Reminder getReminder() {
        return reminder.get();
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTask(Task task) {
        this.task = Optional.of(task);
    }

    public void setEvent(Event event) {
        this.event = Optional.of(event);
    }

    public void setReminder(Reminder reminder) {
        this.reminder = Optional.of(reminder);
    }

    /**
     * Converts the item object to a JSON string. The JSON string is then stored within the saving.
     * @return a JSON string representation of the object
     * @throws JsonProcessingException throws any exception to be handled in the main program
     */
    public String toJson() throws JsonProcessingException {
        String taskJson = isTask() ? task.get().toJson() : "null";
        String eventJson = isEvent() ? event.get().toJson() : "null";
        String reminderJson = isReminder() ? reminder.get().toJson() : "null";

        String result = String.format("{\"description\": \"%s\", \"task\": \"%s\", \"event\": \"%s\", "
                + "\"reminder\": \"%s\"", description, taskJson, eventJson, reminderJson);
        return result;
    }
}


