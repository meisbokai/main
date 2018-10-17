package seedu.addressbook.commands;

import java.util.List;
import java.util.Optional;

import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.AssignmentStatistics;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** The feedback message to be shown to the user. Contains a description of the execution result */
    public final String feedbackToUser;

    /** The list of persons that was produced by the command */
    private final List<? extends ReadOnlyPerson> relevantPersons;

    /** The list of exams that was produced by the command */
    private final List<? extends ReadOnlyExam> relevantExams;

    /** The list of exams that was produced by the command */
    private final List<? extends Assessment> relevantAssessments;

    /** The list of exams that was produced by the command */
    private final List<? extends AssignmentStatistics> relevantStatistics;


    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = null;
        this.relevantExams = null;
        this.relevantAssessments = null;
        this.relevantStatistics = null;
    }

    public CommandResult(String feedbackToUser, List<? extends ReadOnlyPerson> relevantPersons) {
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = relevantPersons;
        this.relevantExams = null;
        this.relevantAssessments = null;
        this.relevantStatistics = null;
    }

    public CommandResult(String feedbackToUser, List<? extends Assessment> relevantAssessments,
                         List<? extends ReadOnlyPerson> relevantPersons) {
        this.feedbackToUser = feedbackToUser;
        this.relevantAssessments = relevantAssessments;
        this.relevantPersons = relevantPersons;
        this.relevantExams = null;
        this.relevantStatistics = null;
    }

    public CommandResult(List<? extends ReadOnlyExam> relevantExams, String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        this.relevantExams = relevantExams;
        this.relevantAssessments = null;
        this.relevantStatistics = null;
        this.relevantPersons = null;
    }

    public CommandResult(List<? extends ReadOnlyExam> relevantExams, String feedbackToUser,
                         List<? extends AssignmentStatistics> relevantStatistics) {
        this.feedbackToUser = feedbackToUser;
        this.relevantExams = relevantExams;
        this.relevantStatistics = relevantStatistics;
        this.relevantAssessments = null;
        this.relevantPersons = null;
    }

    public CommandResult(List<? extends ReadOnlyExam> relevantExams, String feedbackToUser,
                         List<? extends AssignmentStatistics> relevantStatistics,
                         List<? extends Assessment> relevantAssessments) {
        this.feedbackToUser = feedbackToUser;
        this.relevantExams = relevantExams;
        this.relevantStatistics = relevantStatistics;
        this.relevantAssessments = relevantAssessments;
        this.relevantPersons = null;
    }

    /**
     * Returns list of persons relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyPerson>> getRelevantPersons() {
        return Optional.ofNullable(relevantPersons);
    }

    /**
     * Returns list of exams relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyExam>> getRelevantExams() {
        return Optional.ofNullable(relevantExams);
    }

    /**
     * Returns list of statistics relevant to the command command result, if any.
     */
    public Optional<List<? extends AssignmentStatistics>> getRelevantStatistics() {
        return Optional.ofNullable(relevantStatistics);
    }

    /**
     * Returns list of assessments relevant to the command command result, if any.
     */
    public Optional<List<? extends Assessment>> getRelevantAssessments() {
        return Optional.ofNullable(relevantAssessments);
    }
}
