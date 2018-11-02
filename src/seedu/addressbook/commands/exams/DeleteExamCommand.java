package seedu.addressbook.commands.exams;

import seedu.addressbook.commands.commandformat.indexformat.IndexFormatCommand;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.UniqueExamList.ExamNotFoundException;

/**
 * Deletes a exam identified using its last displayed index from the exam book.
 */
public class DeleteExamCommand extends IndexFormatCommand {

    public static final String COMMAND_WORD = "deleteexam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the exam identified by the index number in the last exams listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXAM_SUCCESS = "Deleted %1$s";

    private int targetExamIndex;

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public DeleteExamCommand() {
    }

    public DeleteExamCommand(int targetExamIndex) {
        this.targetExamIndex = targetExamIndex;
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyExam target = getTargetReadOnlyExam(targetExamIndex);
            examBook.removeExam(target);
            addressBook.removeExam(target);
            return new CommandResult(String.format(MESSAGE_DELETE_EXAM_SUCCESS, target));
        } catch (ExamIndexOutOfBoundsException eie) {
            return new CommandResult(Messages.MESSAGE_INVALID_EXAM_DISPLAYED_INDEX);
        } catch (ExamNotFoundException enfe) {
            return new CommandResult(Messages.MESSAGE_EXAM_NOT_IN_EXAMBOOK);
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public boolean isExamMutating() {
        return true;
    }

    @Override
    public Category getCategory() {
        return Category.EXAM;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
