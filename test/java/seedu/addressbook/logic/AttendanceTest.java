package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_DATE;
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
 * For further testing of methods in Attendance class
 */
public class AttendanceTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();
    private StatisticsBook statisticBook;
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        StorageStub stubFile;
        StorageFile saveFile;

        AddressBook addressBook = new AddressBook();
        Privilege privilege = new Privilege(new AdminUser());
        ExamBook examBook = new ExamBook();
        statisticBook = new StatisticsBook();
        // Privilege set to admin to allow all commands.
        // Privilege restrictions are tested separately under PrivilegeTest.
        saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath());
        stubFile = new StorageStub(saveFolder.newFile("testStubFile.txt").getPath());
        saveFile.save(addressBook);
        logic = new Logic(stubFile, addressBook, examBook, statisticBook, privilege);
        CommandAssertions.setData(saveFile, addressBook, logic, examBook, statisticBook);
        saveFile.saveExam(examBook);
        saveFile.saveStatistics(statisticBook);
        saveFile.save(addressBook);
    }

    /** This file contains the following test:
     * UpdateAttendance
     *      - invalid argument
     *      - success
     *      - invalid date
     *      - no input date (d/0)
     *      - duplicate date
     *
     * ViewAttendancePerson
     *      - invalid argument
     *      - success
     *      - NIL date entry
     *
     * ReplaceAttendance
     *      - invalid argument
     *      - Success
     *      - invalid date
     *      - no input date (d/0)
     *      - No existing attendance
     *
     * ViewAttendanceDate
     *      - invalid argument
     *      = success
     *      + invalid date
     *      - no input date (d/0)
     *
     */

    @Test
    public void executeUpdateAttendanceInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateAttendanceCommand.MESSAGE_USAGE);
        assertCommandBehavior("attendance 1 d/29-09-1996 att/ ", expectedMessage);
        assertCommandBehavior("attendance 2", expectedMessage);
    }

    @Test
    public void executeUpdateAttendanceInvalidDateFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_DATE, UpdateAttendanceCommand.MESSAGE_USAGE);

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
    public void executeUpdateAttendanceUpdateCorrectPerson() throws Exception {
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
                String.format(UpdateAttendanceCommand.MESSAGE_SUCCESS + p1Expected.getName()),
                expectedBook,
                false,
                threePersons);

        assertEquals(p1.getAttendance(), p1Expected.getAttendance());
    }

    @Test
    public void executeUpdateAttendanceNoInputDate() throws Exception {
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
                String.format(UpdateAttendanceCommand.MESSAGE_SUCCESS + p1Expected.getName()),
                expectedBook,
                false,
                threePersons);

        assertEquals(p1.getAttendance(), p1Expected.getAttendance());
    }

    @Test
    public void executeUpdateAttendanceDuplicateDate() throws Exception {
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
    public void executeViewAttendanceInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAttendancePersonCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewAttenPerson ", expectedMessage);
    }

    @Test
    public void executeViewAttendanceSuccess() throws Exception {
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
    public void executeViewAttendanceNilAttendance() throws Exception {
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
    public void executeReplaceAttendanceInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplaceAttendanceCommand.MESSAGE_USAGE);
        assertCommandBehavior("replaceAtten 1 d/29-09-1996 att/ ", expectedMessage);
    }

    @Test
    public void executeReplaceAttendanceInvalidDateFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_DATE, ReplaceAttendanceCommand.MESSAGE_USAGE);

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
    public void executeReplaceAttendanceSuccess() throws Exception {
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
    public void executeReplaceAttendanceNoAttendanceYet() throws Exception {
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
    public void executeReplaceAttendanceNoInputDate() throws Exception {
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
                String.format(ReplaceAttendanceCommand.MESSAGE_SUCCESS + p1Expected.getName()),
                expectedBook,
                false,
                onePersons);

        assertEquals(p1.getAttendance(), p1Expected.getAttendance());
    }

    @Test
    public void executeViewAttendanceDateInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAttendanceDateCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewAttenDate ", expectedMessage);
        assertCommandBehavior("viewAttenDate d/", expectedMessage);
    }

    @Test
    public void executeViewAttendanceDateSuccess() throws Exception {
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
    public void executeViewAttendanceDateInvalidDateFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_DATE + ViewAttendanceDateCommand.MESSAGE_USAGE);

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
    public void executeViewAttendanceDateNoInputDate() throws Exception {
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

}
