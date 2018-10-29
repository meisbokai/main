package seedu.addressbook.data.person;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents an assessment of the student
 */
public class Assessment {

    public static final String EXAM_NAME_EXAMPLE = "CG2271 Midterms";
    public static final String MESSAGE_ASSESSMENT_CONSTRAINTS = "Assessment name can contain alpha-numeric characters";
    public static final String ASSESSMENT_VALIDATION_REGEX = "^.*$";

    private String examName;
    private Map<Person, Grades> grade;

    /**
     * Validates given results.
     *
     * @throws IllegalValueException if given results string is invalid.
     */
    public Assessment(String examName) throws IllegalValueException {
        if (!isValidAssessment(examName)) {
            throw new IllegalValueException(MESSAGE_ASSESSMENT_CONSTRAINTS);
        }
        this.examName = examName.trim();
        this.grade = new HashMap<>();
    }

    /**
     * Returns true if a given string is a valid assessment.
     */
    public static boolean isValidAssessment(String test) {
        return test.matches(ASSESSMENT_VALIDATION_REGEX);
    }

    public Grades getGrade(ReadOnlyPerson person) {
        return this.grade.get(person);
    }

    public void addGrade(Person person, Grades grades) {
        grade.put(person, grades);
    }

    public String getExamName() {
        return examName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(examName);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

    public String getPrintableAssessmentString(boolean showPrivate) {
        return examName;
    }

    /**
     * Formats the assessment as text to show all.
     */
    public String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String stringChain = getPrintableAssessmentString(true);
        builder.append(stringChain);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Assessment // instanceof handles nulls
                && this.examName.equals(((Assessment) other).examName)); // state check
    }
}
