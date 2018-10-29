package seedu.addressbook.commands.privilege;

import seedu.addressbook.commands.commandformat.KeywordsFormatCommand;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.logic.Logic;

/**
 * Raises the current privilege to Admin
 * Checks if the password supplied is correct
 */
public class RaisePrivilegeCommand extends KeywordsFormatCommand {

    public static final String COMMAND_WORD = "raise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Raises your privilege level to Admin, requires master password.\n\t"
            + "Parameters: PASSWORD\n\t"
            + "Example: " + COMMAND_WORD + " default_password";

    public static final String MESSAGE_WRONG_PASSWORD = "Wrong password entered";
    public static final String MESSAGE_SUCCESS = "Privilege changed to %1$s";
    private static final int REQUIRED_ARGUMENTS = 1;

    private String password;

    private void validatePassword() throws Logic.WrongPasswordEnteredException {
        if (!password.equals(addressBook.getMasterPassword())) {
            throw new Logic.WrongPasswordEnteredException();
        }
    }

    @Override
    public CommandResult execute() {
        try {
            validatePassword();
            privilege.raiseToAdmin();
            return new CommandResult(String.format(MESSAGE_SUCCESS, privilege.getLevelAsString()));
        } catch (Logic.WrongPasswordEnteredException wpe) {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        }
    }

    @Override
    public void setUp(String[] arguments) {
        assert(arguments.length == REQUIRED_ARGUMENTS);
        this.password = arguments[0];
    }

    @Override
    public int getNumRequiredArg() {
        return REQUIRED_ARGUMENTS;
    }

    @Override
    public Category getCategory() {
        return Category.PRIVILEGE;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }

}
