package seedu.addressbook.data.person;

import java.util.Objects;


/**
 * Represents student's values
 */
public class Grades implements Printable {

    public static final String GRADE_EXAMPLE = "27";

    private int value;

    /**
     * Validates given grades.
     * */

    public Grades(int grade) {
        this.value = grade;
    }

    @Override
    public String toString() {
        return " Grade: " + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grades // instanceof handles nulls
                && this.value == (((Grades) other).value)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(value);
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        return "Grade: " + value;
    }

    public int getValue() {
        return value;
    }

}
