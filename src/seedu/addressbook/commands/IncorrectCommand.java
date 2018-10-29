package seedu.addressbook.commands;

import seedu.addressbook.commands.commandresult.CommandResult;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(feedbackToUser);
    }

    @Override
    public String getCommandUsageMessage() {
        return "ERROR, IncorrectCommand is executed";
    }
}
