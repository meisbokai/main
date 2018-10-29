package seedu.addressbook.commands.assessment;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.Grades;
import seedu.addressbook.data.person.Person;

import seedu.addressbook.data.person.UniquePersonList;

/**
 * Adds a grade to person based on person index and assessment index.
 */
public class AddGradesCommand extends Command {

    public static final String COMMAND_WORD = "addgrades";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds grades to a person based on person index"
            + " and grades index.\n\t"
            + "Parameters: PERSON_INDEX ASSESSMENT_INDEX Grade\n\t"
            + "Example: " + COMMAND_WORD
            + " 1 2 85";

    public static final String MESSAGE_ADD_GRADE_SUCCESS = "Grade has been added!";

    private final int assessIndex;
    private final int gradesVal;

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public AddGradesCommand() {
        assessIndex = -1;
        gradesVal = -1;
    }

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddGradesCommand (int targetPersonIndex, int targetAssessIndex, int gradesVal) {
        super (targetPersonIndex);
        assessIndex = targetAssessIndex;
        this.gradesVal = gradesVal;
    }

    @Override
    public CommandResult execute() {
        try {
            final Person person = addressBook.findPerson(getTargetReadOnlyPerson());

            final Assessment targetAssess = getTargetAssessment(assessIndex);
            person.addAssessment(targetAssess);

            Grades grade = new Grades(gradesVal);

            targetAssess.addGrade(person, grade);

            return new CommandResult(String.format(MESSAGE_ADD_GRADE_SUCCESS));

        } catch (UniquePersonList.PersonNotFoundException pnf) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (AssessmentIndexOutOfBoundsException aie) {
            return new CommandResult(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX);
        }

    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public Category getCategory() {
        return Category.ASSESSMENT;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }

}
