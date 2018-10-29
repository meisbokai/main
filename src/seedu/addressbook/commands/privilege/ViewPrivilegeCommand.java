package seedu.addressbook.commands.privilege;

import java.util.Optional;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.commandresult.CommandResult;
import seedu.addressbook.commands.commandresult.MessageType;
import seedu.addressbook.data.person.Person;

/**
 * Clears the address book.
 */
public class ViewPrivilegeCommand extends Command {

    public static final String COMMAND_WORD = "viewpri";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows your current privilege level.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_PRIVILEGE_FORMAT = "Your privilege is %1$s.";
    public static final String MESSAGE_LOGGED_IN = "You are logged in as: %s.\n";
    public static final String MESSAGE_NOT_LOGGED_IN = "You are not logged in.\n";

    @Override
    public CommandResult execute() {
        String feedbackToUser;
        final Optional<Person> optMyPerson = privilege.getMyPerson();
        if (optMyPerson.isPresent()) {
            feedbackToUser = String.format(MESSAGE_LOGGED_IN, optMyPerson.get().getName());
        } else {
            feedbackToUser = MESSAGE_NOT_LOGGED_IN;
        }
        feedbackToUser += String.format(MESSAGE_PRIVILEGE_FORMAT, privilege.getLevelAsString());
        return new CommandResult(feedbackToUser, MessageType.OUTPUT);
    }

    @Override
    public Category getCategory() {
        return Category.PRIVILEGE;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
