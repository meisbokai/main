package seedu.addressbook.commands;

import java.util.List;

import seedu.addressbook.data.person.Assessment;

/**
 * Lists all assessments in the address book to the user.
 */
public class ListAssessmentCommand extends Command {

    public static final String COMMAND_WORD = "listassess";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all assessments in the address book as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        List<Assessment> allAssessments = addressBook.getAllAssessments().immutableListView();
        return new CommandResult(getMessageForAssessmentListShownSummary(allAssessments), allAssessments,
                null);
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
