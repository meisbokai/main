package seedu.addressbook.commands;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

import java.util.List;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.privilege.Privilege;

/**
 * Represents an executable command.
 */
public abstract class Command {
    /**
     * Enum used to indicate which category the command belongs to
     * */
    public enum Category {
        PERSON,
        PRIVILEGE,
        GENERAL,
        ACCOUNT,
        ASSESSMENT,
        EXAM
    }

    protected static Privilege privilege;
    protected AddressBook addressBook;
    protected ExamBook examBook;
    protected StatisticsBook statisticsBook;
    protected List<? extends ReadOnlyPerson> relevantPersons;
    protected List<? extends Assessment> relevantAssessments;
    protected List<? extends ReadOnlyExam> relevantExams;
    private int targetIndex = -1;

    /**
     * @param targetIndex last visible listing index of the target person
     */
    public Command(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    protected Command() {
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param personsDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, personsDisplayed.size());
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of assessments.
     *
     * @param assessmentsDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForAssessmentListShownSummary(List<? extends Assessment> assessmentsDisplayed) {
        return String.format(Messages.MESSAGE_ASSESSMENTS_LISTED_OVERVIEW, assessmentsDisplayed.size());
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of exams.
     *
     * @param examsDisplayed used to generate summary
     * @return summary message for exams displayed
     */
    public static String getMessageForExamListShownSummary(List<? extends ReadOnlyExam> examsDisplayed) {
        return String.format(Messages.MESSAGE_EXAMS_LISTED_OVERVIEW, examsDisplayed.size());
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of fees.
     *
     * @param personsDisplayed used to generate summary
     * @return summary message for fees displayed
     */
    public static String getMessageForFeesListShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, personsDisplayed.size());
    }

    /**
     * Executes the command and returns the result.
     */
    public abstract CommandResult execute();

    /**
     * Supplies the data the command will operate on.
     */
    public void setData(AddressBook addressBook, StatisticsBook statisticsBook, List<? extends ReadOnlyPerson>
            relevantPersons, Privilege privilege) {
        this.addressBook = addressBook;
        this.statisticsBook = statisticsBook;
        this.relevantPersons = relevantPersons;
        this.privilege = privilege;
    }

    public void setData(AddressBook addressBook, List<? extends ReadOnlyPerson> relevantPersons,
                        List<? extends ReadOnlyExam> relevantExams, List<? extends Assessment> relevantAssessments,
                        Privilege privilege, ExamBook exambook, StatisticsBook statisticsBook) {
        setData(addressBook, statisticsBook, relevantPersons, privilege);
        this.examBook = exambook;
        this.relevantExams = relevantExams;
        this.relevantAssessments = relevantAssessments;
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyPerson getTargetPerson() throws IndexOutOfBoundsException {
        return relevantPersons.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected Assessment getTargetAssessment() throws IndexOutOfBoundsException {
        return relevantAssessments.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }


    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    //TODO: Fix potato code
    public Category getCategory() {
        return Category.GENERAL;
    }

    /**
     * Checks if the command can potentially change the data to be stored
     */
    public boolean isMutating() {
        return false;
    }

    /**
     * Returns the usage message to be used to construct HelpCommand's message
     */
    public abstract String getCommandUsageMessage();

    /**
     * Extracts the the target exams in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    public ReadOnlyExam getTargetExam() throws IndexOutOfBoundsException {
        return relevantExams.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

}
