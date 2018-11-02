package seedu.addressbook.commands.assessment;

import seedu.addressbook.commands.commandformat.indexformat.IndexFormatCommand;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Shows grades of the person identified using the last displayed index.
 */
public class ViewGradesCommand extends IndexFormatCommand {

    public static final String COMMAND_WORD = "viewgrades";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows the grades of a person identified by the "
            + "index number in the last person listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_GRADES_DETAILS = "Viewing grades: %1$s";

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetReadOnlyPerson();
            if (!addressBook.containsPerson(target)) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
            return new CommandResult(String.format(MESSAGE_VIEW_GRADES_DETAILS, target.getAsTextShowAssess()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public Category getCategory() {
        return Category.ASSESSMENT;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
