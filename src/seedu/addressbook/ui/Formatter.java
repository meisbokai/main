package seedu.addressbook.ui;

import java.util.ArrayList;
import java.util.List;

import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.Printable;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Used for formatting text for display. e.g. for adding text decorations.
 */
public class Formatter {

    /** A decorative prefix added to the beginning of lines printed by AddressBook */
    private static final String LINE_PREFIX = " ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();


    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";


    /** Offset required to convert between 1-indexing and 0-indexing.  */
    private static final int DISPLAYED_INDEX_OFFSET = 1;


    /** Formats the given strings for displaying to the user. */
    public String format(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String m : messages) {
            sb.append(LINE_PREFIX + m.replace("\n", LS + LINE_PREFIX) + LS);
        }
        return sb.toString();
    }

    /** Formats the given list of persons for displaying to the user. */
    public String format(List<? extends ReadOnlyPerson> persons) {
        final List<String> formattedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : persons) {
            formattedPersons.add(person.getAsTextHidePrivate());
        }
        return format(asIndexedList(formattedPersons));
    }

    /** Formats a list of strings as an indexed list. */
    private static String asIndexedList(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as an indexed list item.
     *
     * @param visibleIndex index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

    /**
     * Returns a concatenated version of the printable strings of each object.
     */
    public static String getPrintableString(boolean showPrivate, Printable... printables) {
        String stringChain = "";
        for (Printable i: printables) {
            stringChain += i.getPrintableString(showPrivate) + " ";
        }
        return stringChain;
    }

    public static String getPrintableField(boolean showPrivate, boolean isPrivate, String fieldLabel, String value) {
        if (isPrivate) {
            if (showPrivate) {
                return String.format("{private %s: %s}", fieldLabel, value);
            } else {
                return "";
            }
        }
        return String.format("%s: %s", fieldLabel, value);
    }

    /** Formats the given list of exams for displaying to the admin user. */
    public String formatExam(List<? extends ReadOnlyExam> exams) {
        final List<String> formattedExams = new ArrayList<>();
        for (ReadOnlyExam exam : exams) {
            formattedExams.add(exam.getAsTextShowAll());

        }
        return format(asIndexedList(formattedExams));
    }

    /** Formats the given list of exams for displaying to the admin user. */
    public String formatAssessments(List<? extends Assessment> assessments) {
        final List<String> formattedAssessments = new ArrayList<>();
        for (Assessment assessment : assessments) {
            formattedAssessments.add(assessment.getAsTextShowAll());

        }
        return format(asIndexedList(formattedAssessments));
    }
}
