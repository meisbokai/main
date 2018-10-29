package seedu.addressbook.commands;

import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

/**
 * Shows the non-private exams of the person identified using the last displayed index.
 */
public class ViewExamsCommand extends Command {

    public static final String COMMAND_WORD = "viewexams";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Shows the non-private exams of the person identified by the index number "
            + "used in the last person listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_EXAMS_PERSON_SUCCESS = "Name: %1$s";

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public ViewExamsCommand() {
    }

    public ViewExamsCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            addressBook.findPerson(target);
            return new CommandResult(String.format(MESSAGE_VIEW_EXAMS_PERSON_SUCCESS, target.getAsTextShowExam()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    @Override
    public Category getCategory() {
        return Category.EXAM;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
