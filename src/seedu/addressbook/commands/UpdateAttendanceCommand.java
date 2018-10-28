package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList;

/**
 *  Marks the date where the student is present.
 */
public class UpdateAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Updates the attendance of a student. \n"
            + "Parameters: indexOfPerson d/dd-mm-yyyy att/attendance \n"
            + "\tExample: " + COMMAND_WORD + " " + "1 d/29-09-2018 att/1 \n"
            + "\tTo input today's date, input d/0";

    public static final String MESSAGE_SUCCESS = "Attendance updated for: ";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Attendance has already been taken.\n"
            + "Please use `replaceAtten` command to overwrite current attendance.";

    private boolean isPresent;
    private String date;

    // Constructor
    public UpdateAttendanceCommand(int targetIndex, String date, boolean isPresent) {
        super(targetIndex); // super is calling the constructor of the parent function
        this.date = date;
        this.isPresent = isPresent;
    }

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public UpdateAttendanceCommand() {
        // Does nothing
    }

    @Override
    public CommandResult execute() {
        try {
            Person person = addressBook.findPerson(getTargetPerson());
            boolean isDuplicateDate = person.updateAttendanceMethod(date, isPresent, false);
            if (isDuplicateDate) {
                return new CommandResult(String.format((MESSAGE_DUPLICATE_ATTENDANCE)));
            } else {
                return new CommandResult(String.format(MESSAGE_SUCCESS) + person.getName());
            }
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
