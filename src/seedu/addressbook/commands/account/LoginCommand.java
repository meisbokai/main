package seedu.addressbook.commands.account;

import static seedu.addressbook.common.Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK;

import seedu.addressbook.commands.commandformat.KeywordsFormatCommand;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.data.account.Account;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.logic.Logic;

/**
 * Changes the master password to a new one.
 * Checks that the old password given is correct.
 */
public class LoginCommand extends KeywordsFormatCommand {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Logs into your account. Raises current privilege to that of your account.\n\t "
            + "Parameters: USERNAME PASSWORD\n\t"
            + "Example: " + COMMAND_WORD + " IamSudo sudo1234";

    public static final String MESSAGE_SUCCESS = "Logged in as : %s (%s)";
    public static final String MESSAGE_WRONG_PASSWORD = "Wrong password entered";

    private static final int REQUIRED_ARGUMENTS = 2;

    private String userName;
    private String password;

    private void validatePassword(Account account) throws Logic.WrongPasswordEnteredException {
        if (!password.equals(account.getPassword())) {
            throw new Logic.WrongPasswordEnteredException();
        }
    }

    @Override
    public void setUp(String[] arguments) {
        assert(arguments.length == REQUIRED_ARGUMENTS);
        this.userName = arguments[0];
        this.password = arguments[1];
    }

    @Override
    public int getNumRequiredArg () {
        return REQUIRED_ARGUMENTS;
    }

    @Override
    public CommandResult execute() {
        try {
            Person requestedPerson = addressBook.findPersonByUsername(userName);
            final Account requestedAccount = requestedPerson.getAccount().get();
            validatePassword(requestedAccount);
            privilege.copyPrivilege(requestedAccount.getPrivilege());
            final String message = String.format(MESSAGE_SUCCESS,
                    requestedPerson.getName().toString(),
                    requestedAccount.getPrivilege().getLevelAsString());
            return new CommandResult(message);
        } catch (Logic.WrongPasswordEnteredException wpe) {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        } catch (UniquePersonList.PersonNotFoundException pnf) {
            return new CommandResult(MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
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
