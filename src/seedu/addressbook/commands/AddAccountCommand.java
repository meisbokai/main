package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.account.Account;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class AddAccountCommand extends Command {

    public static final String COMMAND_WORD = "addacc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Adds an account as specified to the person identified by the "
            + "index number used in the last person listing.\n\t"
            + "Parameters: INDEX USERNAME PASSWORD PRIVILEGE\n\t"
            + "PRIVILEGE could be of type \"Basic\", \"Tutor\", \"Admin\".\n\t"
            + "Example: " + COMMAND_WORD + " 1 username password basic";

    public static final String MESSAGE_ADD_ACCOUNT_PERSON_SUCCESS = "Added Account to: %1$s";
    public static final String MESSAGE_USERNAME_TAKEN = "Username Taken!";
    public static final String MESSAGE_PERSON_HAS_ACCOUNT = "Target person already has an existing account!!";

    private final Account toAdd;

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public AddAccountCommand() {
        //TODO: Fix potato code
        toAdd = null;
    }
    public AddAccountCommand(int targetVisibleIndex, String username, String password, String privilege)
            throws IllegalValueException {
        super(targetVisibleIndex);
        toAdd = new Account(username, password, privilege);
    }

    @Override
    public CommandResult execute() {
        try {
            if (addressBook.containsPersonWithUsername(toAdd.getUsername())) {
                return new CommandResult(MESSAGE_USERNAME_TAKEN);
            }

            final ReadOnlyPerson target = getTargetPerson();
            if (target.getAccount().isPresent()) {
                return new CommandResult(MESSAGE_PERSON_HAS_ACCOUNT);
            }

            final Person editablePerson = addressBook.findPerson(target);
            toAdd.setPrivilegePerson(editablePerson);
            editablePerson.setAccount(toAdd);
            return new CommandResult(String.format(MESSAGE_ADD_ACCOUNT_PERSON_SUCCESS, target.getName()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
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
