package seedu.addressbook.commands.assessment;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Shows grades of the person identified using the last displayed index.
 */
public class ViewGradesCommand extends Command {

    public static final String COMMAND_WORD = "viewgrades";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows the grades of a person identified by the "
            + "index number in the last person listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_GRADES_DETAILS = "Viewing grades: %1$s";

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public ViewGradesCommand() { }

    public ViewGradesCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

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
        return Category.PERSON;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
