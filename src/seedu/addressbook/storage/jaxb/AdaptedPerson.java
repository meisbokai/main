package seedu.addressbook.storage.jaxb;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.account.Account;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.details.Address;
import seedu.addressbook.data.person.details.Email;
import seedu.addressbook.data.person.details.Name;
import seedu.addressbook.data.person.details.Phone;
import seedu.addressbook.data.tag.Tag;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * JAXB-friendly adapted person data holder class.
 */
public class AdaptedPerson {
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private AdaptedContactDetail phone;
    @XmlElement(required = true)
    private AdaptedContactDetail email;
    @XmlElement(required = true)
    private AdaptedContactDetail address;
    @XmlElement(required = true)
    private AdaptedContactDetail fees;
    @XmlElement(required = true)
    private String dueDate;
//    @XmlElement(required = true)
//    private AdaptedAttendance attendance;

    @XmlElement
    private List<AdaptedExam> exams = new ArrayList<>();

    @XmlElement
    private List<AdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private AdaptedAccount account;
    /**
     * JAXB-friendly adapted contact detail data holder class.
     */
    private static class AdaptedContactDetail {
        @XmlValue
        private String value;
        @XmlAttribute(required = true)
        private boolean isPrivate;

    }

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedPerson
     */
    public AdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;

        phone = new AdaptedContactDetail();
        phone.isPrivate = source.getPhone().isPrivate();
        phone.value = source.getPhone().value;

        email = new AdaptedContactDetail();
        email.isPrivate = source.getEmail().isPrivate();
        email.value = source.getEmail().value;

        address = new AdaptedContactDetail();
        address.isPrivate = source.getAddress().isPrivate();
        address.value = source.getAddress().value;

        fees = new AdaptedContactDetail();
        fees.isPrivate = source.getFees().isPrivate();
        fees.value = source.getFees().value;

        dueDate = source.getFees().duedate;

//        attendance = new AdaptedAttendance(source.getAttendance());

        exams = new ArrayList<>();
        for (Exam exam : source.getExams()) {
            exams.add(new AdaptedExam(exam));
        }

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new AdaptedTag(tag));
        }
        if (source.getAccount().isPresent()) {
            account = new AdaptedAccount(source.getAccount().get());
        }
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
        for (AdaptedTag tag : tagged) {
            if (tag.isAnyRequiredFieldMissing()) {
                return true;
            }
        }

        for (AdaptedExam exam : exams) {
            if (exam.isAnyRequiredFieldMissing()) {
                return true;
            }
        }

        if (account != null && account.isAnyRequiredFieldMissing()) {
            return true;
        }

        // second call only happens if phone/email/address are all not null
        return Utils.isAnyNull(name, phone, email, address, fees)
                || Utils.isAnyNull(phone.value, email.value, address.value, fees.value);
    }

    /**
     * Converts this jaxb-friendly adapted person object into the Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final Set<Tag> tags = new HashSet<>();
        for (AdaptedTag tag : tagged) {
            tags.add(tag.toModelType());
        }
        final Set<Exam> examList = new HashSet<>();
        for (AdaptedExam exam : exams) {
            examList.add(exam.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone.value, this.phone.isPrivate);
        final Email email = new Email(this.email.value, this.email.isPrivate);
        final Address address = new Address(this.address.value, this.address.isPrivate);
        final Fees fees = new Fees(this.fees.value, this.dueDate);
        Optional<AdaptedAccount> optAccount = Optional.ofNullable(account);

        if (!optAccount.isPresent()) {
            final Person person = new Person(name, phone, email, address, tags);
            person.setFees(fees);
            return new Person(name, phone, email, address, tags, examList);
        } else {
            final Account account = this.account.toModelType();
            final Person person = new Person(name, phone, email, address, tags, account, examList);
            person.setFees(fees);
            account.setPrivilegePerson(person);
            return new Person(name, phone, email, address, tags, account, examList);
        }
    }
}
