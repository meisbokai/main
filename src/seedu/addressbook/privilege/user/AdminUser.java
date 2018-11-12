package seedu.addressbook.privilege.user;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.account.AddAccountCommand;
import seedu.addressbook.commands.account.DeleteAccountCommand;
import seedu.addressbook.commands.exams.AddExamCommand;
import seedu.addressbook.commands.exams.ClearExamsCommand;
import seedu.addressbook.commands.exams.DeleteExamCommand;
import seedu.addressbook.commands.exams.EditExamCommand;
import seedu.addressbook.commands.fees.EditFeesCommand;
import seedu.addressbook.commands.fees.PaidFeesCommand;
import seedu.addressbook.commands.person.AddCommand;
import seedu.addressbook.commands.person.ClearCommand;
import seedu.addressbook.commands.person.DeleteCommand;
import seedu.addressbook.commands.privilege.EditPasswordCommand;
import seedu.addressbook.commands.privilege.SetPermanentAdminCommand;

/**
 * Represents a user with Admin access
 * */
public class AdminUser extends TutorUser {
    private static List<Command> newAllowedCommand = Arrays.asList(
            new AddCommand(),
            new DeleteCommand(),
            new ClearCommand(),
            new EditPasswordCommand(),
            new ClearExamsCommand(),
            new EditFeesCommand(),
            new PaidFeesCommand(),
            new SetPermanentAdminCommand(),
            new AddAccountCommand(),
            new DeleteAccountCommand(),
            new AddExamCommand(),
            new DeleteExamCommand(),
            new EditExamCommand()
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
