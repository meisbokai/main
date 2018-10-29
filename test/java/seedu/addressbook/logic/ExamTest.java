package seedu.addressbook.logic;

import org.junit.Test;
import seedu.addressbook.data.person.Exam;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * For further testing of methods in Exam class
 */
public class ExamTest {
    @Test
    public void isValidTime() {
        assertFalse(Exam.isValidTime("0132/2018"));
        assertFalse(Exam.isValidTime("25:00"));
        assertFalse(Exam.isValidTime("2500"));
        assertFalse(Exam.isValidTime("notATime"));
    }

    @Test
    public void isValidTimeInterval() {
        assertFalse(Exam.isValidTimeInterval("2300", "1000"));
        assertFalse(Exam.isValidTimeInterval("23:00", "10:00"));
        assertFalse(Exam.isValidTimeInterval("10:00", "10:00"));
        assertTrue(Exam.isValidTimeInterval("10:00", "10:01"));
    }

}
