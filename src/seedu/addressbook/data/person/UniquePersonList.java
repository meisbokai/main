package seedu.addressbook.data.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;
import seedu.addressbook.ui.Formatter;

/**
 * A list of persons. Does not allow null elements or duplicates.
 *
 * @see Person#equals(Object)
 * @see Utils#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

    private final List<Person> internalList = new ArrayList<>();

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePersonException extends DuplicateDataException {
        protected DuplicatePersonException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class PersonNotFoundException extends Exception {}

    /**
     * Constructs empty person list.
     */
    public UniquePersonList() {}

    /**
     * Constructs a person list with the given persons.
     */
    public UniquePersonList(Person... persons) throws DuplicatePersonException {
        final List<Person> initialTags = Arrays.asList(persons);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicatePersonException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param persons a collection of persons
     * @throws DuplicatePersonException if the {@code persons} contains duplicate persons
     */
    public UniquePersonList(Collection<Person> persons) throws DuplicatePersonException {
        if (!Utils.elementsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }
        internalList.addAll(persons);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniquePersonList(UniquePersonList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Unmodifiable java List view with elements cast as immutable {@link ReadOnlyPerson}s.
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyPerson> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }

    /**
     * Checks if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Person toAdd) throws DuplicatePersonException {
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Finds the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public Person find(ReadOnlyPerson person) throws PersonNotFoundException {
        //TODO: Fix potato
        for (Person p: internalList) {
            if (p.equals(person)) {
                return p;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * loops through list and appends data to string person.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public String loopFees(ReadOnlyPerson person) throws PersonNotFoundException {
        //TODO: Fix potato
        final StringBuilder builder = new StringBuilder();
        for (Person p: internalList) {
            final String stringChain = Formatter.getPrintableString(
                    true,
                    p.getName(),
                    p.getFees());
            builder.append(stringChain);
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Clears all persons in list.
     */
    public void clear() {
        internalList.clear();
    }

    /** Finds and returns the Person who has the given username in its Account
     * @param username to be matched to a person.
     * @return The Person who matches the username. This should be guaranteed to be unique.
     * @throws PersonNotFoundException Person cannot be found with the given username in internalList
     */
    public Person findPersonByUsername(String username) throws PersonNotFoundException {
        //TODO: Fix potato
        for (Person p: internalList) {
            if (p.getAccount().isPresent() && p.getAccount().get().getUsername().equals(username)) {
                return p;
            }
        }
        throw new PersonNotFoundException();
    }

    /**Checks if UniquePersonList holds a Person who has given username in its Account
     * @param username of the person to be associated with.
     * @return true if such a Person exists. False otherwise
     */
    public Boolean containsPersonWithUsername(String username) {
        try {
            findPersonByUsername(username);
            return true;
        } catch (PersonNotFoundException pne) {
            return false;
        }
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals((
                (UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
