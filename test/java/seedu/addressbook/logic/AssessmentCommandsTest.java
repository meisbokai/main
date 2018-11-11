package seedu.addressbook.logic;

import static seedu.addressbook.common.Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_STATISTICS_DISPLAYED_INDEX;
import static seedu.addressbook.common.Messages.MESSAGE_WRONG_NUMBER_ARGUMENTS;

import static seedu.addressbook.logic.CommandAssertions.assertCommandBehavior;
import static seedu.addressbook.logic.CommandAssertions.assertCommandBehaviorForExam;
import static seedu.addressbook.logic.CommandAssertions.assertInvalidIndexBehaviorForCommand;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.TestDataHelper;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.assessment.AddAssessmentCommand;
import seedu.addressbook.commands.assessment.AddGradesCommand;
import seedu.addressbook.commands.assessment.DeleteAssessmentCommand;
import seedu.addressbook.commands.assessment.DeleteGradesCommand;
import seedu.addressbook.commands.assessment.DeleteStatisticsCommand;
import seedu.addressbook.commands.assessment.ListAssessmentCommand;
import seedu.addressbook.commands.assessment.ListStatisticsCommand;
import seedu.addressbook.commands.assessment.ViewGradesCommand;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.privilege.Privilege;
import seedu.addressbook.privilege.user.AdminUser;
import seedu.addressbook.storage.StorageFile;
import seedu.addressbook.stubs.StorageStub;

/**
 * For testing of Assessment-related Commands
 */
public class AssessmentCommandsTest {

    /**
     *  See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private AddressBook addressBook;
    private StatisticsBook statisticBook;

    @Before
    public void setUp() throws Exception {
        StorageFile saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath(),
                saveFolder.newFile("testExamFile.txt").getPath(),
                saveFolder.newFile("testStatisticsFile.txt").getPath());
        StorageStub stubFile = new StorageStub(saveFolder.newFile("testStubFile.txt").getPath(),
                saveFolder.newFile("testStubExamFile.txt").getPath(),
                saveFolder.newFile("testStubStatisticsFile.txt").getPath());

        addressBook = new AddressBook();
        ExamBook examBook = new ExamBook();
        statisticBook = new StatisticsBook();
        // Privilege set to admin to allow all commands.
        // Privilege restrictions are tested separately under PrivilegeTest.
        Privilege privilege = new Privilege(new AdminUser());

        saveFile.save(addressBook);
        saveFile.saveStatistics(statisticBook);
        Logic logic = new Logic(stubFile, addressBook, examBook, statisticBook, privilege);
        CommandAssertions.setData(saveFile, addressBook, logic, examBook, statisticBook);
    }

    @Test
    public void executeAddAssessment_validArgs_successful() throws Exception {
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
    public void executeAddAssessment_duplicateData_duplicateMessage() throws Exception {
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
    public void executeAddAssessment_invalidArgs_invalidMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssessmentCommand.MESSAGE_USAGE);
        assertCommandBehavior("addassess", expectedMessage);
        assertCommandBehavior("addassess ", expectedMessage);
    }

    @Test
    public void executeDeleteAssessment_invalidArgs_invalidMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssessmentCommand.MESSAGE_USAGE);
        assertCommandBehavior("deleteassess ", expectedMessage);
        assertCommandBehavior("deleteassess arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteAssessment_invalidIndex_invalidIndexMessage() throws Exception {
        assertInvalidIndexBehaviorForCommand("deleteassess", MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeListAssessments_validArgs_successfulList() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expected = helper.generateAddressBook(false, true);
        Assessment assessment = new Assessment("Math final");
        expected.addAssessment(assessment);
        addressBook.addAssessment(assessment);
        Assessment assessment2 = new Assessment("CG2271 Midterm");
        expected.addAssessment(assessment2);
        addressBook.addAssessment(assessment2);
        List<? extends Assessment> expectedList = List.of(assessment, assessment2);

        // prepare address book state
        helper.addToAddressBook(addressBook, false, true);

        assertCommandBehavior("listassess",
                Command.getMessageForAssessmentListShownSummary(expectedList),
                "",
                expected,
                false,
                Collections.emptyList(),
                true,
                expectedList,
                false);
    }

    @Test
    public void executeListAssessment_invalidArgs_invalidMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAssessmentCommand.MESSAGE_USAGE);
        assertCommandBehavior("listassess 1 2", expectedMessage);
        assertCommandBehavior("listassess any other arg", expectedMessage);
    }

    @Test
    public void executeAddGrades_invalidParsedArgs_invalidCommandMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradesCommand.MESSAGE_USAGE);

        assertCommandBehaviorForExam("addgrades 1 1 not_a_number", expectedMessage
        );
        assertCommandBehaviorForExam("addgrades 1 not_a_number 1", expectedMessage
        );
        assertCommandBehaviorForExam("addgrades 1 not_a_number not_a_number", expectedMessage
        );
        assertCommandBehaviorForExam("addgrades not_a_number 1 1", expectedMessage
        );
        assertCommandBehaviorForExam("addgrades not_a_number 1 not_a_number", expectedMessage
        );
        assertCommandBehaviorForExam("addgrades not_a_number not_a_number 1", expectedMessage
        );
        assertCommandBehaviorForExam("addgrades not_a_number not_a_number not_a_number", expectedMessage
        );
    }

    @Test
    public void executeAddGrades_noArgs_invalidCommandMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradesCommand.MESSAGE_USAGE);
        assertCommandBehaviorForExam("addgrades", expectedMessage);
        assertCommandBehaviorForExam("addgrades ", expectedMessage);
    }

    @Test
    public void executeAddGrades_invalidNumberOfArgs_invalidNumberMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_WRONG_NUMBER_ARGUMENTS, 3, 1,
                AddGradesCommand.MESSAGE_USAGE);
        assertCommandBehaviorForExam("addgrades 1", expectedMessage);

        expectedMessage = String.format(MESSAGE_WRONG_NUMBER_ARGUMENTS, 3, 2,
                AddGradesCommand.MESSAGE_USAGE);
        assertCommandBehaviorForExam("addgrades 1 1", expectedMessage);
    }

    @Test
    public void executeDeleteGrades_noArgs_invalidCommandMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradesCommand.MESSAGE_USAGE);
        assertCommandBehavior("deletegrades", expectedMessage);
        assertCommandBehavior("deletegrades ", expectedMessage);
    }

    @Test
    public void executeDeleteGrades_invalidNumberOfArgs_invalidNumberMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_WRONG_NUMBER_ARGUMENTS, 2, 1,
                DeleteGradesCommand.MESSAGE_USAGE);
        assertCommandBehavior("deletegrades 1", expectedMessage);

        expectedMessage = String.format(MESSAGE_WRONG_NUMBER_ARGUMENTS, 2, 3,
                DeleteGradesCommand.MESSAGE_USAGE);
        assertCommandBehavior("deletegrades 1 1 1", expectedMessage);
    }

    @Test
    public void executeDeleteGrades_invalidParsedArgs_invalidCommandMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradesCommand.MESSAGE_USAGE);
        assertCommandBehavior("deletegrades not_a_number 1", expectedMessage
        );
        assertCommandBehavior("deletegrades 1 not_a_number", expectedMessage
        );
        assertCommandBehavior("deletegrades not_a_number not_a_number", expectedMessage
        );
    }

    @Test
    public void executeViewGrades_invalidArgsFormat_invalidCommandMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGradesCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewgrades ", expectedMessage);
        assertCommandBehavior("viewgrades arg not number", expectedMessage);
    }

    @Test
    public void executeViewGrades_invalidIndex_invalidIndexMessage() throws Exception {
        assertInvalidIndexBehaviorForCommand("viewgrades");
    }

    @Test
    public void executeDeleteStatistics_invalidArgs_invalidMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatisticsCommand.MESSAGE_USAGE);
        assertCommandBehavior("deletestatistics ", expectedMessage);
        assertCommandBehavior("deletestatistics arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteStatistics_invalidIndex_invalidIndexMessage() throws Exception {
        assertInvalidIndexBehaviorForCommand("deletestatistics", MESSAGE_INVALID_STATISTICS_DISPLAYED_INDEX);
    }

    @Test
    public void executeListStatistics_invalidArgs_invalidMessage() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListStatisticsCommand.MESSAGE_USAGE);
        assertCommandBehavior("liststatistics 1 2", expectedMessage);
        assertCommandBehavior("liststatistics any other args", expectedMessage);
    }
}
