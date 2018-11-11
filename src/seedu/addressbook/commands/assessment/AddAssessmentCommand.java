package seedu.addressbook.commands.assessment;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.commands.commandresult.ListType;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.UniqueAssessmentsList;

/**
 * Adds a person to the address book.
 */
public class AddAssessmentCommand extends Command {

    public static final String COMMAND_WORD = "addassess";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds an assessment to the address book. "
            + "Parameters: EXAM_NAME ...\n\t"
            + "Example: " + COMMAND_WORD
            + " Math Midterm";

    public static final String MESSAGE_SUCCESS = "New assessment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSESSMENT = "This assessment has already been entered!";

    private final Assessment toAdd;

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public AddAssessmentCommand() {
        this.toAdd = null;
    }

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddAssessmentCommand(String examName) throws IllegalValueException {
        this.toAdd = new Assessment(examName);
    }

    public AddAssessmentCommand(Assessment toAdd) {
        this.toAdd = toAdd;
    }

    public Assessment getAssessment() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.addAssessment(toAdd);
            final List<Assessment> updatedList = addressBook.getAllAssessments().immutableListView();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), updatedList, ListType.ASSESSMENT);
        } catch (UniqueAssessmentsList.DuplicateAssessmentException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_ASSESSMENT);
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public Category getCategory() {
        return Category.ASSESSMENT;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
