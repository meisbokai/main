package seedu.addressbook.commands.fees;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.commands.commandformat.indexformat.IndexFormatCommand;
import seedu.addressbook.commands.commandformat.indexformat.ObjectTargeted;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.tag.Tag;

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
            Person person = getTargetPerson();
            person.setFees(fees);
            if ("0.00".equals(fees.value)) {
                Set<Tag> temp = new HashSet<>();
                temp = person.getTags();
                for (Tag t : temp) {
                    if ("feesdue".equals(t.tagName)) {
                        temp.remove(t);
                    }
                }
                person.setTags(temp);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, person.getAsTextShowFee()));
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
    public Category getCategory() {
        return Category.FEES;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }

}
