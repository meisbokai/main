//package seedu.addressbook.storage.jaxb;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.xml.bind.annotation.XmlValue;
//
//import seedu.addressbook.common.Utils;
////import seedu.addressbook.data.exception.IllegalValueException;
//import seedu.addressbook.data.person.Attendance;
//
//
///**
// * JAXB-friendly adapted attendance data holder class.
// */
//public class AdaptedAttendance {
//
//    @XmlValue
//    private List<String> attendanceDate;
//
//    @XmlValue
//    private List<String> attendanceIsPresent;
//
//    /**
//     * No-arg constructor for JAXB use.
//     */
//    public AdaptedAttendance() {}
//
//    /**
//     * Converts a given attendance into this class for JAXB use.
//     *
//     * @param source future changes to this will not affect the created AdaptedTag
//     */
//    public AdaptedAttendance(Attendance source) {
//        for (Map.Entry entry: source.getAttendancePersonMap().entrySet()) {
//            if (entry.getValue().equals(true)) {
//                attendanceIsPresent.add("Present");
//            } else {
//                attendanceIsPresent.add("Absent");
//            }
//        }
//
//        attendanceDate = new ArrayList<>(source.getAttendancePersonMap().keySet());
//    }
//
//    /**
//     * Returns true if any required field is missing.
//     *
//     * JAXB does not enforce (required = true) without a given XML schema.
//     * Since we do most of our validation using the data class constructors, the only extra logic we need
//     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
//     * so we check for that.
//     */
//    public boolean isAnyRequiredFieldMissing() {
//        return Utils.isAnyNull(attendanceDate, attendanceIsPresent);
//    }
//
//    /**
//     * Converts this jaxb-friendly adapted attendance object into the Attendance object.
//     *
//     * @throws IllegalValueException if there were any data constraints violated in the adapted person
//     */
//    //    public Attendance toModelType() throws IllegalValueException {
//    //        return new Attendance(attendanceDate, attendanceIsPresent);
//    //    }
//
//}
