package seedu.addressbook.commands.assessment;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.AssignmentStatistics;
import seedu.addressbook.data.person.UniqueStatisticsList;

/**
 * Creates a new statistic in the statistics book.
 */
public class AddAssignmentStatistics extends Command {

    public static final String COMMAND_WORD = "addstatistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds new statistic to the StatisticsBook. "
            + "Statistics can be marked private by prepending 'p' to the prefix of the field.\n\t"
            + "Parameters: SUBJECTNAME [p]en/EXAMNAME [p]ts/TOP SCORER [p]av/AVERAGE [p]te/TOTAL EXAM TAKERS "
            + "[p]ab/TOTAL NUMBER ABSENT [p]tp/ TOTAL PASS [p]mm/MAX MIN\n\t"
            + "Example: " + COMMAND_WORD
            + " Mathematics en/Midterm ts/John Doe av/21.5 te/86 ab/4 tp/83 mm/35 98";

    public static final String MESSAGE_SUCCESS = "New statistic added : %1$s";
    public static final String MESSAGE_DUPLICATE_STATISTIC = "This statistic already exists in the statistics book!";

    private final AssignmentStatistics toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddAssignmentStatistics (String subjectName, String examName, String topScorer, String averageScore,
                             String totalExamTakers, String numberAbsent, String totalPass, String maxMin,
                             boolean isPrivate) throws IllegalValueException {
        this.toAdd = new AssignmentStatistics(subjectName, examName, topScorer, averageScore, totalExamTakers,
                numberAbsent, totalPass, maxMin, isPrivate);
    }

    public AssignmentStatistics getAssignmentStatistics() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            statisticsBook.addStatistic(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueStatisticsList.DuplicateStatisticsException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_STATISTIC);
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
