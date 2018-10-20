package seedu.addressbook.privilege.user;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.commands.AddAccountCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.ClearExamsCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.DeleteAccountCommand;
import seedu.addressbook.commands.EditPasswordCommand;
import seedu.addressbook.commands.SetPermanentAdminCommand;
import seedu.addressbook.commands.ViewAllCommand;

/**
 * Represents a user with Admin access
 * */
public class AdminUser extends TutorUser {
    private static List<Command> newAllowedCommand = Arrays.asList(
            new ClearCommand(),
            new ViewAllCommand(),
            new EditPasswordCommand(),
            new ClearExamsCommand(),
            new EditPasswordCommand(),
            new SetPermanentAdminCommand(),
            new AddAccountCommand(),
            new DeleteAccountCommand()
    );

    public AdminUser() {
        super();
        addAllowedCommands(newAllowedCommand);
        setCurrentLevel(PrivilegeLevel.Admin);
        sortCommands();
    }

    /**
     * Overrides the check with always be true to allow compatibility with "new" commands that were
     * unaccounted for (ie commands made by teammates)
     * */
    @Override
    public boolean isAllowedCommand(Command command) {
        return true;
    }
}
