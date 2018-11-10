package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.MESSAGE_DATE_CONSTRAINTS;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.addressbook.logic.CommandAssertions.assertCommandBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.TestDataHelper;
import seedu.addressbook.commands.attendance.ReplaceAttendanceCommand;
import seedu.addressbook.commands.attendance.UpdateAttendanceCommand;
import seedu.addressbook.commands.attendance.ViewAttendanceDateCommand;
import seedu.addressbook.commands.attendance.ViewAttendancePersonCommand;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.privilege.Privilege;
import seedu.addressbook.privilege.user.AdminUser;
import seedu.addressbook.storage.StorageFile;
import seedu.addressbook.stubs.StorageStub;


/**
 * For testing of Attendance Commands
 */
public class AttendanceTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();
    private Logic logic;
    private AddressBook addressBook;

    @Before
    public void setUp() throws Exception {
        StorageStub stubFile;
        StorageFile saveFile;

        addressBook = new AddressBook();
        ExamBook examBook = new ExamBook();
        StatisticsBook statisticBook = new StatisticsBook();
        // Privilege set to admin to allow all commands.
        // Privilege restrictions are tested separately under PrivilegeTest.
        Privilege privilege = new Privilege(new AdminUser());

        saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath(),
                saveFolder.newFile("testExamFile.txt").getPath(),
                saveFolder.newFile("testStatisticsFile.txt").getPath());
        stubFile = new StorageStub(saveFolder.newFile("testStubFile.txt").getPath(),
                saveFolder.newFile("testStubExamFile.txt").getPath(),
                saveFolder.newFile("testStubStatisticsFile.txt").getPath());
        saveFile.save(addressBook);
        logic = new Logic(stubFile, addressBook, examBook, statisticBook, privilege);
        CommandAssertions.setData(saveFile, addressBook, logic);
    }

    /** This file contains the following test:
     * UpdateAttendance
     *      - invalid argument
     *      - success
     *      - invalid date format
     *      - no input date (d/0)
     *      - duplicate date
     *      - invalid index
     *      - invalid attendance (att/x, x != 0 | 1)
     *
     * ViewAttendancePerson
     *      - invalid argument
     *      - success
     *      - NIL date entry
     *      - invalid index
     *
     * ReplaceAttendance
     *      - invalid argument
     *      - Success
     *      - invalid date format
     *      - no input date (d/0)
     *      - No existing attendance
     *      - invalid index
     *      - invalid attendance (att/x, x != 0 | 1)
     *
     * ViewAttendanceDate
     *      - invalid argument
     *      = success
     *      - invalid date format
     *      - no input date (d/0)
     *      - checking date with no attendance
     *
     */

    @Test
    public void execute_UpdateAttendance_InvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateAttendanceCommand.MESSAGE_USAGE);
        assertCommandBehavior("attendance 1 d/29-09-1996 att/ ", expectedMessage);
        assertCommandBehavior("attendance 2", expectedMessage);
    }

    @Test
    public void execute_UpdateAttendance_InvalidDateFormat() throws Exception {
        String expectedMessage = MESSAGE_DATE_CONSTRAINTS;
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);
        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("attendance 1 d/123123-123 att/1 ",
                expectedMessage,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_UpdateAttendance_UpdateCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p1Expected = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);
        List<Person> threePersonsExpected = helper.generatePersonList(p1Expected, p2, p3);

        AddressBook expectedBook = helper.generateAddressBook(threePersonsExpected);
        p1Expected.updateAttendanceMethod("29-09-2018", true, false);

        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("attendance 1 d/29-09-2018 att/1",
                UpdateAttendanceCommand.MESSAGE_SUCCESS + p1Expected.getName(),
                expectedBook,
                false,
                threePersons);

        assertEquals(p1.getAttendance(), p1Expected.getAttendance());
    }

    @Test
    public void execute_UpdateAttendance_NoInputDate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p1Expected = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);
        List<Person> threePersonsExpected = helper.generatePersonList(p1Expected, p2, p3);

        AddressBook expectedBook = helper.generateAddressBook(threePersonsExpected);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        p1Expected.updateAttendanceMethod(currentDate, true, false);

        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("attendance 1 d/0 att/1",
                UpdateAttendanceCommand.MESSAGE_SUCCESS + p1Expected.getName(),
                expectedBook,
                false,
                threePersons);

        assertEquals(p1.getAttendance(), p1Expected.getAttendance());
    }

    @Test
    public void execute_UpdateAttendance_DuplicateDate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);

        p1.updateAttendanceMethod("29-09-2018", true, false);

        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("attendance 1 d/29-09-2018 att/1",
                UpdateAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_UpdateAttendance_InvalidPersonIndex() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);

        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("attendance 2 d/29-09-2018 att/1",
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_UpdateAttendance_InvalidAttendance() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateAttendanceCommand.MESSAGE_USAGE);

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);
        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("attendance 1 d/0 att/2 ",
                expectedMessage,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ViewAttendance_PersonInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAttendancePersonCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewAttenPerson ", expectedMessage);
    }

    @Test
    public void execute_ViewAttendance_PersonSuccess() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p1Expected = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);
        List<Person> personListExpected = helper.generatePersonList(p1Expected);

        AddressBook expectedBook = helper.generateAddressBook(personListExpected);
        p1Expected.updateAttendanceMethod("29-09-2018", true, false);

        helper.addToAddressBook(addressBook, personListExpected);
        logic.setLastShownList(personList);

        assertCommandBehavior("viewAttenPerson 1",
                ViewAttendancePersonCommand.MESSAGE_SUCCESS + p1Expected.getName()
                        + ":\n" + p1Expected.viewAttendanceMethod(),
                expectedBook,
                false,
                personListExpected);
    }

    @Test
    public void execute_ViewAttendance_PersonNilAttendance() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p1Expected = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);
        List<Person> personListExpected = helper.generatePersonList(p1Expected);

        AddressBook expectedBook = helper.generateAddressBook(personListExpected);

        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("viewAttenPerson 1",
                ViewAttendancePersonCommand.MESSAGE_SUCCESS + p1Expected.getName()
                        + ":\n" + p1Expected.viewAttendanceMethod(),
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ViewAttendance_PersonInvalidPersonIndex() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);

        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("viewAttenPerson 2",
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ReplaceAttendance_InvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplaceAttendanceCommand.MESSAGE_USAGE);
        assertCommandBehavior("replaceAtten 1 d/29-09-1996 att/ ", expectedMessage
        );
    }

    @Test
    public void execute_ReplaceAttendance_InvalidDateFormat() throws Exception {
        String expectedMessage = MESSAGE_DATE_CONSTRAINTS;
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);
        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("replaceAtten 1 d/123123-123 att/1 ",
                expectedMessage,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ReplaceAttendance_Success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);

        p1.updateAttendanceMethod("29-09-2018", true, false);

        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("replaceAtten 1 d/29-09-2018 att/1",
                ReplaceAttendanceCommand.MESSAGE_SUCCESS + p1.getName(),
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ReplaceAttendance_NoAttendanceYet() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);

        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("replaceAtten 1 d/29-09-2018 att/1",
                ReplaceAttendanceCommand.MESSAGE_NO_DUPLICATE_ATTENDANCE,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ReplaceAttendance_NoInputDate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p1Expected = helper.generatePerson(1, false);

        List<Person> onePersons = helper.generatePersonList(p1);
        List<Person> onePersonsExpected = helper.generatePersonList(p1Expected);

        AddressBook expectedBook = helper.generateAddressBook(onePersonsExpected);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        p1Expected.updateAttendanceMethod(currentDate, false, false);

        helper.addToAddressBook(addressBook, onePersons);
        logic.setLastShownList(onePersons);

        p1.updateAttendanceMethod(currentDate, true, false);

        assertCommandBehavior("replaceAtten 1 d/0 att/0",
                ReplaceAttendanceCommand.MESSAGE_SUCCESS + p1Expected.getName(),
                expectedBook,
                false,
                onePersons);

        assertEquals(p1.getAttendance(), p1Expected.getAttendance());
    }

    @Test
    public void execute_ReplaceAttendance_InvalidPersonIndex() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);

        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("replaceAtten 2 d/29-09-2018 att/1",
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ReplaceAttendance_InvalidAttendance() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplaceAttendanceCommand.MESSAGE_USAGE);

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);
        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("replaceAtten 1 d/0 att/2 ",
                expectedMessage,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ViewAttendance_DateInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAttendanceDateCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewAttenDate ", expectedMessage);
        assertCommandBehavior("viewAttenDate d/", expectedMessage);
    }

    @Test
    public void execute_ViewAttendance_DateSuccess() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        Person p3 = helper.generatePerson(3, false);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);
        AddressBook expectedBook = helper.generateAddressBook(threePersons);

        p1.updateAttendanceMethod("29-09-2018", true, false);
        p2.updateAttendanceMethod("29-09-2018", true, false);
        p3.updateAttendanceMethod("29-09-2018", false, false);

        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        String expectedMessage = ViewAttendanceDateCommand.MESSAGE_SUCCESS + "29-09-2018:\n"
                + "Present\n" + "Person 1\nPerson 2\n" + "\n"
                + "Absent\n" + "Person 3\n" + "\n";

        assertCommandBehavior("viewAttenDate d/29-09-2018",
                expectedMessage,
                expectedBook,
                false,
                threePersons);
    }

    @Test
    public void execute_ViewAttendance_DateInvalidDateFormat() throws Exception {
        String expectedMessage = MESSAGE_DATE_CONSTRAINTS + ViewAttendanceDateCommand.MESSAGE_USAGE;

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        List<Person> personList = helper.generatePersonList(p1);

        AddressBook expectedBook = helper.generateAddressBook(personList);
        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        assertCommandBehavior("viewAttenDate d/123123-123",
                expectedMessage,
                expectedBook,
                false,
                personList);
    }

    @Test
    public void execute_ViewAttendance_DateNoInputDate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        Person p3 = helper.generatePerson(3, false);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);
        AddressBook expectedBook = helper.generateAddressBook(threePersons);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        p1.updateAttendanceMethod(currentDate, true, false);
        p2.updateAttendanceMethod(currentDate, true, false);
        p3.updateAttendanceMethod(currentDate, false, false);

        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        String expectedMessage = ViewAttendanceDateCommand.MESSAGE_SUCCESS + currentDate + ":\n"
                + "Present\n" + "Person 1\nPerson 2\n" + "\n"
                + "Absent\n" + "Person 3\n" + "\n";

        assertCommandBehavior("viewAttenDate d/0",
                expectedMessage,
                expectedBook,
                false,
                threePersons);
    }

    @Test
    public void execute_ViewAttendance_DateNoAttendanceTaken() throws Exception {
        // Test if the default attendance is "Absent"
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        Person p3 = helper.generatePerson(3, false);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);
        AddressBook expectedBook = helper.generateAddressBook(threePersons);

        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        String expectedMessage = ViewAttendanceDateCommand.MESSAGE_SUCCESS + "01-11-2018" + ":\n"
                + "Present\n" + "" + "\n"
                + "Absent\n" + "Person 1\nPerson 2\nPerson 3\n" + "\n";

        assertCommandBehavior("viewAttenDate d/01-11-2018",
                expectedMessage,
                expectedBook,
                false,
                threePersons);
    }
}
