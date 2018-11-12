package seedu.addressbook.privilege.user;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.assessment.AddAssessmentCommand;
import seedu.addressbook.commands.assessment.AddAssignmentStatistics;
import seedu.addressbook.commands.assessment.AddGradesCommand;
import seedu.addressbook.commands.assessment.DeleteAssessmentCommand;
import seedu.addressbook.commands.assessment.DeleteGradesCommand;
import seedu.addressbook.commands.assessment.DeleteStatisticsCommand;
import seedu.addressbook.commands.assessment.ViewGradesCommand;
import seedu.addressbook.commands.attendance.ReplaceAttendanceCommand;
import seedu.addressbook.commands.attendance.UpdateAttendanceCommand;
import seedu.addressbook.commands.attendance.ViewAttendanceDateCommand;
import seedu.addressbook.commands.attendance.ViewAttendancePersonCommand;
import seedu.addressbook.commands.exams.DeregisterExamCommand;
import seedu.addressbook.commands.exams.ListExamsCommand;
import seedu.addressbook.commands.exams.RegisterExamCommand;
import seedu.addressbook.commands.fees.ListDueFeesCommand;
import seedu.addressbook.commands.fees.ListFeesCommand;
import seedu.addressbook.commands.fees.ViewFeesCommand;
import seedu.addressbook.commands.person.ListAllCommand;
import seedu.addressbook.commands.person.ViewAllCommand;

/**
 * Represents a user with Tutor access
 * */
public class TutorUser extends BasicUser {
    private static List<Command> newAllowedCommand = Arrays.asList(
            new ViewAllCommand(),
            new ListAllCommand(),
            new ListExamsCommand(),
            new RegisterExamCommand(),
            new DeregisterExamCommand(),
            new UpdateAttendanceCommand(),
            new ReplaceAttendanceCommand(),
            new ViewAttendancePersonCommand(),
            new ViewAttendanceDateCommand(),
            new ListFeesCommand(),
            new ListDueFeesCommand(),
            new AddAssessmentCommand(),
            new DeleteAssessmentCommand(),
            new AddGradesCommand(),
            new ViewGradesCommand(),
            new DeleteGradesCommand(),
            new ViewFeesCommand(),
            new AddAssignmentStatistics(),
            new DeleteStatisticsCommand()
    );

    public TutorUser() {
        super();
        addAllowedCommands(newAllowedCommand);
        setCurrentLevel(PrivilegeLevel.Tutor);
        sortCommands();
    }
}
