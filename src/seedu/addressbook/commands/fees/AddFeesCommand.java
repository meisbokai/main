package seedu.addressbook.commands.fees;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

/**
 * Adds fees to a respective person
 */
public class AddFeesCommand extends Command {

    public static final String COMMAND_WORD = "addfees";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds fees to an existing person "
            + "in the address book. Fees are automatically marked as private.\n\t"
            + "Parameters: INDEX FEES DUEDATE\n\t"
            + "Example: " + COMMAND_WORD + " 1 3264.90 22-12-2018";

    public static final String MESSAGE_SUCCESS = "Fees updated: %1$s";

    private Fees fees;

    /**
     * Use a constructor to update the fees values in AddressBook.
     */
    public AddFeesCommand(int index, String fees, String date) throws IllegalValueException {
        super(index);
        this.fees = new Fees(fees, date);
    }

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public AddFeesCommand() {
        // Does nothing
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetReadOnlyPerson();
            Person person = addressBook.findPerson(target);
            person.setFees(fees);
            return new CommandResult(String.format(MESSAGE_SUCCESS, target.getAsTextShowFee()));
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    /**
     * Checks if the command can potentially change the data to be stored
     */
    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }

}
