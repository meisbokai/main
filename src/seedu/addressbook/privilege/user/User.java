package seedu.addressbook.privilege.user;

import seedu.addressbook.commands.Command;

import java.util.List;

/**
 This interface describes who the current user is
 */
public interface User {
    /**
     * Enum to describe privilege levels. Used only as String
     * */
    enum PrivilegeLevel { Basic, Tutor, Admin }

    String getPrivilegeLevelAsString();
    PrivilegeLevel getPrivilegeLevel();
    List<Command> getAllowedCommands();
    boolean isAllowedCommand (Command command);
}
