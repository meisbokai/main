package seedu.addressbook.data;

import java.util.Optional;

import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniqueAssessmentsList;
import seedu.addressbook.data.person.UniqueAssessmentsList.AssessmentNotFoundException;
import seedu.addressbook.data.person.UniqueAssessmentsList.DuplicateAssessmentException;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;



/**
 * Represents the entire address book. Contains the data of the address book.
 */
public class AddressBook {

    public static final String DEFAULT_MASTER_PASSWORD = "default_pw";
    private final UniquePersonList allPersons;
    private final UniqueAssessmentsList allAssessments;
    private String masterPassword;

    /**
     * Creates an empty address book.
     */
    public AddressBook() {
        allPersons = new UniquePersonList();
        allAssessments = new UniqueAssessmentsList();
        masterPassword = DEFAULT_MASTER_PASSWORD;
    }

    /**
     * Constructs an address book with the given data.
     *
     * @param persons external changes to this will not affect this address book
     * @param masterPassword
     */
    public AddressBook(UniquePersonList persons, UniqueAssessmentsList assessments, String masterPassword) {
        allPersons = new UniquePersonList(persons);
        allAssessments = new UniqueAssessmentsList(assessments);
        this.masterPassword = Optional.ofNullable(masterPassword)
                .orElse(DEFAULT_MASTER_PASSWORD);
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public static AddressBook empty() {
        return new AddressBook();
    }

    /**
     * Adds a person to the address book.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException {
        allPersons.add(toAdd);
    }

    /**
     * Adds an assessment to the address book.
     *
     * @throws DuplicateAssessmentException if an equivalent person already exists.
     */
    public void addAssessment(Assessment toAdd) throws DuplicateAssessmentException {
        allAssessments.add(toAdd);
    }

    /**
     * Checks if an equivalent person exists in the address book.
     */
    public boolean containsPerson(ReadOnlyPerson key) {
        return allPersons.contains(key);
    }

    /**
     * Removes the equivalent person from the address book.
     *
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public void removePerson(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        allPersons.remove(toRemove);
    }

    /**
     * Removes the equivalent assessment from the address book.
     *
     * @throws AssessmentNotFoundException if no such Assessment could be found.
     */
    public void removeAssessment(Assessment toRemove) throws AssessmentNotFoundException {
        allAssessments.remove(toRemove);
    }

    public Person findPersonByUsername(String username) throws PersonNotFoundException {
        return allPersons.findPersonByUsername(username);
    }

    public Boolean containsPersonWithUsername(String username) {
        return allPersons.containsPersonWithUsername(username);
    }

    /**
     * Finds and returns the given person in the AddressBook
     *
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public Person findPerson(ReadOnlyPerson person) throws PersonNotFoundException {
        return allPersons.find(person);
    }

    /**
     * Clears all persons from the address book.
     */
    public void clear() {
        allPersons.clear();
    }

    /**
     * Defensively copied UniquePersonList of all persons in the address book at the time of the call.
     */
    public UniquePersonList getAllPersons() {
        return new UniquePersonList(allPersons);
    }

    /**
     * Defensively copied UniqueAssessmentList of all assessments in the address book at the time of the call.
     */
    public UniqueAssessmentsList getAllAssessments() {
        return new UniqueAssessmentsList(allAssessments);
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.allPersons.equals(((AddressBook) other).allPersons));
    }

    @Override
    public int hashCode() {
        return allPersons.hashCode();
    }
}
