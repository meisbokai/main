package seedu.addressbook.data.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents an assessment of the student
 */
public class Assessment {

    public static final String EXAM_NAME_EXAMPLE = "CG2271 Midterms";

    private String examName;
    private List<Grades> grade;

    /**
     * Validates given results.
     *
     * @throws IllegalValueException if given results string is invalid.
     */
    public Assessment(String examName) throws IllegalValueException {
        this.examName = examName.trim();
        this.grade = new ArrayList<>();
    }

    public List<Grades> getGrade() {
        return new ArrayList<>(grade);
    }

    public void setGrade(Grades grade) {
        this.grade.add(grade);
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
