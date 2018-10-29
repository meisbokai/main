package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Objects;
/**
 * Represents a exam in the exam book.
 */
public class AssignmentStatistics implements Printable {

    public static final String SUBJECT_NAME_EXAMPLE = "Mathematics";
    public static final String EXAM_NAME_EXAMPLE = "Midterms";
    public static final String TOPSCORER_EXAMPLE = "John Doe";
    public static final String AVERAGESCORE_EXAMPLE = "21.5";
    public static final String TOTALEXAMTAKERS_EXAMPLE = "84"; //Number of students who attended test
    public static final String NUMBERABSENT_EXAMPLE = "4"; //Number of students who were absent
    public static final String TOTALPASS_EXAMPLE = "82"; //Number os students who passed the test
    public static final String MAXMIN_EXAMAPLE = "27 10"; //maximum score and minimum score

    public static final String AVERAGE_SCORE_CONSTRAINTS = "May include up to decimal places only";
    public static final String TOTAL_EXAM_TAKERS_CONSTRAINTS = "Number of total exam takers must only contain whole "
           + "numbers";
    public static final String NUMBER_ABSENT_CONSTRAINTS = "Number of absentees must only contain whole numbers";
    public static final String TOTAL_PASS_CONSTRAINTS = "Number of students who passed must only contain whole numbers";
    public static final String MAX_MIN_CONSTRAINTS = "Max-Min value can contain two whole numbers separated by a space";

    public static final String AVERAGE_SCORE_VALIDATION_REGEX = "\\d+(\\.\\d+)?";
    public static final String TOTAL_EXAM_TAKERS_REGEX = "\\d+";
    public static final String NUMBER_ABSENT_REGEX = "\\d+";
    public static final String TOTAL_PASS_REGEX = "\\d+";
    public static final String MAX_MIN_VALIDATION_REGEX = ".+";

    private String subjectName;
    private String examName;
    private String topScorer;
    private String averageScore;
    private String totalExamTakers;
    private String numberAbsent;
    private String totalPass;
    private String maxMin;
    private boolean isPrivate;

    public AssignmentStatistics() {}


    /**
     * Validates given statistics.
     *
     * @throws IllegalValueException if given results string is invalid.
     */
    public AssignmentStatistics(String subjectName, String examName, String topScorer, String averageScore,
                                String totalExamTakers, String numberAbsent, String totalPass, String maxMin,
                                boolean isPrivate) throws IllegalValueException {

        this.subjectName = subjectName.trim();
        this.examName = examName.trim();
        this.topScorer = topScorer.trim();

        String trimmedAverageScore = averageScore.trim();
        if (!isValidAverageScore(trimmedAverageScore)) {
            throw new IllegalValueException(AVERAGE_SCORE_CONSTRAINTS);
        }
        this.averageScore = trimmedAverageScore;

        String trimmedTotalExamTakers = totalExamTakers.trim();
        if (!isValidTotalExamTakers(trimmedTotalExamTakers)) {
            throw new IllegalValueException(TOTAL_EXAM_TAKERS_CONSTRAINTS);
        }
        this.totalExamTakers = trimmedTotalExamTakers;

        String trimmedNumberAbsent = numberAbsent.trim();
        if (!isValidNumberAbsent(trimmedNumberAbsent)) {
            throw new IllegalValueException(NUMBER_ABSENT_CONSTRAINTS);
        }
        this.numberAbsent = trimmedNumberAbsent;

        String trimmedTotalPass = totalPass.trim();
        if (!isValidTotalPass(trimmedTotalPass)) {
            throw new IllegalValueException(TOTAL_PASS_CONSTRAINTS);
        }
        this.totalPass = trimmedTotalPass;

        String trimmedMaxMin = maxMin.trim();
        if (!isValidMaxMin(trimmedMaxMin)) {
            throw new IllegalValueException(MAX_MIN_CONSTRAINTS);
        }
        this.maxMin = trimmedMaxMin;

        this.isPrivate = isPrivate;
    }

    /**
     * Checks if a given string is a valid average score.
     */
    public static boolean isValidAverageScore(String value) {
        return value.matches(AVERAGE_SCORE_VALIDATION_REGEX);
    }

    /**
     * Checks if a given string is a valid number for the total exam takers.
     */
    public static boolean isValidTotalExamTakers(String value) {
        return value.matches(TOTAL_EXAM_TAKERS_REGEX);
    }

    /**
     * Checks if a given string is a valid number of absentees.
     */
    public static boolean isValidNumberAbsent(String value) {
        return value.matches(NUMBER_ABSENT_REGEX);
    }

    /**
     * Checks if a given string is a valid number of total students who passed.
     */
    public static boolean isValidTotalPass(String value) {
        return value.matches(TOTAL_PASS_REGEX);
    }

    /**
     * Checks if a given string is a valid entry of max-min score.
     */
    public static boolean isValidMaxMin(String value) {
        return value.matches(MAX_MIN_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return subjectName + " - " + examName + " Top Scorer: " + topScorer
                + " Average Score: " + averageScore + " Total Exam Takers: " + totalExamTakers
                + " Total Absentees: " + numberAbsent + " Total Pass: " + totalPass
                + " Max-Min Score: " + maxMin;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentStatistics // instanceof handles nulls
                && this.examName.equals(((AssignmentStatistics) other).examName)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(averageScore, totalExamTakers, numberAbsent, totalPass, maxMin);
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        if (isPrivate()) {
            if (showPrivate) {
                return "{private Results: " + subjectName + " " + examName + " " + topScorer + " "
                        + averageScore + " " + totalExamTakers + " " + numberAbsent + totalPass + " "
                        + maxMin + " " + "}";
            } else {
                return "";
            }
        }
        return "Exam: " + subjectName + " " + examName + " " + topScorer + " "
                + averageScore + " " + totalExamTakers + " " + numberAbsent + totalPass + " "
                + maxMin;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public String getExamName() {
        return examName;
    }

    public String getTopScorer() {
        return topScorer;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public String getTotalExamTakers() {
        return totalExamTakers;
    }

    public String getNumberAbsent() {
        return numberAbsent;
    }

    public String getTotalPass() {
        return totalPass;
    }

    public String getMaxMin() {
        return maxMin;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
