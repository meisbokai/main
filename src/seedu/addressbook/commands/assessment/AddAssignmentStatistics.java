package seedu.addressbook.commands.assessment;

import java.util.List;
import java.util.Map;

import seedu.addressbook.commands.commandformat.indexformat.IndexFormatCommand;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.commands.commandresult.ListType;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.AssignmentStatistics;
import seedu.addressbook.data.person.Grades;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniqueStatisticsList;

/**
 * Creates a new statistic in the statistics book.
 */
public class AddAssignmentStatistics extends IndexFormatCommand {

    public static final String COMMAND_WORD = "addstatistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds new statistic to the StatisticsBook based "
            + "on index of assessment, as per the last listing of listassessment.\n\t"
            + "Parameters: EXAMNAME\n\t"
            + "Example: " + COMMAND_WORD
            + " 1";

    public static final String MESSAGE_SUCCESS = "New statistic added : %1$s";
    public static final String MESSAGE_DUPLICATE_STATISTIC = "This statistic already exists in the statistics book!";

    private AssignmentStatistics toAdd;

    private String examName;
    private double averageScore;
    private int totalExamTakers;
    private double maxScore;
    private double minScore;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddAssignmentStatistics () {
        examName = null;
        averageScore = -1;
        totalExamTakers = -1;
        maxScore = -1;
        minScore = -1;
        toAdd = null;
    }

    public AssignmentStatistics getAssignmentStatistics() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            final Map<Person, Grades> grade;
            Assessment assessName = getTargetAssessment();
            examName = assessName.getExamName();
            double maxGrade = 0;
            double minGrade = 1000000;
            double total = 0;
            int count = 0;
            grade = assessName.getAllGrades();
            for (Grades gradeVal : grade.values()) {
                if (gradeVal.getValue() > maxGrade) {
                    maxGrade = gradeVal.getValue();
                }
                if (gradeVal.getValue() < minGrade) {
                    minGrade = gradeVal.getValue();
                }
                total = total + (gradeVal.getValue());
                count = count + 1;
            }
            if (count > 0) {
                averageScore = (double) Math.round((total / count) * 100) / 100;
            } else {
                averageScore = 0; //to account for empty grades
            }
            totalExamTakers = count;
            maxScore = maxGrade;
            if (minGrade == 1000000) { //to account for empty grades
                minScore = 0;
            } else {
                minScore = minGrade;
            }
            this.toAdd = new AssignmentStatistics(examName, averageScore, totalExamTakers, maxScore, minScore);
            statisticsBook.addStatistic(toAdd);
            final List<AssignmentStatistics> updatedList = statisticsBook.getAllStatistics().immutableListView();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), updatedList,
                    ListType.STATISTICS);
        } catch (UniqueStatisticsList.DuplicateStatisticsException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_STATISTIC);
        } catch (AssessmentIndexOutOfBoundsException aie) {
            return new CommandResult(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX);
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
