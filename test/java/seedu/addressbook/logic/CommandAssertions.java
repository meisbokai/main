package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_EXAM_DISPLAYED_INDEX;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.Collections;
import java.util.List;

import seedu.addressbook.TestDataHelper;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.commands.commandresult.MessageType;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.storage.StorageFile;

/**
 * This class holds the commonly used assertions of commands.
 * */
public class CommandAssertions {
    private static StorageFile saveFile;
    private static AddressBook addressBook;
    private static Logic logic;
    private static ExamBook examBook;
    private static StatisticsBook statisticsBook;

    public static void setData(StorageFile saveFile, AddressBook addressBook, Logic logic) {
        CommandAssertions.saveFile = saveFile;
        CommandAssertions.addressBook = addressBook;
        CommandAssertions.logic = logic;
    }

    public static void setData(StorageFile saveFile, AddressBook addressBook, Logic logic, ExamBook examBook,
                               StatisticsBook statisticsBook) {
        setData(saveFile, addressBook, logic);
        CommandAssertions.examBook = examBook;
        CommandAssertions.statisticsBook = statisticsBook;
    }

    /**
     * Executes the command and confirms that the result messages are correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, AddressBook, boolean, List)
     */
    public static void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, AddressBook.empty(),
                false, Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result messages are correct.
     * The `exam book` and the `last shown list` are given.
     * @see #assertCommandBehavior(String, String, ExamBook, boolean, List)
     */
    public static void assertCommandBehavior(String inputCommand, String expectedStatusMessage,
                                             ExamBook exambook, List<ReadOnlyExam> lastShownList) throws Exception {
        assertCommandBehavior(inputCommand, expectedStatusMessage, exambook,
                false, lastShownList);
    }

    /**
     * Executes the command and confirms that the status and output messages are correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, String, AddressBook, boolean, List)
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             String expectedOutputMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedStatusMessage, expectedOutputMessage,
                AddressBook.empty(), false, Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result messages are correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @param messageType specify which console the given message is supposed to be written to
     * @see #assertCommandBehavior(String, String, AddressBook, boolean, List)
     * @see #assertCommandBehavior(String, String, String, AddressBook, boolean, List)
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedMessage,
                                             MessageType messageType) throws Exception {
        if (messageType.equals(MessageType.STATUS)) {
            assertCommandBehavior(inputCommand, expectedMessage, AddressBook.empty(),
                    false, Collections.emptyList());
        } else if (messageType.equals(MessageType.OUTPUT)) {
            assertCommandBehavior(inputCommand, "", expectedMessage, AddressBook.empty(),
                    false, Collections.emptyList());
        }
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * Assumes the storage to be tested
     * @see #assertCommandBehavior(String, String, AddressBook, boolean, List, boolean)
     */
    public static void assertCommandBehavior(String inputCommand,
                                      String expectedStatusMessage,
                                      AddressBook expectedAddressBook,
                                      boolean isRelevantPersonsExpected,
                                      List<? extends ReadOnlyPerson> lastShownList) throws Exception {
        assertCommandBehavior(inputCommand,
                expectedStatusMessage,
                expectedAddressBook,
                isRelevantPersonsExpected,
                lastShownList,
                false);
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * Assumes the storage to not be tested
     * @param messageType specify which console the given message is supposed to be written to
     * @see #assertCommandBehavior(String, String, AddressBook, boolean, List, boolean)
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedMessage,
                                             MessageType messageType,
                                             AddressBook expectedAddressBook,
                                             boolean isRelevantPersonsExpected,
                                             List<? extends ReadOnlyPerson> lastShownList) throws Exception {
        if (messageType.equals(MessageType.STATUS)) {
            assertCommandBehavior(inputCommand,
                    expectedMessage,
                    "",
                    expectedAddressBook,
                    isRelevantPersonsExpected,
                    lastShownList,
                    false);
        } else if (messageType.equals(MessageType.OUTPUT)) {
            assertCommandBehavior(inputCommand,
                    "",
                    expectedMessage,
                    expectedAddressBook,
                    isRelevantPersonsExpected,
                    lastShownList,
                    false);
        }
    }

    /**
     * Executes the command and confirms that the output and status messages is correct and
     * Assumes the storage to not be tested
     * @see #assertCommandBehavior(String, String, String, AddressBook, boolean, List, boolean)
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             String expectedOutputMessage,
                                             AddressBook expectedAddressBook,
                                             boolean isRelevantPersonsExpected,
                                             List<? extends ReadOnlyPerson> lastShownList) throws Exception {
        assertCommandBehavior(inputCommand,
                expectedStatusMessage,
                expectedOutputMessage,
                expectedAddressBook,
                isRelevantPersonsExpected,
                lastShownList,
                false);
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * sets the expectedOutputMessage to be empty.
     * @see #assertCommandBehavior(String, String, String, AddressBook, boolean, List, boolean)
     */
    public static void assertCommandBehavior(String inputCommand,
                                      String expectedStatusMessage,
                                      AddressBook expectedAddressBook,
                                      boolean isRelevantPersonsExpected,
                                      List<? extends ReadOnlyPerson> lastShownList,
                                      boolean hasStorageTested) throws Exception {
        assertCommandBehavior(inputCommand, expectedStatusMessage, "", expectedAddressBook,
                isRelevantPersonsExpected, lastShownList, hasStorageTested);
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the internal 'last shown list' matches the {@code lastShownList} <br>
     *
     *      if storage after execution is to be tested
     *      - the storage file content matches data in {@code expectedAddressBook} <br>
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             String expectedOutputMessage,
                                             AddressBook expectedAddressBook,
                                             boolean isRelevantPersonsExpected,
                                             List<? extends ReadOnlyPerson> lastShownList,
                                             boolean writesToFile) throws Exception {
        assertCommandBehavior(inputCommand, expectedStatusMessage, expectedOutputMessage, expectedAddressBook,
                isRelevantPersonsExpected, lastShownList, false, Collections.emptyList(),
                writesToFile);

    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the internal 'last shown list' matches the {@code lastShownList} <br>
     *
     *      if the command will write to file
     *      - the storage file content matches data in {@code expectedAddressBook} <br>
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             String expectedOutputMessage,
                                             AddressBook expectedAddressBook,
                                             boolean isRelevantPersonsExpected,
                                             List<? extends ReadOnlyPerson> lastShownList,
                                             boolean isRelevantAssessmentsExpected,
                                             List<? extends Assessment> lastShownAssessmentsList,
                                             boolean hasStorageTested) throws Exception {
        // Injects the saveFile object to check
        if (hasStorageTested) {
            logic.setStorage(saveFile);
        }
        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedOutputMessage, r.getOutputConsoleMessage());
        assertEquals(expectedStatusMessage, r.getStatusConsoleMessage());
        assertEquals(r.getRelevantPersons().isPresent(), isRelevantPersonsExpected);
        if (isRelevantPersonsExpected) {
            assertEquals(lastShownList, r.getRelevantPersons().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedAddressBook, addressBook);
        assertEquals(lastShownList, logic.getLastShownList());
        if (hasStorageTested) {
            assertEquals(addressBook, saveFile.load());
        }
        if (isRelevantAssessmentsExpected) {
            assertEquals(lastShownAssessmentsList, logic.getLastShownAssessmentList());
        }
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the internal 'last shown list' matches the {@code lastShownList} <br>
     *
     *      if the command will write to file
     *      - the storage file content matches data in {@code expectedAddressBook} <br>
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             String expectedOutputMessage,
                                             StatisticsBook expectedStatisticsBook,
                                             boolean isRelevantPersonsExpected,
                                             List<? extends ReadOnlyPerson> lastShownList,
                                             boolean isRelevantAssessmentsExpected,
                                             List<? extends Assessment> lastShownAssessmentsList,
                                             boolean hasStorageTested) throws Exception {
        // Injects the saveFile object to check
        if (hasStorageTested) {
            logic.setStorage(saveFile);
        }
        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedOutputMessage, r.getOutputConsoleMessage());
        assertEquals(expectedStatusMessage, r.getStatusConsoleMessage());
        assertEquals(r.getRelevantPersons().isPresent(), isRelevantPersonsExpected);
        if (isRelevantPersonsExpected) {
            assertEquals(lastShownList, r.getRelevantPersons().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedStatisticsBook, statisticsBook);
        assertEquals(lastShownList, logic.getLastShownList());
        if (hasStorageTested) {
            assertEquals(statisticsBook, saveFile.load());
        }
        if (isRelevantAssessmentsExpected) {
            assertEquals(lastShownAssessmentsList, logic.getLastShownAssessmentList());
        }
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * Assumes the storage to not be tested
     * @see #assertCommandBehavior(String, String, ExamBook, boolean, List, boolean)
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             ExamBook expectedExamBook,
                                             boolean isRelevantExamsExpected,
                                             List<? extends ReadOnlyExam> lastShownList) throws Exception {
        assertCommandBehavior(inputCommand,
                expectedStatusMessage,
                expectedExamBook,
                isRelevantExamsExpected,
                lastShownList,
                false);
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * sets the expectedOutputMessage to be empty.
     * @see #assertCommandBehavior(String, String, String, ExamBook, boolean, List, boolean)
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             ExamBook expectedExamBook,
                                             boolean isRelevantExamsExpected,
                                             List<? extends ReadOnlyExam> lastShownList,
                                             boolean hasStorageTested) throws Exception {
        assertCommandBehavior(inputCommand, expectedStatusMessage, "", expectedExamBook,
                isRelevantExamsExpected, lastShownList, hasStorageTested);
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal exam book data are same as those in the {@code expectedExamBook} <br>
     *      - the internal 'last shown list' matches the {@code lastShownList} <br>
     *
     *      if storage after execution is to be tested
     *      - the storage file content matches data in {@code expectedExamBook} <br>
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             String expectedOutputMessage,
                                             ExamBook expectedExamBook,
                                             boolean isRelevantExamsExpected,
                                             List<? extends ReadOnlyExam> lastShownList,
                                             boolean hasStorageTested) throws Exception {
        // If we need the storage to be tested
        // Injects the saveFile object to check
        if (hasStorageTested) {
            logic.setStorage(saveFile);
        }
        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedStatusMessage, r.getStatusConsoleMessage());
        assertEquals(expectedOutputMessage, r.getOutputConsoleMessage());
        assertEquals(r.getRelevantExams().isPresent(), isRelevantExamsExpected);
        if (isRelevantExamsExpected) {
            assertEquals(lastShownList, r.getRelevantExams().get());
        }
        //Confirm the state of data is as expected
        assertEquals(expectedExamBook, examBook);
        assertEquals(lastShownList, logic.getLastShownExamList());
        if (hasStorageTested) {
            assertEquals(examBook, saveFile.loadExam());
        }
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * also confirms that the following two parts of the Logic object's state are as expected:<br>
     *      - the internal statistics book data are same as those in the {@code expectedStatisticsBook} <br>
     *
     *      if storage after execution is to be tested
     *      - the storage file content matches data in {@code expectedStatisticsBook} <br>
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             StatisticsBook expectedStatisticsBook,
                                             boolean hasStorageTested) throws Exception {
        // If we need the storage to be tested
        // Injects the saveFile object to check
        if (hasStorageTested) {
            logic.setStorage(saveFile);
        }
        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedStatusMessage, r.getStatusConsoleMessage());

        //Confirm the state of data is as expected
        assertEquals(expectedStatisticsBook, statisticsBook);
        if (hasStorageTested) {
            assertEquals(statisticsBook, saveFile.loadStatistics());
        }
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * Assumes the storage to be tested and the expectedOutputMessage to be empty
     * @see #assertCommandBehavior(String, String, String, AddressBook, ExamBook, boolean, boolean, List, List, boolean)
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             AddressBook expectedAddressBook,
                                             ExamBook expectedExamBook,
                                             boolean isRelevantPersonsExpected,
                                             boolean isRelevantExamsExpected,
                                             List<? extends ReadOnlyPerson> lastShownList,
                                             List<? extends ReadOnlyExam> lastShownExamList) throws Exception {
        assertCommandBehavior(inputCommand,
                expectedStatusMessage,
                "",
                expectedAddressBook,
                expectedExamBook,
                isRelevantPersonsExpected,
                isRelevantExamsExpected,
                lastShownList,
                lastShownExamList,
                true);
    }

    /**
     * Executes the command and confirms that the result messages are correct and
     * also confirms that the following six parts of the Logic object's state are as expected:<br>
     *     - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *     - the internal exam book data are same as those in the {@code expectedExamBook} <br>
     *     - the internal 'last shown list' matches the {@code lastShownList} <br>
     *     - the internal 'last shown exam list' matches the {@code lastShownExamList} <br>
     *
     *     if storage after execution is to be tested
     *     - the storage file content matches data in {@code expectedAddressBook} <br>
     *     - the storage exam file content matches data in {@code expectedExamBook} <br>
     */
    public static void assertCommandBehavior(String inputCommand,
                                             String expectedStatusMessage,
                                             String expectedOutputMessage,
                                             AddressBook expectedAddressBook,
                                             ExamBook expectedExamBook,
                                             boolean isRelevantPersonsExpected,
                                             boolean isRelevantExamsExpected,
                                             List<? extends ReadOnlyPerson> lastShownList,
                                             List<? extends ReadOnlyExam> lastShownExamList,
                                             boolean hasStorageTested) throws Exception {
        // If we need the storage to be tested
        // Injects the saveFile object to check
        if (hasStorageTested) {
            logic.setStorage(saveFile);
        }
        //Execute the command
        CommandResult r = logic.execute(inputCommand);
        //Confirm the result contains the right data
        assertEquals(expectedStatusMessage, r.getStatusConsoleMessage());
        assertEquals(expectedOutputMessage, r.getOutputConsoleMessage());
        assertEquals(r.getRelevantPersons().isPresent(), isRelevantPersonsExpected);
        assertEquals(r.getRelevantExams().isPresent(), isRelevantExamsExpected);
        if (isRelevantPersonsExpected) {
            assertEquals(lastShownList, r.getRelevantPersons().get());
        }
        if (isRelevantExamsExpected) {
            assertEquals(lastShownExamList, r.getRelevantExams().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedExamBook, examBook);
        assertEquals(expectedAddressBook, addressBook);
        if (hasStorageTested) {
            assertEquals(addressBook, saveFile.load());
            assertEquals(examBook, saveFile.loadExam());
        }
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the last shown list, using visible index.
     * Used for commands in the form of COMMAND_WORD INDEX
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    public static void assertInvalidIndexBehaviorForCommand(String commandWord) throws Exception {
        assertInvalidIndexBehaviorForCommand(commandWord, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the last shown list, using visible index.
     * Used for commands in the form of COMMAND_WORD INDEX
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    public static void assertInvalidIndexBehaviorForCommand(String commandWord, String messageFormat) throws Exception {
        final String[] commands = {commandWord + " 0", commandWord + " -1", commandWord + " 3"};
        assertInvalidIndexBehaviour(commands, messageFormat);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the last shown list, using visible index.
     * Used for commands in the form of COMMAND_WORD PREFIX INDEX SUFFIX
     * PREFIX and SUFFIX can be empty
     * @param commandWord of the command.
     * @param prefix containing required information to enter before the INDEX.
     * @param suffix containing required information to enter after the INDEX.
     */
    public static void assertInvalidIndexBehaviorForCommand(String commandWord, String prefix,
                                                            String suffix) throws Exception {
        final String[] commands = {String.format("%s %s 0 %s", commandWord, prefix, suffix),
                String.format("%s %s -1 %s", commandWord, prefix, suffix),
                String.format("%s %s 3 %s", commandWord, prefix, suffix)};
        assertInvalidIndexBehaviour(commands, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command array
     * targeting a single person in the last shown list, using visible index.
     *  @param commands to test assuming it targets a single person in the last shown list based on visible index.
     */
    private static void assertInvalidIndexBehaviour(String[] commands, String messageFormat) throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Person> lastShownList = helper.generatePersonList(false, true);

        logic.setLastShownList(lastShownList);

        for (String command: commands) {
            assertCommandBehavior(command, messageFormat,
                    AddressBook.empty(), false, lastShownList);
        }
    }

    /**
     * Executes the command and confirms that the result messages are correct.
     * Both the 'exam book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ExamBook, boolean, List)
     */
    public static void assertCommandBehaviorForExam(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, ExamBook.empty(),
                false, Collections.emptyList());
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single exam in the last shown list, using visible index.
     * Used for commands in the form of COMMAND_WORD INDEX
     * @param commandWord to test assuming it targets a single exam in the last shown list based on visible index.
     */
    public static void assertInvalidIndexBehaviorForExamCommand(String commandWord) throws Exception {
        final String[] commands = {commandWord + " 0", commandWord + " -1", commandWord + " 3"};
        assertInvalidIndexBehaviourForExam(commands);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single exam in the last shown list, using visible index.
     * Used for commands in the form of COMMAND_WORD PREFIX INDEX SUFFIX
     * PREFIX and SUFFIX can be empty
     * @param commandWord of the command.
     * @param prefix containing required information to enter before the INDEX.
     * @param suffix containing required information to enter after the INDEX.
     */
    public static void assertInvalidIndexBehaviorForExamCommand(String commandWord,
                                                                String prefix, String suffix) throws Exception {
        final String[] commands = {String.format("%s %s 0 %s", commandWord, prefix, suffix),
                String.format("%s %s -1 %s", commandWord, prefix, suffix),
                String.format("%s %s 3 %s", commandWord, prefix, suffix)};
        assertInvalidIndexBehaviourForExam(commands);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command array
     * targeting a single person in the last shown list, using visible index.
     *
     * @param commands to test assuming it targets a single person in the last shown list based on visible index.
     */
    private static void assertInvalidIndexBehaviourForExam(String[] commands) throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Exam> lastShownList = helper.generateExamList(false, true);

        logic.setLastShownExamList(lastShownList);

        for (String command: commands) {
            assertCommandBehavior(command, MESSAGE_INVALID_EXAM_DISPLAYED_INDEX,
                    ExamBook.empty(), false, lastShownList);
        }
    }
}
