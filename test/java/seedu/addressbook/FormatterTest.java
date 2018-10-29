package seedu.addressbook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.addressbook.common.Pair;
import seedu.addressbook.data.account.Account;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.formatter.Formatter;
import seedu.addressbook.formatter.PersonListFormat;

public class FormatterTest {
    /** A platform independent line separator. */
    private static final String NEWLINE = System.lineSeparator();
    private List<Pair<List<Person>, String>> inputToExpectedOutput = new ArrayList<>();

    @Test
    public void formatStringChain() {
        assertEquals(String.format(" A%s B%<s C%<s", NEWLINE),
                Formatter.format("A", "B", "C"));
        assertEquals(String.format(" A%s B%<s C%<s D%<s", NEWLINE),
                Formatter.format("A", "B\nC", "D"));
    }

    @Test
    public void formatPersonsAllDetailsFormat() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        assertEmptyListShowsEmpty(PersonListFormat.ALL_PUBLIC_DETAILS);
        final String personFormat = " %1$d. Person %1$d%4$s"
                + " Phone: %1$d%4$s"
                + " Email: %1$d@email%4$s"
                + " Address: House of %1$d%4$s"
                + " Tags: [tag%2$d][tag%3$d]%4$s";
        addInputToExpectedOutput(helper.generatePersonList(false),
                String.format(personFormat + " %4$s %4$s", 1, 1, 2, NEWLINE));

        //List of 3 people, all fields non-private
        addInputToExpectedOutput(helper.generatePersonList(false, false , false),
                String.format(personFormat + " %4$s", 1, 1, 2, NEWLINE)
                        + String.format(personFormat + " %4$s", 2, 2, 3, NEWLINE)
                        + String.format(personFormat + " %4$s %4$s", 3, 4, 3, NEWLINE));
        //List of 3 people, number 2 is is private
        final String person2Format = " %1$d. Person %1$d%4$s"
                + " Tags: [tag%2$d][tag%3$d]%4$s";
        addInputToExpectedOutput(helper.generatePersonList(false, true , false),
                String.format(personFormat + " %4$s", 1, 1, 2, NEWLINE)
                        + String.format(person2Format + " %4$s", 2, 2, 3, NEWLINE)
                        + String.format(personFormat + " %4$s %4$s", 3, 4, 3, NEWLINE));

        assertFormatterBehaviour(PersonListFormat.ALL_PUBLIC_DETAILS);
    }

    @Test
    public void formatPersonsOnlyNameFormat() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        assertEmptyListShowsEmpty(PersonListFormat.NAMES_ONLY);

        final String personFormat = " %d. Person %<d%s";
        addInputToExpectedOutput(helper.generatePersonList(false),
                String.format(personFormat + " %<s", 1, NEWLINE));

        //List of 3 people, all fields non-private
        addInputToExpectedOutput(helper.generatePersonList(false, false , false),
                String.format(personFormat, 1, NEWLINE)
                        + String.format(personFormat, 2, NEWLINE)
                        + String.format(personFormat + " %<s", 3, NEWLINE));

        //List of 3 people, all fields non-private*
        addInputToExpectedOutput(helper.generatePersonList(false, true , false),
                String.format(personFormat, 1, NEWLINE)
                        + String.format(personFormat, 2, NEWLINE)
                        + String.format(personFormat + " %<s", 3, NEWLINE));

        assertFormatterBehaviour(PersonListFormat.NAMES_ONLY);
    }

    @Test
    public void formatPersonsAccountFormat() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        Person p3 = helper.generatePerson(3, false);
        List<Person> persons = List.of(p1, p2, p3);

        //List of 3 people, all without accounts
        String expected = String.format(" 1. Person 1%s"
                + " 2. Person 2%<s"
                + " 3. Person 3%<s"
                + " %<s", NEWLINE);
        assertEquals(expected, Formatter.format(persons, PersonListFormat.ACCOUNT_DETAILS));
        p1.setAccount(new Account("user1", "pw", "Basic"));
        p2.setAccount(new Account("user2", "pw", "Tutor"));
        p3.setAccount(new Account("user3", "pw", "Admin"));

        //List of 3 people, all with accounts
        expected = String.format(" 1. Person 1 User Type: Basic%s"
                + " 2. Person 2 User Type: Tutor%<s"
                + " 3. Person 3 User Type: Admin%<s"
                + " %<s", NEWLINE);
        assertEquals(expected, Formatter.format(persons, PersonListFormat.ACCOUNT_DETAILS));
    }

    @Test
    public void formatAllExam() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Exam> exams = helper.generateExamList(false, true);
        final String examFormat = " %1$d. %2$sExam: Exam %1$d Subject %1$d 01-02-2018 10:00 12:00 Held in %1$d 0"
                + NEWLINE;
        String expected = String.format(examFormat, 1, "") + String.format(examFormat, 2, "private ") + " " + NEWLINE;
        assertEquals(expected, Formatter.formatExam(exams));
    }

    private void assertEmptyListShowsEmpty(PersonListFormat personListFormat) {
        assertEquals(" " + NEWLINE, Formatter.format(Collections.emptyList(), personListFormat));
    }

    private void addInputToExpectedOutput(List<Person> input, String output) {
        inputToExpectedOutput.add(new Pair<>(input, output));
    }

    /**
     * Asserts formatter turns input list in inputToExpectedOutput to its expected output
     * Also tests for empty list
     */
    private void assertFormatterBehaviour(PersonListFormat personListFormat) {
        assertEmptyListShowsEmpty(personListFormat);

        for (Pair<List<Person>, String> inputToOutput: inputToExpectedOutput) {
            final String actual = Formatter.format(inputToOutput.getFirst(), personListFormat);
            final String expected = inputToOutput.getSecond();
            assertEquals(expected, actual);
        }
    }
}
