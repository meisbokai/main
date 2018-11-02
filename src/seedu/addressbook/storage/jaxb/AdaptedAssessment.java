package seedu.addressbook.storage.jaxb;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Assessment;

/**
 * JAXB-friendly adapted assessment data holder class.
 */
public class AdaptedAssessment {
    @XmlElement(required = true)
    private String examName;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedAssessment() {}

    /**
     * Converts a given Assessment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedAssessment
     */
    public AdaptedAssessment(Assessment source) {
        examName = source.getExamName();
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
        return Utils.isAnyNull(examName);
    }

    /**
     * Converts this jaxb-friendly adapted assessment object into the Assessment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assessment
     */
    public Assessment toModelType() throws IllegalValueException {
        final String examName = this.examName;
        return new Assessment(examName);
    }
}
