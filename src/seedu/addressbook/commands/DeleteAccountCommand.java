package seedu.addressbook.commands;

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

    public DeleteAccountCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public DeleteAccountCommand() {}

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            final Person editablePerson = addressBook.findPerson(target);

            if (!target.getAccount().isPresent()) {
                return new CommandResult(MESSAGE_PERSON_ACCOUNT_ABSENT);
            }
            privilege.checkTargetIsSelf(editablePerson);
            editablePerson.removeAccount();
            return new CommandResult(String.format(MESSAGE_DELETE_ACCOUNT_PERSON_SUCCESS, target.getName()));
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
