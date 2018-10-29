package seedu.addressbook.commands.assessment;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
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

    private final int assessIndex;

    /**
     * Constructor used for Assessments
     * Command constructed has no functionality
     * */
    public DeleteAssessmentCommand() {
        assessIndex = -1;
    }

    public DeleteAssessmentCommand(int targetVisibleIndex) {
        assessIndex = targetVisibleIndex;
    }

    @Override
    public CommandResult execute() {
        try {
            final Assessment target = getTargetAssessment(assessIndex);
            addressBook.removeAssessment(target);
            return new CommandResult(String.format(MESSAGE_DELETE_ASSESSMENT_SUCCESS, target));
        } catch (AssessmentIndexOutOfBoundsException aie) {
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
