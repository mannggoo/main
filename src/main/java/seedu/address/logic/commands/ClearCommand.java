package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.ItemModel;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Item list has been cleared!";


    @Override
    public CommandResult execute(ItemModel model) {
        requireNonNull(model);
        model.clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
