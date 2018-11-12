package seedu.addressbook.commands.fees;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.commands.commandformat.indexformat.IndexFormatCommand;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.formatter.PersonListFormat;

/**
 * Removes the due fees from a respective person
 */
public class PaidFeesCommand extends IndexFormatCommand {
    public static final String COMMAND_WORD = "paidfees";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Removes the existing fees of a person "
            + "in the address book.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Fees paid: %1$s";
    public static final String MESSAGE_NO_FEES = "%1s does not have any due Fees.";

    @Override
    public CommandResult execute() {
        try {
            try {
                Person person = getTargetPerson();
                if (person.getFees().isEdited()) {
                    person.setFees(new Fees());
                    Set<Tag> temp = new HashSet<>();
                    temp = person.getTags();
                    for (Tag t : temp) {
                        if ("feesdue".equals(t.tagName)) {
                            temp.remove(t);
                        }
                    }
                    person.setTags(temp);
                    List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
                    return new CommandResult(String.format(MESSAGE_SUCCESS, person.getAsTextShowFee()), allPersons,
                            PersonListFormat.ALL_PUBLIC_DETAILS);
                } else {
                    return new CommandResult(String.format(MESSAGE_NO_FEES, person.getAsTextShowOnlyName()));
                }
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
