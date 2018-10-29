package seedu.addressbook.privilege.user;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.attendance.ViewAttendancePersonCommand;
import seedu.addressbook.commands.DeregisterExamCommand;
import seedu.addressbook.commands.RegisterExamCommand;
import seedu.addressbook.commands.attendance.ReplaceAttendanceCommand;
import seedu.addressbook.commands.attendance.UpdateAttendanceCommand;
import seedu.addressbook.commands.exams.AddExamCommand;
import seedu.addressbook.commands.exams.DeleteExamCommand;
import seedu.addressbook.commands.exams.EditExamCommand;
import seedu.addressbook.commands.exams.ExamsListCommand;
import seedu.addressbook.commands.person.ListAllCommand;
import seedu.addressbook.commands.person.ViewAllCommand;


/**
 * Represents a user with Tutor access
 * */
public class TutorUser extends BasicUser {
    private static List<Command> newAllowedCommand = Arrays.asList(
            new ViewAllCommand(),
            new ListAllCommand(),
            new AddExamCommand(),
            new ExamsListCommand(),
            new DeleteExamCommand(),
            new EditExamCommand(),
            new RegisterExamCommand(),
            new DeregisterExamCommand(),
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
