package seedu.addressbook.commands.attendance;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.details.Name;

import java.util.List;

/**
 *  Lists down the people who were present on the particular date.
 */
public class ViewAttendanceDateCommand extends Command {

    public static final String COMMAND_WORD = "viewAttenDate";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Views the attendance of the date. \n"
            + "Parameters: date \n"
            + "\tExample: " + COMMAND_WORD + " " + "28-10-2018";

    public static final String MESSAGE_SUCCESS = "Attendance for the given date, ";

    private String date;

    // Constructor
    public ViewAttendanceDateCommand(String date) {
        this.date = date;
    }

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public ViewAttendanceDateCommand() {
        // Does nothing
    }

    @Override
    public CommandResult execute() {
        try {
            String output = "";
            final List<Name> listOfPresent = addressBook.getPresentPeople(date);
            for (Name n: listOfPresent) {
                output += (n + "\n");
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS) + date + ":\n" + output);

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
