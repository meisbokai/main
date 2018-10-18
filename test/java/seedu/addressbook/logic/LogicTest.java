package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_DATE;
import static seedu.addressbook.common.Messages.MESSAGE_NO_ARGS_FOUND;
import static seedu.addressbook.common.Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK;
import static seedu.addressbook.common.Messages.MESSAGE_WRONG_NUMBER_ARGUMENTS;
import static seedu.addressbook.logic.CommandAssertions.assertCommandBehavior;
import static seedu.addressbook.logic.CommandAssertions.assertInvalidIndexBehaviorForCommand;
import static seedu.addressbook.logic.CommandAssertions.assertInvalidIndexBehaviorForExamCommand;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.TestDataHelper;
import seedu.addressbook.commands.AddAssessmentCommand;
import seedu.addressbook.commands.AddAssignmentStatistics;
import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.AddExamCommand;
import seedu.addressbook.commands.AddFeesCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.ClearExamsCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.DeleteExamCommand;
import seedu.addressbook.commands.EditExamCommand;
import seedu.addressbook.commands.EditPasswordCommand;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.FindCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.ReplaceAttendanceCommand;
import seedu.addressbook.commands.UpdateAttendanceCommand;
import seedu.addressbook.commands.ViewAllCommand;
import seedu.addressbook.commands.ViewAttendanceCommand;
import seedu.addressbook.commands.ViewCommand;
import seedu.addressbook.commands.ViewFeesCommand;
import seedu.addressbook.commands.ViewSelfCommand;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.AssignmentStatistics;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.privilege.Privilege;
import seedu.addressbook.privilege.user.AdminUser;
import seedu.addressbook.storage.StorageFile;
import seedu.addressbook.stubs.StorageStub;

public class LogicTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private StorageFile saveFile;
    private AddressBook addressBook;
    private Privilege privilege;
    private ExamBook examBook;
    private StatisticsBook statisticBook;
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        StorageStub stubFile;
        saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath(),
                saveFolder.newFile("testExamFile.txt").getPath(),
                saveFolder.newFile("testStatisticsFile.txt").getPath());
        stubFile = new StorageStub(saveFolder.newFile("testStubFile.txt").getPath(),
                saveFolder.newFile("testStubExamFile.txt").getPath(),
                saveFolder.newFile("testStubStatisticsFile.txt").getPath());

        addressBook = new AddressBook();
        examBook = new ExamBook();
        statisticBook = new StatisticsBook();
        // Privilege set to admin to allow all commands.
        // Privilege restrictions are tested separately under PrivilegeTest.
        privilege = new Privilege(new AdminUser());

        saveFile.save(addressBook);
        logic = new Logic(stubFile, addressBook, examBook, statisticBook, privilege);
        CommandAssertions.setData(saveFile, addressBook, logic, examBook, statisticBook);
        saveFile.saveExam(examBook);
        saveFile.saveStatistics(statisticBook);
        saveFile.save(addressBook);
    }

    @Test
    public void constructor() {
        //Constructor is called in the setup() method which executes before every test, no need to call it here again.

        //Confirm the last shown list is empty
        assertEquals(Collections.emptyList(), logic.getLastShownList());
    }

    private void setUpThreePerson(AddressBook addressBook,
                                  AddressBook expected,
                                  Logic logic,
                                  TestDataHelper.ThreePersons threePersons) throws Exception {
        TestDataHelper helper = new TestDataHelper();
        //expected = helper.generateAddressBook(threePersons.getExpected());
        helper.addToAddressBook(expected, threePersons.getExpected());
        helper.addToAddressBook(addressBook, threePersons.getActual());
        logic.setLastShownList(threePersons.getActual());
    }


    @Test
    public void defaultConstructor() throws Exception {
        // Verifies if addressbook.txt is loadable
        logic = new Logic();
        //Confirm the last shown list is empty
        assertEquals(Collections.emptyList(), logic.getLastShownList());
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, HelpCommand.makeHelpMessage());
    }

    @Test
    public void executeHelp() throws Exception {
        assertCommandBehavior("help", HelpCommand.makeHelpMessage());

        privilege.raiseToTutor();
        assertCommandBehavior("help", HelpCommand.makeHelpMessage());

        privilege.raiseToAdmin();
        assertCommandBehavior("help", HelpCommand.makeHelpMessage());
    }

    @Test
    public void executeExit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWEDGEMENT);
    }


    @Test
    public void executeClearSuccess() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        // TODO: refactor this elsewhere
        logic.setStorage(saveFile);
        // generates the 3 test people and execute the add command
        for (int i = 1; i <= 3; ++i) {
            final Person testPerson = helper.generatePerson(i, true);
            addressBook.addPerson(testPerson);
            logic.execute(helper.generateAddCommand(testPerson));
        }

        assertCommandBehavior("clear",
                ClearCommand.MESSAGE_SUCCESS,
                AddressBook.empty(),
                false,
                Collections.emptyList(),
                true);
    }

    @Test
    public void executeAddInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address", expectedMessage);
    }

    @Test
    public void executeAddInvalidPersonData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] p/12345 e/valid@e.mail a/valid, address", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/not_numbers e/valid@e.mail a/valid, address", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/notAnEmail a/valid, address", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@e.mail a/#$%#@#What Am I?", Address.MESSAGE_ADDRESS_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/1234 e/valid@e.mail a/valid, address t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void executeAddSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        AddressBook expected = new AddressBook();
        expected.addPerson(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expected,
                false,
                Collections.emptyList(),
                true);

    }

    @Test
    public void executeAddFeesCommandInvalidData() throws Exception {
        assertCommandBehavior(
                "addfees 2 1.111", Fees.MESSAGE_FEES_CONSTRAINTS);
    }

    @Test
    public void executeAddFeesSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expected = helper.generateAddressBook(threePersons);
        expected.findPerson(p2).setFees(helper.fees());


        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        assertCommandBehavior(helper.generateAddFeesCommand(),
                String.format(AddFeesCommand.MESSAGE_SUCCESS, p2),
                expected,
                false,
                threePersons);
    }

    @Test
    public void executeAddDuplicateNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        AddressBook expected = new AddressBook();
        expected.addPerson(toBeAdded);

        // setup starting state
        addressBook.addPerson(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_PERSON,
                expected,
                false,
                Collections.emptyList());

    }

    @Test
    public void executeAddExamSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Exam toBeAdded = helper.math();
        ExamBook expected = new ExamBook();
        expected.addExam(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddExamCommand(toBeAdded),
                String.format(AddExamCommand.MESSAGE_SUCCESS, toBeAdded, toBeAdded.isPrivate() ? " private" : ""),
                expected, true);
    }

    @Test
    public void executeAddDuplicateExamNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Exam toBeAdded = helper.math();
        ExamBook expected = new ExamBook();
        expected.addExam(toBeAdded);

        // setup starting state
        examBook.addExam(toBeAdded); // exam already in internal exam book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddExamCommand(toBeAdded),
                AddExamCommand.MESSAGE_DUPLICATE_EXAM,
                expected,
                true);
    }

    @Test
    public void executeAddExamInvalidArgs() throws Exception {
        final String invalidDateArg = "d/32122018";
        final String invalidDateArg2 = "d/not_a_number";
        final String validDateArg = "d/" + Exam.EXAM_DATE_EXAMPLE;
        final String invalidStartTimeArg = "st/not__numbers";
        final String invalidStartTimeArg2 = "st/999999";
        final String validStartTimeArg = "st/" + Exam.EXAM_START_TIME_EXAMPLE;
        final String invalidEndTimeArg = "et/not__numbers";
        final String invalidEndTimeArg2 = "et/999999";
        final String validEndTimeArg = "et/" + Exam.EXAM_END_TIME_EXAMPLE;
        final String invalidEndTimeIntervalArg = "et/08:00";

        // exam name, subject name and details can be any string, so no invalid strings
        final String addExamCommandFormatString = "addexam e/" + Exam.EXAM_NAME_EXAMPLE
                + " s/" + Exam.SUBJECT_NAME_EXAMPLE
                + " %s %s %s dt/" + Exam.EXAM_DETAILS_EXAMPLE;

        TestDataHelper helper = new TestDataHelper();
        Exam toBeAdded = helper.math();
        ExamBook expected = new ExamBook();
        expected.addExam(toBeAdded);

        String expectedMessage = Exam.MESSAGE_DATE_CONSTRAINTS;
        assertCommandBehavior(String.format(addExamCommandFormatString, invalidDateArg,
                validStartTimeArg, validEndTimeArg), expectedMessage);
        assertCommandBehavior(String.format(addExamCommandFormatString, invalidDateArg2,
                validStartTimeArg, validEndTimeArg), expectedMessage);

        expectedMessage = Exam.MESSAGE_TIME_CONSTRAINTS;
        assertCommandBehavior(String.format(addExamCommandFormatString, validDateArg,
                invalidStartTimeArg, validEndTimeArg), expectedMessage);
        assertCommandBehavior(String.format(addExamCommandFormatString, validDateArg,
                invalidStartTimeArg2, validEndTimeArg), expectedMessage);
        assertCommandBehavior(String.format(addExamCommandFormatString, validDateArg,
                validStartTimeArg, invalidEndTimeArg), expectedMessage);
        assertCommandBehavior(String.format(addExamCommandFormatString, validDateArg,
                validStartTimeArg, invalidEndTimeArg2), expectedMessage);

        expectedMessage = Exam.MESSAGE_TIME_INTERVAL_CONSTRAINTS;
        assertCommandBehavior(String.format(addExamCommandFormatString, validDateArg,
                validStartTimeArg, invalidEndTimeIntervalArg), expectedMessage);
    }

    @Test
    public void executeAddAssignmentStatisticsSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        AssignmentStatistics toBeAdded = helper.stat();
        StatisticsBook expected = new StatisticsBook();
        expected.addStatistic(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddAssignmentStatistics(toBeAdded),
                String.format(AddAssignmentStatistics.MESSAGE_SUCCESS, toBeAdded),
                expected, false);
    }

    @Test
    public void executeAddAssignmentStatisticsDuplicateNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        AssignmentStatistics toBeAdded = helper.stat();
        StatisticsBook expected = new StatisticsBook();
        expected.addStatistic(toBeAdded);

        // setup starting state
        statisticBook.addStatistic(toBeAdded); // statistic already in internal statistic book

        // execute command and verify result
        assertCommandBehavior(helper.generateAddAssignmentStatistics(toBeAdded),
                AddAssignmentStatistics.MESSAGE_DUPLICATE_STATISTIC, expected, false);
    }

    @Test
    public void executeAddAssessmentSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Assessment toBeAdded = helper.assess();
        AddressBook expected = new AddressBook();
        expected.addAssessment(toBeAdded);
        List<? extends ReadOnlyPerson> dummyList = expected.getAllPersons().immutableListView();

        // execute command and verify result
        assertCommandBehavior(helper.generateAddAssessment(toBeAdded),
                String.format(AddAssessmentCommand.MESSAGE_SUCCESS, toBeAdded), expected, false,
                dummyList);
    }

    @Test
    public void executeAddAssessmentDuplicateNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Assessment toBeAdded = helper.assess();
        AddressBook expected = new AddressBook();
        expected.addAssessment(toBeAdded);
        List<? extends ReadOnlyPerson> dummyList = expected.getAllPersons().immutableListView();

        // setup starting state
        addressBook.addAssessment(toBeAdded); // statistic already in internal statistic book

        // execute command and verify result
        assertCommandBehavior(helper.generateAddAssessment(toBeAdded),
                AddAssessmentCommand.MESSAGE_DUPLICATE_ASSESSMENT, expected, false, dummyList);
    }

    @Test
    public void executeExamsListShowsAllExams() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        ExamBook expected = null;

        expected = helper.generateExamBook(false, true);
        List<? extends ReadOnlyExam> expectedList = expected.getAllExam().immutableListView();

        // prepare exam book state
        helper.addToExamBook(examBook, false, true);

        assertCommandBehavior("examslist",
                Command.getMessageForExamListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeListShowsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expected = helper.generateAddressBook(false, true);
        List<? extends ReadOnlyPerson> expectedList = expected.getAllPersons().immutableListView();

        // prepare address book state
        helper.addToAddressBook(addressBook, false, true);


        assertCommandBehavior("list",
                Command.getMessageForPersonListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeViewInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertCommandBehavior("view ", expectedMessage);
        assertCommandBehavior("view arg not number", expectedMessage);
    }

    @Test
    public void executeViewInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("view");
    }

    @Test
    public void executeViewOnlyShowsNonPrivate() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expected = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);

        assertCommandBehavior("view 1",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p1.getAsTextHidePrivate()),
                expected,
                false,
                lastShownList);


        assertCommandBehavior("view 2",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p2.getAsTextHidePrivate()),
                expected,
                false,
                lastShownList);
    }

    @Test
    public void executeTryToViewMissingPersonErrorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);

        AddressBook expected = new AddressBook();
        expected.addPerson(p2);

        addressBook.addPerson(p2);
        logic.setLastShownList(lastShownList);


        assertCommandBehavior("view 1",
                MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expected,
                false,
                lastShownList);
    }

    @Test
    public void executeViewAllInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewall ", expectedMessage);
        assertCommandBehavior("viewall arg not number", expectedMessage);
    }

    @Test
    public void executeViewAllInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("viewall");
    }

    @Test
    public void executeViewAllAlsoShowsPrivate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expected = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);


        assertCommandBehavior("viewall 1",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p1.getAsTextShowAll()),
                expected,
                false,
                lastShownList);


        assertCommandBehavior("viewall 2",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p2.getAsTextShowAll()),
                expected,
                false,
                lastShownList);
    }

    @Test
    public void executeTryToViewAllPersonMissingInAddressBookErrorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);

        AddressBook expected = new AddressBook();
        expected.addPerson(p1);

        addressBook.addPerson(p1);
        logic.setLastShownList(lastShownList);


        assertCommandBehavior("viewall 2",
                MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expected,
                false,
                lastShownList);
    }

    @Test
    public void executeViewFeesCommandSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expected = helper.generateAddressBook(threePersons);
        expected.findPerson(p2).setFees(helper.fees());

        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("viewfees 2",
                String.format(ViewFeesCommand.MESSAGE_VIEWFEE_PERSON_SUCCESS, p2.getAsTextShowFee()),
                expected,
                false,
                threePersons,
                false);
    }

    @Test
    public void executeViewFeesCommandInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("viewfees");
    }

    @Test
    public void executeViewFeesInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewFeesCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewfees ", expectedMessage);
        assertCommandBehavior("viewfees arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertCommandBehavior("delete ", expectedMessage);
        assertCommandBehavior("delete arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("delete");
    }

    @Test
    public void executeDeleteRemovesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        AddressBook expected = new AddressBook();
        TestDataHelper.ThreePersons threePersons = helper.generateThreePersons();

        setUpThreePerson(addressBook, expected, logic, threePersons);

        final Person p2 = threePersons.getExpectedPerson(2);
        expected.removePerson(p2);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, p2),
                expected,
                false,
                threePersons.getActual(),
                true);
    }

    @Test
    public void executeDeleteSelfExceptionThrown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        AddressBook expected = new AddressBook();
        TestDataHelper.ThreePersons threePersons = helper.generateThreePersons();

        setUpThreePerson(addressBook, expected, logic, threePersons);

        final Person p2 = threePersons.getActualPerson(2);
        privilege.setMyPerson(p2);
        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETING_SELF),
                expected,
                false,
                threePersons.getActual(),
                true);
    }

    @Test
    public void executeDeleteMissingInAddressBook() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expected = helper.generateAddressBook(threePersons);
        expected.removePerson(p2);

        helper.addToAddressBook(addressBook, threePersons);
        addressBook.removePerson(p2);
        logic.setLastShownList(threePersons);


        assertCommandBehavior("delete 2",
                MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expected,
                false,
                threePersons);
    }

    @Test
    public void executeFindInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void executeFindOnlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p1 = helper.generatePersonWithName("KE Y");
        Person p2 = helper.generatePersonWithName("KEYKEYKEY sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expected = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);


        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeFindIsCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p1 = helper.generatePersonWithName("key key");
        Person p2 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expected = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);


        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeFindMatchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla rAnDoM bla bceofeia");
        Person p1 = helper.generatePersonWithName("key key");
        Person p2 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expected = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);


        assertCommandBehavior("find KEY rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeChangePasswordInvalidArguments() throws Exception {
        final String initialPassword = addressBook.getMasterPassword();
        final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditPasswordCommand.MESSAGE_USAGE);
        assertCommandBehavior("editpw", expectedMessage);
        assertCommandBehavior("editpw ", expectedMessage);
        assertEquals(addressBook.getMasterPassword(), initialPassword);
    }

    @Test
    public void executeChangePasswordInvalidArgumentNumber() throws Exception {
        final String initialPassword = addressBook.getMasterPassword();
        final String expectedMessage = MESSAGE_WRONG_NUMBER_ARGUMENTS;
        final int requiredArguments = 2;
        int actualArguments = 1;
        assertCommandBehavior("editpw default_pw", String.format(expectedMessage,
                requiredArguments, actualArguments, EditPasswordCommand.MESSAGE_USAGE));

        actualArguments = 3;
        assertCommandBehavior("editpw default_pw new_pw extra_arg",
                String.format(expectedMessage, requiredArguments, actualArguments,
                        EditPasswordCommand.MESSAGE_USAGE));
        assertEquals(addressBook.getMasterPassword(), initialPassword);
    }

    @Test
    public void executeChangePasswordWrongPassword() throws Exception {
        final String initialPassword = addressBook.getMasterPassword();
        String expectedMessage = EditPasswordCommand.MESSAGE_WRONG_PASSWORD;
        assertCommandBehavior("editpw wrong_password new_password", expectedMessage);
        assertCommandBehavior("editpw default_password1 new_password", expectedMessage);
        assertCommandBehavior("editpw Default_password new_password", expectedMessage);
        assertEquals(addressBook.getMasterPassword(), initialPassword);
    }

    @Test
    public void executeChangePasswordSuccess() throws Exception {
        final String expectedMessage = EditPasswordCommand.MESSAGE_SUCCESS;
        final String commandFormat = "editpw %s %s";
        String oldPassword = addressBook.getMasterPassword();
        String newPassword = "new_password";
        String commandInput = String.format(commandFormat, oldPassword, newPassword);
        assertCommandBehavior(commandInput,
                String.format(expectedMessage, newPassword));
        assertEquals(addressBook.getMasterPassword(), newPassword);

        oldPassword = addressBook.getMasterPassword();
        newPassword = "another_new_password";
        commandInput = String.format(commandFormat, oldPassword, newPassword);
        assertCommandBehavior(commandInput,
                String.format(expectedMessage, newPassword));
        assertEquals(addressBook.getMasterPassword(), newPassword);
    }

    @Test
    public void executeViewSelfNotLoggedIn() throws Exception {
        assertCommandBehavior("viewself", Messages.MESSAGE_NOT_LOGGED_IN);
    }

    @Test
    public void executeViewSelfSuccess() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        AddressBook expected = new AddressBook();
        TestDataHelper.ThreePersons threePersons = helper.generateThreePersons();

        setUpThreePerson(addressBook, expected, logic, threePersons);

        final Person p2 = threePersons.getActualPerson(2);
        privilege.setMyPerson(p2);
        assertCommandBehavior("viewself",
                String.format(ViewSelfCommand.MESSAGE_VIEW_PERSON_DETAILS, p2.getAsTextShowAll()),
                expected,
                false,
                threePersons.getActual());
    }

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

        assertCommandBehavior("viewAtten 1",
                ViewAttendanceCommand.MESSAGE_SUCCESS + p1Expected.getName()
                               + ":\n" + p1Expected.viewAttendanceMethod(),
                expectedBook,
                false,
                personListExpected);
    }

    @Test
    public void executeViewAttendanceNILAttendance() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);

        List<Person> personList = helper.generatePersonList(p1);

        helper.addToAddressBook(addressBook, personList);
        logic.setLastShownList(personList);

        // TODO update later
        assertCommandBehavior("viewAtten 1",
                ViewAttendanceCommand.MESSAGE_SUCCESS + p1.getName()
                        + ":\n" + p1.viewAttendanceMethod()
                );
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
    public void executeClearExamsSuccess() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        // TODO: refactor this elsewhere
        logic.setStorage(saveFile);
        // generates the 3 test exam and execute the add exam command
        for (int i = 1; i <= 3; ++i) {
            final Exam testExam = helper.generateExam(i, true);
            examBook.addExam(testExam);
            logic.execute(helper.generateAddExamCommand(testExam));
        }
        assertCommandBehavior("clearexams",
                ClearExamsCommand.MESSAGE_SUCCESS,
                ExamBook.empty(),
                false,
                Collections.emptyList(),
                true);
    }

    @Test
    public void executeDeleteExamInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExamCommand.MESSAGE_USAGE);
        assertCommandBehavior("deleteexam ", expectedMessage);
        assertCommandBehavior("deleteexam arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteExamInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForExamCommand("deleteexam");
    }

    @Test
    public void executeDeleteExamRemovesCorrectExam() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Exam e1 = helper.generateExam(1, false);
        Exam e2 = helper.generateExam(2, true);
        Exam e3 = helper.generateExam(3, true);

        List<Exam> threeExams = helper.generateExamList(e1, e2, e3);

        ExamBook expected = helper.generateExamBook(threeExams);
        expected.removeExam(e2);


        helper.addToExamBook(examBook, threeExams);
        logic.setLastShownExamList(threeExams);

        assertCommandBehavior("deleteexam 2",
                String.format(DeleteExamCommand.MESSAGE_DELETE_EXAM_SUCCESS, e2),
                expected,
                false,
                threeExams,
                true);
    }

    @Test
    public void executeDeleteMissingInExamBook() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Exam e1 = helper.generateExam(1, false);
        Exam e2 = helper.generateExam(2, true);
        Exam e3 = helper.generateExam(3, true);

        List<Exam> threeExams = helper.generateExamList(e1, e2, e3);

        ExamBook expected = helper.generateExamBook(threeExams);
        expected.removeExam(e2);

        helper.addToExamBook(examBook, threeExams);
        examBook.removeExam(e2);
        logic.setLastShownExamList(threeExams);

        assertCommandBehavior("deleteexam 2",
                Messages.MESSAGE_EXAM_NOT_IN_EXAMBOOK,
                expected,
                false,
                threeExams);
    }

    @Test
    public void executeEditMissingInExamBook() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Exam e1 = helper.generateExam(1, false);
        Exam e2 = helper.generateExam(2, true);
        Exam e3 = helper.generateExam(3, true);

        List<Exam> threeExams = helper.generateExamList(e1, e2, e3);

        ExamBook expected = helper.generateExamBook(threeExams);
        expected.removeExam(e2);

        helper.addToExamBook(examBook, threeExams);
        examBook.removeExam(e2);
        logic.setLastShownExamList(threeExams);

        assertCommandBehavior("editexam 2 s/Mathematics",
                Messages.MESSAGE_EXAM_NOT_IN_EXAMBOOK,
                expected,
                false,
                threeExams);
    }

    @Test
    public void executeEditExamInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForExamCommand("editexam 4 s/Mathematics");
    }

    @Test
    public void executeEditExamInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExamCommand.MESSAGE_USAGE);
        assertCommandBehavior("editexam ", expectedMessage);
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NO_ARGS_FOUND
                + EditExamCommand.MESSAGE_USAGE);
        assertCommandBehavior("editexam arg not number", expectedMessage);
    }

    @Test
    public void executeEditExamSuccess() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Exam e1 = helper.generateExam(1, false);
        Exam e2 = helper.generateExam(2, true);
        Exam e3 = helper.generateExam(3, true);
        Exam e4 = helper.generateExam(4, false);
        List<Exam> threeExams = helper.generateExamList(e1, e2, e3);

        ExamBook expected = helper.generateExamBook(threeExams);
        expected.editExam(e2, e4);


        helper.addToExamBook(examBook, threeExams);
        logic.setLastShownExamList(threeExams);

        assertCommandBehavior("editexam 2 p/n e/Exam 4 s/Subject 4 d/01-02-2018 dt/Held in 4",
                String.format(EditExamCommand.MESSAGE_EDIT_EXAM_SUCCESS, e2, e4, e4.isPrivate() ? " private" : ""),
                expected,
                false,
                threeExams,
                true);
    }

    @Test
    public void executeEditExamInvalidArgs() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Exam e1 = helper.generateExam(1, false);
        List<Exam> singleExam = helper.generateExamList(e1);
        helper.addToExamBook(examBook, singleExam);
        logic.setLastShownExamList(singleExam);

        String expectedMessage = Exam.MESSAGE_DATE_CONSTRAINTS;
        assertCommandBehavior("editexam 1 d/notADate", expectedMessage);
        assertCommandBehavior("editexam 1 d/07012018 st/09:00", expectedMessage);
        assertCommandBehavior("editexam 1 d/32122018 st/09:00", expectedMessage);

        expectedMessage = Exam.MESSAGE_TIME_CONSTRAINTS;
        assertCommandBehavior("editexam 1 st/notATime", expectedMessage);
        assertCommandBehavior("editexam 1 st/07012018", expectedMessage);
        assertCommandBehavior("editexam 1 st/2366", expectedMessage);
        assertCommandBehavior("editexam 1 et/notATime", expectedMessage);
        assertCommandBehavior("editexam 1 et/07012018", expectedMessage);
        assertCommandBehavior("editexam 1 et/2366", expectedMessage);

        expectedMessage = Exam.MESSAGE_TIME_INTERVAL_CONSTRAINTS;
        assertCommandBehavior("editexam 1 st/09:00 et/08:00", expectedMessage);

        expectedMessage = AddExamCommand.MESSAGE_DUPLICATE_EXAM;
        assertCommandBehavior(String.format("editexam 1 e/%s", e1.getExamName()), expectedMessage);
    }

}

