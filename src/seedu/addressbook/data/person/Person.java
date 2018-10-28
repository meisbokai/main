package seedu.addressbook.data.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.addressbook.data.account.Account;
import seedu.addressbook.data.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Account account;
    private Fees fees;
    private final Set<Tag> tags = new HashSet<>();
    private Attendance attendance;
    private List<Assessment> assessments;
    /**
     * Assumption: Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.attendance = new Attendance();
        this.fees = new Fees();
        this.assessments = new ArrayList<>();
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Account account) {
        this(name, phone, email, address, tags);
        this.account = account;
    }

    /**
     * Only update the fees when called in setAssessmentsCommand
     */
    public void setAssessments(Assessment assessment) {
        this.assessments.add(assessment);
    }

    /**
     * Only update the fees when called in AddFeesCommand
     * @param fees
     */
    public void setFees(Fees fees) {
        this.fees = fees;
    }

    /**
     * Replaces this person's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void removeAccount() {
        this.account = null;
    }

    public List<Assessment> getAssessments() {
        return new ArrayList<>(assessments);
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Fees getFees() {
        return fees;
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    @Override
    public Optional<Account> getAccount() {
        return Optional.ofNullable(account);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

    @Override
    public Attendance getAttendance() {
        return attendance;
    }

    /** Has a boolean to check if the date is a duplicate **/
    public boolean updateAttendanceMethod(String date, Boolean isPresent, Boolean overWrite) {
        boolean duplicateDate = attendance.addAttendance(date, isPresent, overWrite);
        return duplicateDate;
    }

    /** Method to allow user to view the attendance of a person **/
    public String viewAttendanceMethod() {
        return attendance.viewAttendance();
    }

    /** Replaces the attendance if there is already a duplicate **/
    public boolean replaceAttendanceMethod(String date, Boolean isPresent, Boolean overWrite) {
        boolean noDuplicateDate = !attendance.addAttendance(date, isPresent, overWrite);
        return noDuplicateDate;
    }

}
