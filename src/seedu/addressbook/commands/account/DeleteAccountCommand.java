package seedu.addressbook.commands.account;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.privilege.Privilege.SelfTargetingException;

/**
 * Deletes the account of a person identified using it's last displayed index from the address book.
 */
public class DeleteAccountCommand extends Command {

    public static final String COMMAND_WORD = "delacc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the account of the person identified by the index number used in the last person listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ACCOUNT_PERSON_SUCCESS = "Deleted Account of: %1$s";
    public static final String MESSAGE_PERSON_ACCOUNT_ABSENT = "Target person does not have an existing account!";
    public static final String MESSAGE_DELETING_SELF = "You cannot delete the account your are logged-in as!";

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetReadOnlyPerson();
            final Person editablePerson = addressBook.findPerson(target);

            if (!target.hasAccount()) {
                return new CommandResult(MESSAGE_PERSON_ACCOUNT_ABSENT);
            }
            privilege.checkTargetIsSelf(editablePerson);
            editablePerson.removeAccount();
            final List<ReadOnlyPerson> updatedList = addressBook.getAllPersons().immutableListView();

            return new CommandResult(String.format(MESSAGE_DELETE_ACCOUNT_PERSON_SUCCESS, target.getName()),
                    updatedList);
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        } catch (SelfTargetingException ste) {
            return new CommandResult(MESSAGE_DELETING_SELF);
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public Category getCategory() {
        return Category.ACCOUNT;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
