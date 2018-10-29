package seedu.addressbook;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.addressbook.logic.Logic;
import seedu.addressbook.ui.Gui;
import seedu.addressbook.ui.Stoppable;

/**
 *  Main entry point to the application.
 */
public class Main extends Application implements Stoppable {

    /** Version info of the program. */
    private static final String VERSION = "ClassRepo - Version 1.3";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Gui gui = new Gui(new Logic(), VERSION);
            gui.start(primaryStage, this);
        } catch (Exception e) {
            Logger logger = Logger.getLogger("Foo");
            logger.log(Level.WARNING, e.getMessage());
            throw e;
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}


