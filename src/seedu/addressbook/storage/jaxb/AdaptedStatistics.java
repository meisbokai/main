package seedu.addressbook.storage.jaxb;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.AssignmentStatistics;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly adapted assignment statistics data holder class.
 */
public class AdaptedStatistics {
    @XmlAttribute(required = true)
    private Boolean isPrivate;
    @XmlElement(required = true)
    private String subjectName;
    @XmlElement(required = true)
    private String examName;
    @XmlElement(required = true)
    private String topScorer;
    @XmlElement(required = true)
    private String averageScore;
    @XmlElement(required = true)
    private String totalExamTakers;
    @XmlElement (required = true)
    private String numberAbsent;
    @XmlElement (required = true)
    private String totalPass;
    @XmlElement (required = true)
    private String maxMin;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedStatistics() {}

    /**
     * Converts a given statistic into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedStatistics
     */
    public AdaptedStatistics(AssignmentStatistics source) {
        isPrivate = source.isPrivate();

        subjectName = source.getSubjectName();

        examName = source.getExamName();

        topScorer = source.getTopScorer();

        averageScore = source.getAverageScore();

        totalExamTakers = source.getTotalExamTakers();

        numberAbsent = source.getNumberAbsent();

        totalPass = source.getTotalPass();

        maxMin = source.getMaxMin();
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
        return Utils.isAnyNull(subjectName, examName, topScorer, averageScore, totalExamTakers, numberAbsent,
                totalPass, maxMin, isPrivate);
    }

    /**
     * Converts this jaxb-friendly adapted statistics object into the Assignment Statistics object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted statistics
     */
    public AssignmentStatistics toModelType() throws IllegalValueException {
        final String subjectName = this.subjectName;
        final String examName = this.examName;
        final String topScorer = this.topScorer;
        final String averageScore = this.averageScore;
        final String totalExamTakers = this.totalExamTakers;
        final String numberAbsent = this.numberAbsent;
        final String totalPass = this.totalPass;
        final String maxMin = this.maxMin;
        final boolean isPrivate = this.isPrivate;
        return new AssignmentStatistics(subjectName, examName, topScorer, averageScore, totalExamTakers, numberAbsent,
                totalPass, maxMin, isPrivate);
    }
}
