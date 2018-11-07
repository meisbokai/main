package seedu.addressbook.data.person;

import seedu.addressbook.formatter.Formatter;

/**
 * A read-only immutable interface for an Exam in the exam book.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyExam {

    String getExamName();
    String getSubjectName();
    String getExamDate();
    String getExamStartTime();
    String getExamEndTime();
    String getExamDetails();
    int getTakers();
    boolean isPrivate();

    /**
     * Formats the exam as text to show all.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String stringChain = Formatter.getPrintableExam(getExamName(), getSubjectName(), getExamDate(),
                getExamStartTime(), getExamEndTime(), getExamDetails(), getTakers(), isPrivate());
        builder.append(stringChain);
        return builder.toString();
    }

    /**
     * Returns true if the values inside this object is same as those of the other
     * Does not include details or takers
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyExam other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getSubjectName().equals(this.getSubjectName()) // state checks here onwards
                && other.getExamName().equals(this.getExamName())
                && other.getExamDate().equals(this.getExamDate())
                && other.getExamStartTime().equals(this.getExamStartTime())
                && other.getExamEndTime().equals(this.getExamEndTime())
                && (other.isPrivate() == this.isPrivate()));
    }

    /**
     * Returns true if the values inside this object is fully the same as those of the other
     * Includes details and takers
     */
    default boolean isFullyEqual(ReadOnlyExam other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getSubjectName().equals(this.getSubjectName()) // state checks here onwards
                && other.getExamName().equals(this.getExamName())
                && other.getExamDate().equals(this.getExamDate())
                && other.getExamStartTime().equals(this.getExamStartTime())
                && other.getExamEndTime().equals(this.getExamEndTime())
                && other.getExamDetails().equals(this.getExamDetails())
                && other.getTakers() == this.getTakers()
                && (other.isPrivate() == this.isPrivate()));
    }
}
