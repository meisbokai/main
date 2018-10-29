package seedu.addressbook.storage.jaxb;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniqueAssessmentsList;
import seedu.addressbook.data.person.UniquePersonList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly adapted address book data holder class.
 */
@XmlRootElement(name = "AddressBook")
public class AdaptedAddressBook {

    @XmlElement
    private List<AdaptedPerson> persons = new ArrayList<>();

    @XmlElement
    private List<Assessment> assessments = new ArrayList<>();

    @XmlElement(name = "password", defaultValue = "default_pw")
    private String password;

    @XmlAttribute
    private boolean isPermAdmin;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedAddressBook() {}
    /**
     * Converts a given AddressBook into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedAddressBook
     */
    public AdaptedAddressBook(AddressBook source) {
        persons = new ArrayList<>();
        source.getAllPersons().forEach(person -> persons.add(new AdaptedPerson(person)));
        password = source.getMasterPassword();
        isPermAdmin = source.isPermAdmin();
    }


    /**
     * Returns true if any required field is missing.
     *
     * JAXB does not enforce (required = true) without a given XML schema.
     * Since we do most of our validation using the data class constructors, the only extra logic we need
     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
     * so we check for that.
     */
    public boolean isAnyRequiredFieldMissing() {

        return persons.stream().anyMatch(AdaptedPerson::isAnyRequiredFieldMissing);
    }


    /**
     * Converts this jaxb-friendly {@code AdaptedAddressBook} object into the corresponding(@code AddressBook} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public AddressBook toModelType() throws IllegalValueException {
        final List<Person> personList = new ArrayList<>();
        for (AdaptedPerson person : persons) {
            personList.add(person.toModelType());
        }
        final String masterPassword = password;
        final List<Assessment> assessmentList = new ArrayList<>();
        for (Assessment assessment : assessments) {
            assessmentList.add(assessment);
        }
        final AddressBook ab = new AddressBook(new UniquePersonList(personList),
                new UniqueAssessmentsList(assessmentList),
                masterPassword);
        ab.setPermAdmin(isPermAdmin);
        return ab;
    }
}
