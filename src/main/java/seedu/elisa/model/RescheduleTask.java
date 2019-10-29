package seedu.elisa.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.application.Platform;
import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.LogicManager;

/**
 * Task to reschedule an event.
 */
public class RescheduleTask extends TimerTask {
    public static LinkedList<RescheduleTask> allTasks = new LinkedList<>();
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private ItemModel model;
    private Item item;
    private Event event;
    private AutoReschedulePeriod period;

    public static void removeFromAllTask(Event event) {
        for (RescheduleTask task : RescheduleTask.allTasks) {
            if (task.getEvent().equals(event)) {
                task.cancel();
                RescheduleTask.allTasks.remove(task);
            }
        }
    }

    public RescheduleTask(Item item, AutoReschedulePeriod period, ItemModel model) {
        this.item = item;
        this.event = item.getEvent().get();
        this.period = period;
        this.model = model;
    }

    public LocalDateTime getStartTime() {
        return event.getStartDateTime();
    }

    public long getLongPeriod() {
        return period.getPeriod();
    }

    public Event getEvent() {
        return this.event;
    }

    /**
     * Executes this task.
     * Update this event's startDateTime with the new startDateTime, given the period of reccurence.
     * Update the model with the new item.
     * Refreshes the view on the Ui to reflect the new startDateTime of this event.
     */
    public void run() {
        logger.info("----------[INFO] " + "Reschedule runs again. Old event: " + event.toString());
        Item oldItem = item;
        long period = event.getPeriod().getPeriod();
        LocalDateTime newStart = LocalDateTime.now().plusNanos(Duration.ofMillis(period).toNanos());
        Event newEvent = event.changeStartDateTime(newStart);
        Item newItem = item.changeEvent(newEvent);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                model.replaceItem(oldItem, newItem);
                model.updateLists();
                model.setVisualizeList(model.getVisualList()); // to refresh the view
            }
        });

        this.item = newItem;
        this.event = newEvent;

        logger.info("-----------[INFO] " + "End of run. New event: " + newEvent.toString());
    }
}
