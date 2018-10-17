package seedu.addressbook.data.person;

import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;
/**
 * Represents student's grade.s
 */
public class Grades implements Printable {

    public static final String GRADE_EXAMPLE = "27";

    public static final String GRADE_CONSTRAINTS = "May include up to decimal places only";
    public static final String GRADE_VALIDATION_REGEX = "\\d+(\\.\\d+)?";

    private String grade;
    //private Person person;

    /**
     * Validates given results.
     *
     * @throws IllegalValueException if given results string is invalid.
     */
    public Grades(String grade) throws IllegalValueException {

        String trimmedMarksScored = grade.trim();
        if (!isValidMarksScored(trimmedMarksScored)) {
            throw new IllegalValueException(GRADE_CONSTRAINTS);
        }
        // Assign grade with score
        this.grade = trimmedMarksScored;
    }

    /**
     * Checks if a given string is a valid marks scored.
     */
    public static boolean isValidMarksScored(String value) {
        return value.matches(GRADE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return " Grade: " + grade;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grades // instanceof handles nulls
                && this.grade.equals(((Grades) other).grade)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(grade);
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        return "Grade: " + grade;
    }

    public String getMarksScored() {
        return grade;
    }

}
