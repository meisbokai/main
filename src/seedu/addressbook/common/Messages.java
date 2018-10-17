package seedu.addressbook.common;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid."
            + "Run the list/find command to generate a list of person.";
    public static final String MESSAGE_PERSON_NOT_IN_ADDRESSBOOK = "Person could not be found in address book!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE = "Launch command format: "
            + "java seedu.addressbook.Main [STORAGE_FILE_PATH]";
    public static final String MESSAGE_WELCOME = "Welcome to your ClassRepo!";
    public static final String MESSAGE_USING_STORAGE_FILE = "Using storage file : %1$s";
    public static final String MESSAGE_INSUFFICIENT_PRIVILEGE = "Insufficient Privilege.\n"
            + "Requires privilege level of %s, but current level is %s";
    public static final String MESSAGE_NOT_LOGGED_IN = "Unable to process command as user is not logged in.\n";
    public static final String MESSAGE_WRONG_NUMBER_ARGUMENTS =
            "Wrong number of arguments, expected %d, actual %d\n %s";
    public static final String MESSAGE_USING_EXAMS_FILE = "Using exams file : %1$s";
    public static final String MESSAGE_EXAMS_LISTED_OVERVIEW = "%1$d exams listed!";
    public static final String MESSAGE_USING_STATISTICS_FILE = "Using statistics file : %1$s";
    public static final String MESSAGE_EXAM_NOT_IN_EXAMBOOK = "Exam could not be found in exam book!";
    public static final String MESSAGE_INVALID_EXAM_DISPLAYED_INDEX = "The exam index provided is invalid. "
            + "Run the examslist command to generate a list of exams.";
    public static final String MESSAGE_NO_ARGS_FOUND = "No arguments found!\n";

    public static final String MESSAGE_INVALID_DATE = "Input date is invalid\n";
    public static final String MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX = "The assessment index provided is invalid."
            + "Run the list/find command to generate a list of assessments.";
    public static final String MESSAGE_ASSESSMENT_NOT_IN_ADDRESSBOOK = "Assessment could not be found in address book!";
    public static final String MESSAGE_ASSESSMENTS_LISTED_OVERVIEW = "%1$d assessments listed!";
}
