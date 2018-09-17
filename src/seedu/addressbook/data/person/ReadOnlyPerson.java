package seedu.addressbook.data.person;

import java.util.Set;

import seedu.addressbook.data.tag.Tag;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    Name getName();
    Phone getPhone();
    Email getEmail();
    Address getAddress();

    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the person's internal tags.
     */
    Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsTextShowAll() {
        final TextFormatter textFormatter = new TextFormatter();
        final StringBuilder builder = new StringBuilder();
        final String stringChain = textFormatter.getPrintableString(
                true,
                getName(),
                getPhone(),
                getEmail(),
                getAddress());

        builder.append(stringChain)
                .append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }

        return builder.toString();
    }

    /**
     * Formats a person as text, showing only non-private contact details.
     */
    default String getAsTextHidePrivate() {
        final TextFormatter textFormatter = new TextFormatter();
        final StringBuilder builder = new StringBuilder();
        final String stringChain = textFormatter.getPrintableString(
                false,
                getName(),
                getPhone(),
                getEmail(),
                getAddress());
        builder.append(stringChain)
                .append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }


}