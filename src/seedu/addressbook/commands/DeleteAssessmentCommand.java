package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.UniqueAssessmentsList.AssessmentNotFoundException;

/**
 * Deletes an assessment identified using its last displayed index from the address book.
 */
public class DeleteAssessmentCommand extends Command {

    public static final String COMMAND_WORD = "deleteassess";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the assessment identified by the index number used in the last assessment listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ASSESSMENT_SUCCESS = "Deleted Assessment: %1$s";

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public DeleteAssessmentCommand() {
    }

    public DeleteAssessmentCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        try {
            final Assessment target = getTargetAssessment();
            addressBook.removeAssessment(target);
            return new CommandResult(String.format(MESSAGE_DELETE_ASSESSMENT_SUCCESS, target));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX);
        } catch (AssessmentNotFoundException nfe) {
            return new CommandResult(Messages.MESSAGE_ASSESSMENT_NOT_IN_ADDRESSBOOK);
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
