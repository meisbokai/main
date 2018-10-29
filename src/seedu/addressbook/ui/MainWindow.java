package seedu.addressbook.ui;

import static seedu.addressbook.common.Messages.MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE;
import static seedu.addressbook.common.Messages.MESSAGE_USING_EXAMS_FILE;
import static seedu.addressbook.common.Messages.MESSAGE_USING_STATISTICS_FILE;
import static seedu.addressbook.common.Messages.MESSAGE_USING_STORAGE_FILE;
import static seedu.addressbook.common.Messages.MESSAGE_WELCOME_ASCII_ART;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.commands.general.ExitCommand;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.formatter.Formatter;
import seedu.addressbook.formatter.PersonListFormat;
import seedu.addressbook.logic.Logic;

/**
 * Main Window of the GUI.
 */
public class MainWindow {

    private Logic logic;
    private Stoppable mainApp;

    @FXML
    private TextField commandInput;


    @FXML
    private TextArea outputConsole;

    @FXML
    private TextArea asciiArt;

    @FXML
    private TextArea statusConsole;

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setMainApp(Stoppable mainApp) {
        this.mainApp = mainApp;
    }

    /** Returns true of the result given is the result of an exit command */
    private boolean isExitCommand(CommandResult result) {
        return result.getStatusConsoleMessage().equals(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    /** Clears the command input box */
    private void clearCommandInput() {
        commandInput.setText("");
    }

    /** Clears the output display area */
    private void clearOutputConsole() {
        outputConsole.clear();
    }

    /** Clears the status display area */
    private void clearStatusConsole() {
        statusConsole.clear();
    }

    /** Displays the result of a command execution to the user. */
    private void displayResult(CommandResult result) {
        clearOutputConsole();
        closeAsciiArt();
        final Optional<List<? extends ReadOnlyPerson>> resultPersons = result.getRelevantPersons();

        resultPersons.ifPresent((p) -> display(p, result.getPersonListFormat()));
        //TODO: Clean up Optional code
        final Optional<List<? extends ReadOnlyExam>> resultExams = result.getRelevantExams();
        final Optional<List<? extends Assessment>> resultAssessment = result.getRelevantAssessments();
        if (resultExams.isPresent()) {
            displayExams(resultExams.get());
        } else if (resultAssessment.isPresent()) {
            displayAssessments(resultAssessment.get());
        }
        display(result.getOutputConsoleMessage());
    }
    /** Displays the welcome message**/
    public void displayWelcomeMessage(String version, String storageFilePath, String examsFilePath,
                                      String statisticsFilePath) {
        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
        String examsFileInfo = String.format(MESSAGE_USING_EXAMS_FILE, examsFilePath);
        String statisticsFileInfo = String.format(MESSAGE_USING_STATISTICS_FILE, statisticsFilePath);
        displayAscii(MESSAGE_WELCOME_ASCII_ART);
        display(version, MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE, storageFileInfo, examsFileInfo,
                statisticsFileInfo);
    }

    /**
     * Displays the list of persons in the output display area, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void display(List<? extends ReadOnlyPerson> persons, PersonListFormat personListFormat) {
        display(Formatter.format(persons, personListFormat));
    }
    /**
     * Displays the given messages on the output display area, after formatting appropriately.
     */
    private void display(String... messages) {
        outputConsole.setText(outputConsole.getText() + Formatter.format(messages));
    }

    /**
     * Displays the given messages on the ASCII art area.
     */
    private void displayAscii(String messages) {
        asciiArt.setText(messages);
    }

    /**
     * Displays the list of exams in the output display area, formatted as an indexed list.
     */
    private void displayExams(List<? extends ReadOnlyExam> exams) {
        display(Formatter.formatExam(exams));
    }

    /**
     * Displays the given messages on the output display area, after formatting appropriately.
     */
    private void displayStatus(String message) {
        clearStatusConsole();
        statusConsole.setText(message);
    }

    /**
     * Displays the given messages on the output display area, after formatting appropriately.
     */
    private void handleDisplay(CommandResult result) {
        clearStatusConsole();
        clearCommandInput();
        if (result.hasStatusMessage()) {
            displayStatus(result.getStatusConsoleMessage());
        }
        if (result.hasOutputMessage()) {
            displayResult(result);
        }
    }
    /**
     * Displays the list of exams in the output display area, formatted as an indexed list.
     */
    private void displayAssessments(List<? extends Assessment> assessments) {
        display(Formatter.formatAssessments(assessments));
    }

    private void closeAsciiArt() {
        asciiArt.setText("");
        asciiArt.setManaged(false);
    }

    /** Reads the user command on the CLI **/
    @FXML
    void onCommand(ActionEvent event) {
        try {
            String userCommandText = commandInput.getText();
            CommandResult result = logic.execute(userCommandText);
            handleDisplay(result);
            if (isExitCommand(result)) {
                clearOutputConsole();
                exitApp();
            }
        } catch (Exception e) {
            display(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /** Exits the app after a given delay*/
    private void exitApp() {
        final int delayInMillis = 500;
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    mainApp.stop();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, delayInMillis);
    }
}
