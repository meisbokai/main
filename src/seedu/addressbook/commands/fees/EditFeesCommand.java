package seedu.addressbook.commands.fees;

import static seedu.addressbook.common.Messages.MESSAGE_DATE_CONSTRAINTS;
import static seedu.addressbook.common.Messages.MESSAGE_FEES_VALUE_CONSTRAINTS;
import static seedu.addressbook.common.Utils.isValidDate;

import seedu.addressbook.commands.commandformat.indexformat.IndexFormatCommand;
import seedu.addressbook.commands.commandformat.indexformat.ObjectTargeted;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

/**
 * Adds fees to a respective person
 */
public class EditFeesCommand extends IndexFormatCommand {
    public static final String COMMAND_WORD = "editfees";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Edits the fees of an existing person "
            + "in the address book. Fees are automatically marked as private.\n\t"
            + "Parameters: INDEX FEES DUEDATE\n\t"
            + "Example: " + COMMAND_WORD + " 1 3264.90 22-12-2018";

    public static final String MESSAGE_SUCCESS = "Fees updated: %1$s";

    private Fees fees;

    /**
     * Use a constructor to update the fees values in AddressBook.
     */
    public EditFeesCommand(int index, String fees, String date) throws IllegalValueException {
        setTargetIndex(index, ObjectTargeted.PERSON);
        if (!isValidDate(date) && !"0".equals(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if ("0.00".equals(fees)) {
            throw new IllegalValueException(MESSAGE_FEES_VALUE_CONSTRAINTS);
        }
        fees = fees.replaceFirst ("^0*", "");
        this.fees = new Fees(fees, date);
    }

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public EditFeesCommand() {
        // Does nothing
    }

    @Override
    public CommandResult execute() {
        try {
            try {
                Person person = getTargetPerson();
                person.setFees(fees);
                return new CommandResult(String.format(MESSAGE_SUCCESS, person.getAsTextShowFee()));
            } catch (PersonNotFoundException pnfe) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
    public Category getCategory() {
        return Category.FEES;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }

}
