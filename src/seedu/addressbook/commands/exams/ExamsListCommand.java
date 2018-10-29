package seedu.addressbook.commands.exams;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.commands.commandresult.ListType;
import seedu.addressbook.data.person.ReadOnlyExam;

/**
 * Lists all persons in the address book to the user.
 */
public class ExamsListCommand extends Command {

    public static final String COMMAND_WORD = "examslist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all exams in the exam book as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        List<ReadOnlyExam> allExams = examBook.getAllExam().immutableListView();
        return new CommandResult(getMessageForExamListShownSummary(allExams), allExams, ListType.EXAMS);
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
