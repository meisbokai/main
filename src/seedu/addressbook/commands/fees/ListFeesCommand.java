package seedu.addressbook.commands.fees;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;

/**
 * Lists all persons in the address book to the user.
 */
public class ListFeesCommand extends Command {

    public static final String COMMAND_WORD = "listfees";
    //no more by index but instead show the whole fees List
    // TODO: sorted by date
    // TODO: their indexed numbers in list

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all names and fees in the address book.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_LISTFEE_PERSON_SUCCESS = "%1$s";

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public ListFeesCommand() {
    }

    public ListFeesCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetReadOnlyPerson();
            final String result = addressBook.loopFeesPerson(target);
            return new CommandResult(String.format(MESSAGE_LISTFEE_PERSON_SUCCESS, result));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
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
