package seedu.addressbook.data.person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Represents a Person's attendance in the address book.
 */

public class Attendance implements Printable {

    /** Represents a map for each person, showing which every attendance for each date*/
    private Map<String, Boolean> attendancePersonMap = new HashMap<>();

    /** Represents a map for each date, showing a list of people who were present*/
    private List<String> peoplePresent = new ArrayList<String>();
    private Map<String, List> attendanceDateMap = new HashMap<>();

    /** Method to add attendance*/
    public boolean addAttendance(String date, Boolean isPresent, Boolean overWrite) {
        String inputDate = date;
        if ("0".equals(date)) {
            inputDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        }
        // If there is a duplicate date
        if (attendancePersonMap.containsKey(inputDate) && overWrite) {
            attendancePersonMap.put(inputDate, isPresent);
            return true;
        } else if (attendancePersonMap.containsKey(inputDate) && !overWrite) {
            return true;
        } else {
            attendancePersonMap.put(inputDate, isPresent);
            return false;
        }
    }

    /** Method to reiterate person's attendance */
    public String viewAttendance() {
        String output = "Date \t\t Attendance\n";
        String attendance = "Absent";
        for (Map.Entry entry : attendancePersonMap.entrySet()) {
            if (entry.getValue().equals(true)) {
                attendance = "Present";
            }
            output += entry.getKey() + "\t\t" + attendance + "\n";
        }
        if ("".equals(output)) {
            output = "NIL";
        }
        return output;
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        return "{}";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendance // instanceof handles nulls
                && this.attendancePersonMap.equals(((Attendance) other).attendancePersonMap)); // state check
    }

    public boolean isPrivate() {
        return true;
    }

    //TODO store the attendance somewhere (under address book)
}

