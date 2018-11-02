package seedu.addressbook.commands.fees;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.formatter.PersonListFormat;

/**
 * Lists all persons in the address book to the user.
 */
public class ListDueFeesCommand extends Command {

    public static final String COMMAND_WORD = "listdue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all the names and fees of people with fees due with respect to today's date.\n\t"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        String date = java.time.LocalDate.now().toString();
        return new CommandResult(getMessageForFeesListShownSummary(addressBook.dueFeesPerson(date)),
                addressBook.dueFeesPerson(date), PersonListFormat.FEES_DUE_DETAILS);
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
