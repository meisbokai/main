package seedu.addressbook.privilege.user;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.AddExamCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.DeleteExamCommand;
import seedu.addressbook.commands.EditExamCommand;
import seedu.addressbook.commands.ExamsListCommand;
import seedu.addressbook.commands.ReplaceAttendanceCommand;
import seedu.addressbook.commands.UpdateAttendanceCommand;
import seedu.addressbook.commands.ViewAttendancePersonCommand;


/**
 * Represents a user with Tutor access
 * */
public class TutorUser extends BasicUser {
    private static List<Command> newAllowedCommand = Arrays.asList(
            new AddCommand(),
            new DeleteCommand(),
            new AddExamCommand(),
            new ExamsListCommand(),
            new DeleteExamCommand(),
            new EditExamCommand(),
            new UpdateAttendanceCommand(),
            new ReplaceAttendanceCommand(),
            new ViewAttendancePersonCommand()
    );

    public TutorUser() {
        super();
        addAllowedCommands(newAllowedCommand);
        setCurrentLevel(PrivilegeLevel.Tutor);
        sortCommands();
    }
}
