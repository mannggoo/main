package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "Description should not be blank.";
    public final String itemDateTime;

    public DateTime(String dt) {
        requireNonNull(dt);
        checkArgument(isValidDateTime(dt), MESSAGE_CONSTRAINTS);
        itemDateTime = dt;
    }

    public static boolean isValidDateTime(String dateTime) {
        return (!dateTime.isEmpty());
    }

    @Override
    public String toString() {
        return itemDateTime;
    }
}