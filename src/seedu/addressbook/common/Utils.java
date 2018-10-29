package seedu.addressbook.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility methods
 */
public class Utils {

    /**
     * Checks whether any of the given items are null.
     */
    public static boolean isAnyNull(Object... items) {
        for (Object item : items) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if every element in a collection are unique by {@link Object#equals(Object)}.
     */
    public static boolean elementsAreUnique(Collection<?> items) {
        final List<Object> testSet = new ArrayList<>();
        for (Object item: items) {
            if (objectIsInList(testSet, item)) {
                return false;
            }

            testSet.add(item);
        }
        return true;
    }
    /** Checks if an object is exists in the list*/
    private static boolean objectIsInList (List<Object> list, Object object) {
        return (!list.stream()
                .filter(p -> (p == null && object == null)
                        || (p != null && p.equals(object)))
                .collect(Collectors.toList())
                .isEmpty());
    }

    /**
     * Checks if a given string is a valid date.
     * Solution below adapted from https://stackoverflow.com/a/30578421
     */
    public static boolean isValidDate(String value) {
        boolean valid;
        final String format = "dd-MM-yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            String parsedDate = LocalDate.parse(value, formatter).format(formatter);
            valid = value.equals(parsedDate);
        } catch (DateTimeParseException ex) {
            valid = false;
        }
        return valid;
    }
}
