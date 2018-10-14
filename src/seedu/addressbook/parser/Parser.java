package seedu.addressbook.parser;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.addressbook.commands.AddAssignmentStatistics;
import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.AddFeesCommand;
import seedu.addressbook.commands.ChangePasswordCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CreateExamCommand;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.FindCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.ListCommand;
import seedu.addressbook.commands.RaisePrivilegeCommand;
import seedu.addressbook.commands.SayCommand;
import seedu.addressbook.commands.UpdateAttendanceCommand;
import seedu.addressbook.commands.ViewAllCommand;
import seedu.addressbook.commands.ViewAttendanceCommand;
import seedu.addressbook.commands.ViewCommand;
import seedu.addressbook.commands.ViewFeesCommand;
import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Parses user input.
 */
public class Parser {

    public static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    public static final Pattern SINGLE_KEYWORD_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+\\s*)"); // one keyword separated by whitespace

    public static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
                    + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
                    + " (?<isAddressPrivate>p?)a/(?<address>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    public static final Pattern EXAM_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<subjectName>[^/]+)"
                    + " (?<isExamPrivate>p?)n/(?<examName>[^/]+)"
                    + " d/(?<examDate>[^/]+)"
                    + " st/(?<examStartTime>[^/]+)"
                    + " et/(?<examEndTime>[^/]+)"
                    + " dt/(?<examDetails>[^/]+)");
    public static final Pattern FEES_DATA_ARGS_FORMAT =
            Pattern.compile("(?<index>[^/]+)"
                    + " (?<fees>[^/]+)");
    public static final Pattern STATISTICS_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<subjectName>[^/]+)"
                    + " (?<isExamPrivate>p?)en/(?<examName>[^/]+)"
                    + " ts/(?<topScorer>[^/]+)"
                    + " av/(?<averageScore>[^/]+)"
                    + " te/(?<totalExamTakers>[^/]+)"
                    + " ab/(?<numberAbsent>[^/]+)"
                    + " tp/(?<totalPass>[^/]+)"
                    + " mm/(?<maxMin>[^/]+)");
    public static final Pattern ATTENDANCE_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<targetIndex>.+)"
                    + " d/(?<date>[^/]+)"
                    + " att/(?<isPresent>[0-1])");

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Signals that the user input could not be parsed.
     */
    public static class ParseException extends Exception {
        ParseException(String message) {
            super(message);
        }

    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case AddFeesCommand.COMMAND_WORD:
            return prepareFees(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ViewCommand.COMMAND_WORD:
            return prepareView(arguments);

        case ViewAllCommand.COMMAND_WORD:
            return prepareViewAll(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case SayCommand.COMMAND_WORD:
            return new SayCommand();

        case RaisePrivilegeCommand.COMMAND_WORD:
            return prepareRaisePrivilege(arguments);

        case ChangePasswordCommand.COMMAND_WORD:
            return prepareChangePassword(arguments);

        case CreateExamCommand.COMMAND_WORD:
            return prepareCreateExam(arguments);
        case ViewFeesCommand.COMMAND_WORD:
            return prepareViewFees(arguments);
        case AddAssignmentStatistics.COMMAND_WORD:
            return prepareAddStatistics(arguments);

        case UpdateAttendanceCommand.COMMAND_WORD:
            return prepareUpdateAttendance(arguments);

        case ViewAttendanceCommand.COMMAND_WORD:
            return prepareViewAttendance(arguments);

        case HelpCommand.COMMAND_WORD: // Fallthrough
        default:
            return new HelpCommand();
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),

                    matcher.group("phone"),
                    isPrivatePrefixPresent(matcher.group("isPhonePrivate")),

                    matcher.group("email"),
                    isPrivatePrefixPresent(matcher.group("isEmailPrivate")),

                    matcher.group("address"),
                    isPrivatePrefixPresent(matcher.group("isAddressPrivate")),

                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Checks whether the private prefix of a contact detail in the add command's arguments string is present.
     */
    private static boolean isPrivatePrefixPresent(String matchedPrefix) {
        return "p".equals(matchedPrefix);
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the AddFeesCommand command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFees(String args) {
        final Matcher matcher = FEES_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(matcher.group("index"));
            return new AddFeesCommand(
                    targetIndex,
                    matcher.group("fees")
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }


    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new DeleteCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the viewFees command.
     */
    private Command prepareViewFees(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new ViewFeesCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewFeesCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the view command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareView(String args) {

        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new ViewCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the view all command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareViewAll(String args) {

        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new ViewAllCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAllCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given arguments string as a single index number.
     *
     * @param args arguments string to parse as index number
     * @return the parsed index number
     * @throws ParseException if no region of the args string could be found for the index
     * @throws NumberFormatException the args string region is not a valid number
     */
    private int parseArgsAsDisplayedIndex(String args) throws ParseException, NumberFormatException {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Could not find index number to parse");
        }
        return parseInt(matcher.group("targetIndex"));
    }

    /**
     * Parse the given arguments string as a float

    private float parseArgsAsFloat(String args) throws ParseException, NumberFormatException {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Could not find float number to parse");
        }
        return Float.parseFloat(matcher.group("targetIndex"));
    }*/

    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

    /**
     * Parses arguments in the context of the RaisePrivilege command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareRaisePrivilege(String args) {

        final Matcher matcher = SINGLE_KEYWORD_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RaisePrivilegeCommand.MESSAGE_USAGE));
        }

        final String password = matcher.group("keywords");

        return new RaisePrivilegeCommand(password);
    }

    /**
     * Parses arguments in the context of the RaisePrivilege command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareChangePassword(String args) {
        // TODO Change the regex to match only 2 words
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        try {
            return new ChangePasswordCommand(keywords);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the createexam exam command.
     */
    private Command prepareCreateExam(String args) {
        final Matcher matcher = EXAM_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateExamCommand.MESSAGE_USAGE));
        }
        try {
            return new CreateExamCommand(
                    matcher.group("subjectName"),
                    matcher.group("examName"),
                    matcher.group("examDate"),
                    matcher.group("examStartTime"),
                    matcher.group("examEndTime"),
                    matcher.group("examDetails"),
                    isPrivatePrefixPresent(matcher.group("isExamPrivate"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the add assignment statistics command.
     */
    private Command prepareAddStatistics(String args) {
        final Matcher matcher = STATISTICS_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAssignmentStatistics.MESSAGE_USAGE));
        }
        try {
            return new AddAssignmentStatistics(
                    matcher.group("subjectName"),
                    matcher.group("examName"),
                    matcher.group("topScorer"),
                    matcher.group("averageScore"),
                    matcher.group("totalExamTakers"),
                    matcher.group("numberAbsent"),
                    matcher.group("totalPass"),
                    matcher.group("maxMin"),
                    isPrivatePrefixPresent(matcher.group("isExamPrivate"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the update Attendance command.
     */
    private Command prepareUpdateAttendance(String args) {

        final Matcher matcher = ATTENDANCE_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateAttendanceCommand.MESSAGE_USAGE));
        }
        try {
            final int targetIndex = parseInt(matcher.group("targetIndex"));
            final int isPresent = parseInt(matcher.group("isPresent"));
            final boolean isPresent_b;
            if ((matcher.group("isPresent")).equals("1")) isPresent_b = true;
            else isPresent_b = false;

            return new UpdateAttendanceCommand(
                    targetIndex,
                    matcher.group("date"),
                    isPresent_b);
        } catch (NumberFormatException nfe) { //do the most specific catch on top
            return new IncorrectCommand(nfe.getMessage());
        }

    }

    /**
     * Parses arguments in the context of the view attendance command.
     */
    private Command prepareViewAttendance(String args) {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAttendanceCommand.MESSAGE_USAGE));
        }

        try {
            final int targetIndex = parseInt(matcher.group("targetIndex"));
            return new ViewAttendanceCommand(targetIndex);
        } catch (NumberFormatException nfe) { //do the most specific catch on top
            return new IncorrectCommand(nfe.getMessage());
        }
    }
}
