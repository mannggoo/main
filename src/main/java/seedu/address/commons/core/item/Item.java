package seedu.address.commons.core.item;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.tag.Tag;

/**
 * Represents an Item in Elisa.
 * Guarantees: ItemDescription is present and not null.
 * At least one of the following three fields (Task, Event, Reminder) is present and not null.
 */
public class Item {

    // Identity fields
    private final Task task;
    private final Event event;
    private final Reminder reminder;
    // Data fields
    private final ItemDescription itemDescription;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    private Item(Task task, Event event, Reminder reminder,
                 ItemDescription itemDescription, Set<Tag> tags) {
        requireNonNull(itemDescription);
        this.task = task;
        this.event = event;
        this.reminder = reminder;
        this.itemDescription = itemDescription;
        this.tags.addAll(tags);
    }

    public boolean hasTask() {
        return task != null;
    }

    public boolean hasEvent() {
        return event != null;
    }

    public boolean hasReminder() {
        return reminder != null;
    }

    public Optional<Task> getTask() {
        if (this.task == null) {
            return Optional.empty();
        } else {
            return Optional.of(this.task);
        }
    }

    public Optional<Event> getEvent() {
        if (this.event == null) {
            return Optional.empty();
        } else {
            return Optional.of(this.event);
        }
    }

    public Optional<Reminder> getReminder() {
        if (this.reminder == null) {
            return Optional.empty();
        } else {
            return Optional.of(this.reminder);
        }
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    /**
     * Change ItemDescription
     */
    public Item changeItemDescription(ItemDescription newItemDescription) {
        return new ItemBuilder().setItemDescription(newItemDescription)
                .setTask(task)
                .setEvent(event)
                .setReminder(reminder)
                .setTags(tags)
                .build();
    }

    /**
     * Change Task referenced
     */
    public Item changeTask(Task newTask) {
        return new ItemBuilder().setItemDescription(itemDescription)
                .setTask(newTask)
                .setEvent(event)
                .setReminder(reminder)
                .setTags(tags)
                .build();
    }

    /**
     * Change Event referenced
     */
    public Item changeEvent(Event newEvent) {
        return new ItemBuilder().setItemDescription(itemDescription)
                .setTask(task)
                .setEvent(newEvent)
                .setReminder(reminder)
                .setTags(tags)
                .build();
    }

    /**
     * Change Reminder referenced
     */
    public Item changeReminder(Reminder newReminder) {
        return new ItemBuilder().setItemDescription(itemDescription)
                .setTask(task)
                .setEvent(event)
                .setReminder(newReminder)
                .setTags(tags)
                .build();
    }

    /**
     * Change Tags referenced
     */
    public Item changeTags(HashSet<Tag> newTags) {
        return new ItemBuilder().setItemDescription(itemDescription)
                .setTask(task)
                .setEvent(event)
                .setReminder(reminder)
                .setTags(newTags)
                .build();
    }

    /**
     * Returns true if both items have the same task referenced.
     * This defines a weaker notion of equality between two items.
     */
    public boolean hasSameTask(Item otherItem) {
        return getTask().equals(otherItem.getTask());
    }

    /**
     * Returns true if both items have the same Event referenced.
     * This defines a weaker notion of equality between two items.
     */
    public boolean hasSameEvent(Item otherItem) {
        return getEvent().equals(otherItem.getEvent());
    }

    /**
     * Returns true if both items have the same Reminder referenced.
     * This defines a weaker notion of equality between two items.
     */
    public boolean hasSameReminder(Item otherItem) {
        return getReminder().equals(otherItem.getReminder());
    }

    /**
     * Returns true if both items have the same identity and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;

        return otherItem.getTask().equals(getTask())
                && otherItem.getReminder().equals(getReminder())
                && otherItem.getEvent().equals(getEvent())
                && otherItem.getItemDescription().equals(getItemDescription())
                && otherItem.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(task, event, reminder, itemDescription, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nDescription: ")
                .append(itemDescription.toString());

        if (getTask().isPresent()) {
            builder.append("\n\nTask Details: ")
                    .append(getTask().get().toString());
        }

        if (getEvent().isPresent()) {
            builder.append("\n\nEvent Details: ")
                    .append(getEvent().get().toString());
        }

        if (getReminder().isPresent()) {
            builder.append("\n\nReminder Details: ")
                    .append(getReminder().get().toString());
        }

        if (tags.size() > 0) {
            builder.append("\nTags: ");
            getTags().forEach(builder::append);
        }

        return builder.toString();
    }

    /**
     * Builder class for Item.
     */
    public static class ItemBuilder {

        // Identity fields
        private Task task = null;
        private Event event = null;
        private Reminder reminder = null;

        // Data fields
        private ItemDescription itemDescription = null;
        private Set<Tag> tags = new HashSet<>();

        public ItemBuilder() {}

        public ItemBuilder setTask(Task task) {
            requireNonNull(task);
            this.task = task;
            return this;
        }

        public ItemBuilder setEvent(Event event) {
            requireNonNull(event);
            this.event = event;
            return this;
        }

        public ItemBuilder setReminder(Reminder reminder) {
            requireNonNull(reminder);
            this.reminder = reminder;
            return this;
        }

        public ItemBuilder setItemDescription(ItemDescription descriptor) {
            requireNonNull(descriptor);
            this.itemDescription = descriptor;
            return this;
        }

        //Consider using a defensive copy of tags, similar to EditCommand in AB3
        public ItemBuilder setTags(Set<Tag> tags) {
            requireNonNull(tags);
            this.tags = tags;
            return this;
        }

        /**Validates arguments of Item before initialising it
         *
         * @return A valid Item.
         * @throws IllegalArgumentException If description not provided or task, event and reminder fields are null.
         */
        public Item build() throws IllegalArgumentException {
            Item newItem = new Item(this.task, this.event, this.reminder, this.itemDescription, this.tags);

            //Validation of parameters of object after object has been created.
            //Validate after object has been created as per StackOverflow link
            //https://stackoverflow.com/questions/38173274/builder-pattern-validation-effective-java
            //However this seems to be contrary to the answer in the following link
            //https://stackoverflow.com/questions/12930852/clearing-doubts-about-the-builder-pattern
            //However it seems safer to follow the first as the object fields could be mutated after it has been copied
            // from the builder to the object.
            if (newItem.getItemDescription() == null) {
                throw new IllegalArgumentException("Description must be provided!");
            }
            if (newItem.getTask() == null && newItem.getEvent() == null && newItem.getReminder() == null) {
                throw new IllegalArgumentException("Task, Event & Reminder cannot all be empty!");
            }

            //Resetting all constructing parameters back to null, so a new object doesn't use the parameters of the
            // previous object.
            task = null;
            event = null;
            reminder = null;
            itemDescription = null;
            tags = null;

            return newItem;
        }

    }

    /**
     * Converts the item object into a json string.
     * @return string representation of the item
     * @throws JsonProcessingException when the item cannot be converted into a JSON string
     */
    public String toJson() throws JsonProcessingException {
        return JsonUtil.toJsonString(this);
    }

    /**
     * Creates an item object from a JSON string.
     * @param jsonString the JSON string that represents the item
     * @return the item object that is created
     * @throws IOException when the jsonString is not in JSON format
     * @throws IllegalValueException when the JSON string contains incorrect value
     */
    public static Item fromJson(String jsonString) throws IOException, NullPointerException {
        JsonNode node = JsonUtil.getObjectMapper().readTree(jsonString);
        ItemBuilder temp = new ItemBuilder();

        String itemDescriptionString = node.get("itemDescription").toString();
        ItemDescription id = ItemDescription.fromJson(itemDescriptionString);
        temp.setItemDescription(id);

        if (node.hasNonNull("task")) {
            String taskString = node.get("task").toString();
            Task t = Task.fromJson(taskString);
            temp = temp.setTask(t);
        }

        if (node.hasNonNull("event")) {
            String eventString = node.get("event").toString();
            Event e = Event.fromJson(eventString);
            temp = temp.setEvent(e);
        }

        if (node.hasNonNull("reminder")) {
            String reminderString = node.get("reminder").toString();
            Reminder r = Reminder.fromJson(reminderString);
            temp = temp.setReminder(r);
        }

        Set<Tag> tagsSet = new HashSet<>();
        JsonNode tags = node.get("tags");
        Iterator<JsonNode> it = tags.elements();
        while (it.hasNext()) {
            tagsSet.add(new Tag(it.next().get("tagName").asText()));
        }

        return temp.setTags(tagsSet).build();
    }

    public Item deepCopy() throws IOException {
        return Item.fromJson(this.toJson());
    }

}
