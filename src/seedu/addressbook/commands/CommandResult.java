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
    private static final String BLANK_MESSAGE = "";

    /** The list of exams that was produced by the command */
    private final List<? extends ReadOnlyExam> relevantExams;

    /** The list of exams that was produced by the command */
    private final List<? extends Assessment> relevantAssessments;

    /** The list of exams that was produced by the command */
    private final List<? extends AssignmentStatistics> relevantStatistics;

    /** The list of persons that was produced by the command */
    private List<? extends ReadOnlyPerson> relevantPersons;

    /** The output message to be shown to the user. Contains a description of the execution result */
    private String outputConsoleMessage;

    /** The status message to be shown to the user. Contains a description of the execution result */
    private String statusConsoleMessage;

    /** Enum to indicate on which console the message should be shown*/
    public enum MessageType { OUTPUT, STATUS }

    public CommandResult(String statusConsoleMessage) {
        this(statusConsoleMessage, MessageType.STATUS);
    }

    public CommandResult(String message, MessageType messageType) {
        if (messageType.equals(MessageType.STATUS)) {
            statusConsoleMessage = message;
        } else if (messageType.equals(MessageType.OUTPUT)) {
            outputConsoleMessage = message;
        }
        relevantExams = null;
        relevantStatistics = null;
        relevantAssessments = null;
    }

    public CommandResult(String statusConsoleMessage,
                         String outputConsoleMessage) {
        this.statusConsoleMessage = statusConsoleMessage;

        this.outputConsoleMessage = outputConsoleMessage;
        relevantExams = null;
        relevantStatistics = null;
        relevantAssessments = null;
    }

    public CommandResult(String statusConsoleMessage, List<? extends ReadOnlyPerson> relevantPersons) {
        this.statusConsoleMessage = statusConsoleMessage;
        this.relevantPersons = relevantPersons;
        this.relevantExams = null;
        this.relevantAssessments = null;
        this.relevantStatistics = null;
    }

    public CommandResult(String statusConsoleMessage, List<? extends Assessment> relevantAssessments,
                         List<? extends ReadOnlyPerson> relevantPersons) {
        this.statusConsoleMessage = statusConsoleMessage;
        this.relevantAssessments = relevantAssessments;
        this.relevantPersons = relevantPersons;
        this.relevantExams = null;
        this.relevantStatistics = null;
    }

    public CommandResult(List<? extends ReadOnlyExam> relevantExams, String statusConsoleMessage) {
        this.statusConsoleMessage = statusConsoleMessage;
        this.relevantExams = relevantExams;
        this.relevantAssessments = null;
        this.relevantStatistics = null;
    }

    public CommandResult(List<? extends ReadOnlyExam> relevantExams, String statusConsoleMessage,
                         List<? extends AssignmentStatistics> relevantStatistics) {
        this.statusConsoleMessage = statusConsoleMessage;
        this.relevantExams = relevantExams;
        this.relevantStatistics = relevantStatistics;
        this.relevantAssessments = null;
    }

    public CommandResult(List<? extends ReadOnlyExam> relevantExams, String statusConsoleMessage,
                         List<? extends AssignmentStatistics> relevantStatistics,
                         List<? extends Assessment> relevantAssessments) {
        this.statusConsoleMessage = statusConsoleMessage;
        this.relevantExams = relevantExams;
        this.relevantStatistics = relevantStatistics;
        this.relevantAssessments = relevantAssessments;
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

    public boolean hasStatusMessage() {
        return Optional.ofNullable(statusConsoleMessage).isPresent();
    }

    /** Returns if this command results prints to output console*/
    public boolean hasOutputMessage() {
        return Optional.ofNullable(outputConsoleMessage).isPresent()
                || getRelevantPersons().isPresent()
                || getRelevantExams().isPresent()
                || getRelevantAssessments().isPresent()
                || getRelevantStatistics().isPresent();
    }
    public String getOutputConsoleMessage() {
        return Optional.ofNullable(outputConsoleMessage).orElse(BLANK_MESSAGE);
    }

    public String getStatusConsoleMessage() {
        return Optional.ofNullable(statusConsoleMessage).orElse(BLANK_MESSAGE);
    }
}
