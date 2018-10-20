package seedu.addressbook.data.account;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Printable;
import seedu.addressbook.privilege.Privilege;

/**
 * Represents a Account in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidPrivilegeName(String)}
 */
public class Account implements Printable {

    public static final String MESSAGE_PRIVILEGE_CONSTRAINTS = "Desired privilege level of %s does not exists";

    private final String username;
    private final String password;
    private final Privilege privilege;

    private Account(String username, String password, Privilege privilege) {
        String trimmedName = username.trim();
        String trimmedPassword = password.trim();
        this.username = trimmedName;
        this.password = trimmedPassword;
        this.privilege = privilege;
    }

    public Account(String username, String password, String privilege) throws IllegalValueException {
        this(username, password, Privilege.getPrivilegeFromString(privilege));
        if (!isValidPrivilegeName(privilege)) {
            final String message = String.format(MESSAGE_PRIVILEGE_CONSTRAINTS, privilege);
            throw new IllegalValueException(message);
        }
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

    /**
     * Returns true if a given string is a valid Privilege name.
     */
    private static boolean isValidPrivilegeName(String privilege) {
        return (Privilege.getPrivilegeFromString(privilege) != null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Account // instanceof handles nulls
                && this.username.equals(((Account) other).username)); // state check
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        return this.privilege.getLevelAsString();
    }
}
