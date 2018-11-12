package seedu.addressbook.data.account;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Printable;
import seedu.addressbook.privilege.Privilege;

/**
 * Represents an account in the address book.
 */
public class Account implements Printable {
    private final String username;
    private final String password;
    private final Privilege privilege;

    public Account(String username, String password, String privilege) throws IllegalValueException {
        String trimmedName = username.trim();
        String trimmedPassword = password.trim();
        this.username = trimmedName;
        this.password = trimmedPassword;
        this.privilege = Privilege.getPrivilegeFromString(privilege);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilegePerson(Person person) {
        privilege.setMyPerson(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Account // instanceof handles nulls
                && this.username.equals(((Account) other).username)); // state check
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        // We only print the privilege levels as we do not want any users to see others username/password
        return privilege.getLevelAsString();
    }
}
