package seedu.addressbook.data.person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Person's attendance in the address book.
 */

public class Attendance implements Printable {

    /** Represents a map that links dates(kay) to attendance(value)*/
    private Map<String, Boolean> attendanceMap = new HashMap<>();

    /** Method to add attendance */
    public boolean addAttendance(String date, Boolean isPresent, Boolean overWrite) {
        String inputDate = date;
        if ("0".equals(date)) { //PMD 3.3
            inputDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        }
        // If there is a duplicate date
        if (attendanceMap.containsKey(inputDate) && overWrite) {
            attendanceMap.put(inputDate, isPresent);
            return true;
        } else if (attendanceMap.containsKey(inputDate) && !overWrite) {
            return true;
        } else {
            attendanceMap.put(inputDate, isPresent);
            return false;
        }
    }

    /** Method to reiterate person's attendance */
    public String viewAttendance() {
        String output = "Date \t\t Attendance\n";
        String attendance = "Absent";
        for (Map.Entry entry : attendanceMap.entrySet()) {
            if(entry.getValue().equals(true)){
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
                && this.attendanceMap.equals(((Attendance) other).attendanceMap)); // state check
    }

    //TODO store the attendance somewhere (perhaps attendance book?)
}

