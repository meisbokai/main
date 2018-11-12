//@@author meisbokai
package seedu.addressbook.commands.attendance;

import static seedu.addressbook.common.Messages.MESSAGE_DATE_CONSTRAINTS;
import static seedu.addressbook.common.Utils.isValidDate;

import seedu.addressbook.commands.commandformat.indexformat.IndexFormatCommand;
import seedu.addressbook.commands.commandformat.indexformat.ObjectTargeted;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList;

/**
 *  Marks the date where the student is present.
 */
public class UpdateAttendanceCommand extends IndexFormatCommand {

    public static final String COMMAND_WORD = "attendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Updates the attendance of a student. \n"
            + "Parameters: INDEX d/DATE att/ISPRESENT \n"
            + "\tExample: " + COMMAND_WORD + " " + "1 d/29-09-2018 att/1 \n"
            + "\tTo input today's date, input d/0";

    public static final String MESSAGE_SUCCESS = "Attendance updated for: ";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Attendance has already been taken.\n"
            + "Please use `replaceAtten` command to overwrite current attendance.";

    private boolean isPresent;
    private String date;

    // Constructor
    public UpdateAttendanceCommand(int targetIndex, String date, boolean isPresent) throws IllegalValueException {
        setTargetIndex(targetIndex, ObjectTargeted.PERSON);
        if (!isValidDate(date) && !"0".equals(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
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
                return new CommandResult(MESSAGE_DUPLICATE_ATTENDANCE);
            } else {
                return new CommandResult(MESSAGE_SUCCESS + person.getName());
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

    @Override
    public Category getCategory() {
        return Category.ATTENDANCE;
    }
}
