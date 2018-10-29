package seedu.addressbook;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.account.Account;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.AssignmentStatistics;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.details.Address;
import seedu.addressbook.data.person.details.Email;
import seedu.addressbook.data.person.details.Name;
import seedu.addressbook.data.person.details.Phone;
import seedu.addressbook.data.tag.Tag;

/**
 * A utility class to generate test data.
 */
public class TestDataHelper {
    /** Test person for testing**/
    public Person adam() throws Exception {
        Name name = new Name("Adam Brown");
        Phone privatePhone = new Phone("111111", true);
        Email email = new Email("adam@gmail.com", false);
        Address privateAddress = new Address("111, alpha street", true);
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Set<Tag> tags = new HashSet<>(Arrays.asList(tag1, tag2));
        return new Person(name, privatePhone, email, privateAddress, tags);
    }

    /** Test exam for testing**/
    public Exam math() throws Exception {
        String examName = "Math Mid-Terms 2018";
        String subjectName = "Mathematics";
        String date = "06-06-2018";
        String startTime = "09:00";
        String endTime = "12:00";
        String details = "Held in MPSH";
        boolean isPrivate = false;
        return new Exam(examName, subjectName, date, startTime, endTime, details, isPrivate);
    }

    /** Test fees for testing**/
    public Fees fees () throws Exception {
        String test = "123.45";
        String testdate = "01-01-2018";
        return new Fees(test, testdate);
    }

    /** Test exam for testing**/
    public AssignmentStatistics stat() throws Exception {
        String subjectName = "Spanish";
        String examName = "Quiz";
        String topScorer = "Pedro";
        String averageScore = "95";
        String totalExamTakers = "10";
        String numberAbsent = "3";
        String totalPass = "7";
        String maxMin = "100 87";
        Boolean isPrivate = false;
        return new AssignmentStatistics(subjectName, examName, topScorer, averageScore, totalExamTakers, numberAbsent,
                totalPass, maxMin, isPrivate);
    }

    /** Test assessment for testing**/
    public final Assessment assess () throws Exception {
        String assessment = "CG2271 Midterm";
        return new Assessment(assessment);
    }

    /**
     * Generates a valid person using the given seed.
     * Running this function with the same parameter values guarantees the returned person will have the same state.
     * Each unique seed will generate a unique Person object.
     *
     * @param seed used to generate the person data field values
     * @param isAllFieldsPrivate determines if private-able fields (phone, email, address) will be private
     */
    public Person generatePerson(int seed, boolean isAllFieldsPrivate) throws Exception {
        return new Person(
                new Name("Person " + seed),
                new Phone("" + Math.abs(seed), isAllFieldsPrivate),
                new Email(seed + "@email", isAllFieldsPrivate),
                new Address("House of " + seed, isAllFieldsPrivate),
                new HashSet<>(Arrays.asList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))))
        );
    }

    /**
     * Generates a valid person with a valid exam using the given seeds.
     * Running this function with the same parameter values guarantees the returned person will have the same state.
     * Each unique seed will generate a unique Person object.
     *
     * @param seed used to generate the person data field values
     * @param isAllFieldsPrivate determines if private-able fields (phone, email, address) will be private
     * @param examSeed used to generate the exam data field values
     * @param isExamPrivate determines if exam will be private
     * @param takers determines number of takers of exam
     */
    public Person generatePerson(int seed, boolean isAllFieldsPrivate, int examSeed,
                                 boolean isExamPrivate, int takers) throws Exception {
        Person p1 = new Person(
                new Name("Person " + seed),
                new Phone("" + Math.abs(seed), isAllFieldsPrivate),
                new Email(seed + "@email", isAllFieldsPrivate),
                new Address("House of " + seed, isAllFieldsPrivate),
                new HashSet<>(Arrays.asList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))))
        );
        Exam e1 = generateExam(examSeed, isExamPrivate);
        e1.setTakers(takers);
        p1.addExam(e1);
        return p1;
    }

    /**
     * Generates a valid exam using the given seed.
     * Running this function with the same parameter values guarantees the returned exam will have the same state.
     * Each unique seed will generate a unique Exam object.
     *
     * @param seed used to generate the exam data field values
     * @param isExamPrivate determines if the exam will be private
     */
    public Exam generateExam(int seed, boolean isExamPrivate) throws Exception {
        return new Exam(("Exam " + seed), ("Subject " + seed), "01-02-2018",
                "10:00", "12:00", ("Held in " + seed), isExamPrivate);
    }

    /**
     * Generates a valid exam using the given seed and sets the takers value.
     * Running this function with the same parameter values guarantees the returned exam will have the same state.
     * Each unique seed will generate a unique Exam object.
     *
     * @param seed used to generate the exam data field values
     * @param isExamPrivate determines if the exam will be private
     * @param takers used to determine the number of exam-takers
     */
    public Exam generateExam(int seed, boolean isExamPrivate, int takers) throws Exception {
        Exam exam = new Exam(("Exam " + seed), ("Subject " + seed), "01-02-2018",
                "10:00", "12:00", ("Held in " + seed), isExamPrivate);
        exam.setTakers(takers);
        return exam;
    }

    /**Generated the prefix for the field **/
    public String getPrefix (Phone phone) {
        return (phone.isPrivate() ? " pp/" : " p/");
    }

    public String getPrefix (Email email) {
        return (email.isPrivate() ? " pe/" : " e/");
    }

    public String getPrefix (Address address) {
        return (address.isPrivate() ? " pa/" : " a/");
    }

    public String getPrefix (String name, Boolean isPrivate) {
        return (isPrivate ? " pn/" : " n/");
    }

    public String getExamNamePrefix (Boolean isPrivate) {
        return (isPrivate ? " pe/" : "e/");
    }

    public String getSubjectNamePrefix() {
        return (" s/");
    }

    public String getDatePrefix() {
        return (" d/");
    }

    public String getStartTimePrefix() {
        return (" st/");
    }

    public String getEndTimePrefix() {
        return (" et/");
    }

    public String getDetailsPrefix() {
        return (" dt/");
    }

    public String getTopScorerPrefix (String value) {
        return (" ts/");
    }

    public String getAverageScorePrefix (String value) {
        return (" av/");
    }

    public String getTotalExamTakersPrefix (String value) {
        return (" te/");
    }

    public String getNumberAbsentPrefix (String value) {
        return (" ab/");
    }

    public String getTotalPassPrefix (String value) {
        return (" tp/");
    }

    public String getMaxMinPrefix (String value) {
        return (" mm/");
    }

    public String getPrivatePrefix (Boolean isPrivate) {
        return (isPrivate ? " p" : "");
    }

    /** Generates the correct add command based on the person given */
    public String generateAddCommand(Person p) {
        StringJoiner cmd = new StringJoiner(" ");
        String phoneField = getPrefix(p.getPhone()) + p.getPhone();
        String emailField = getPrefix(p.getEmail()) + p.getEmail();
        String addressField = getPrefix(p.getAddress()) + p.getAddress();

        cmd.add("add");
        cmd.add(p.getName().toString());
        cmd.add(phoneField);
        cmd.add(emailField);
        cmd.add(addressField);

        Set<Tag> tags = p.getTags();
        for (Tag t: tags) {
            cmd.add("t/" + t.tagName);
        }
        return cmd.toString();
    }

    /** Generates the correct addfees command based on the person given */
    public String generateAddFeesCommand() {
        StringJoiner cmd = new StringJoiner(" ");
        cmd.add("addfees");
        cmd.add(" 2");
        cmd.add(" 123.45");
        cmd.add(" 01-01-2018");
        return cmd.toString();
    }

    /** Generates the correct add exam command based on the exam given */
    public String generateAddExamCommand(Exam e) {
        StringJoiner cmd = new StringJoiner(" ");
        String examNameField = getExamNamePrefix(e.isPrivate()) + e.getExamName();
        String subjectNameField = getSubjectNamePrefix() + e.getSubjectName();
        String dateField = getDatePrefix() + e.getExamDate();
        String startTimeField = getStartTimePrefix() + e.getExamStartTime();
        String endTimeField = getEndTimePrefix() + e.getExamEndTime();
        String detailsField = getDetailsPrefix() + e.getExamDetails();

        cmd.add("addexam");
        cmd.add(examNameField);
        cmd.add(subjectNameField);
        cmd.add(dateField);
        cmd.add(startTimeField);
        cmd.add(endTimeField);
        cmd.add(detailsField);
        return cmd.toString();
    }

    /** Generates the correct addstatistics command based on the exam given */
    public String generateAddAssignmentStatistics(AssignmentStatistics s) {
        StringJoiner cmd = new StringJoiner(" ");
        String subjectField = s.getSubjectName();
        String examNameField = getExamNamePrefix(s.isPrivate()) + s.getExamName();
        String topScorerField = getTopScorerPrefix(s.getTopScorer()) + s.getTopScorer();
        String averageScoreField = getAverageScorePrefix(s.getAverageScore()) + s.getAverageScore();
        String totalExamTakersField = getTotalExamTakersPrefix(s.getTotalExamTakers()) + s.getTotalExamTakers();
        String numberAbsentField = getNumberAbsentPrefix(s.getNumberAbsent()) + s.getNumberAbsent();
        String totalPassField = getTotalPassPrefix(s.getTotalPass()) + s.getTotalPass();
        String maxMinField = getMaxMinPrefix(s.getMaxMin()) + s.getMaxMin();

        cmd.add("addstatistics");
        cmd.add(subjectField);
        cmd.add(examNameField);
        cmd.add(topScorerField);
        cmd.add(averageScoreField);
        cmd.add(totalExamTakersField);
        cmd.add(numberAbsentField);
        cmd.add(totalPassField);
        cmd.add(maxMinField);
        return cmd.toString();
    }

    /** Generates the correct addassess command based on the person given */
    public String generateAddAssessment(Assessment a) {
        StringJoiner cmd = new StringJoiner(" ");
        String examName = a.getExamName();

        cmd.add("addassess");
        cmd.add(examName);
        return cmd.toString();
    }

    /**
     * Generates an ExamBook based on the list of Exams given.
     */
    public ExamBook generateExamBook(List<Exam> exams) throws Exception {
        ExamBook examBook = new ExamBook();
        addToExamBook(examBook, exams);
        return examBook;
    }

    /**
     * Adds auto-generated Exam objects to the given ExamBook
     * @param examBook The ExamBook to which the Exams will be added
     * @param isPrivateStatuses flags to indicate if the generated exams should be set to
     *                          private.
     */
    public void addToExamBook(ExamBook examBook, Boolean... isPrivateStatuses) throws Exception {
        addToExamBook(examBook, generateExamList(isPrivateStatuses));
    }

    /**
     * Adds the given list of Exams to the given ExamBook
     */
    public void addToExamBook(ExamBook examBook, List<Exam> examsToAdd) throws Exception {
        for (Exam p: examsToAdd) {
            examBook.addExam(p);
        }
    }

    /**
     * Removes special characters in a string for exam values
     */
    public String removeSpecialChar(String value) {
        String newValue = value.replaceAll("[^a-zA-Z0-9!@\\.,]", "");
        return newValue;
    }

    /**
     * Generates an AddressBook with auto-generated persons.
     * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
     *                          private.
     */
    public AddressBook generateAddressBook(Boolean... isPrivateStatuses) throws Exception {
        AddressBook addressBook = new AddressBook();
        addToAddressBook(addressBook, isPrivateStatuses);
        return addressBook;
    }

    /**
     * Generates an AddressBook based on the list of Persons given.
     */
    public AddressBook generateAddressBook(List<Person> persons) throws Exception {
        AddressBook addressBook = new AddressBook();
        addToAddressBook(addressBook, persons);
        return addressBook;
    }

    /**
     * Adds auto-generated Person objects to the given AddressBook
     * @param addressBook The AddressBook to which the Persons will be added
     * @param isPrivateStatuses flags to indicate if all contact details of generated persons should be set to
     *                          private.
     */
    public void addToAddressBook(AddressBook addressBook, Boolean... isPrivateStatuses) throws Exception {
        addToAddressBook(addressBook, generatePersonList(isPrivateStatuses));
    }

    /**
     * Adds the given list of Persons to the given AddressBook
     */
    public void addToAddressBook(AddressBook addressBook, List<Person> personsToAdd) throws Exception {
        for (Person p: personsToAdd) {
            addressBook.addPerson(p);
        }
    }

    /**
     * Creates a list of Persons based on the give Person objects.
     */
    public List<Person> generatePersonList(Person... persons) {
        return new ArrayList<>(Arrays.asList(persons));
    }

    /**
     * Generates a list of Persons based on the flags.
     * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
     *                          private.
     */
    public List<Person> generatePersonList(Boolean... isPrivateStatuses) throws Exception {
        List<Person> persons = new ArrayList<>();
        int i = 1;
        for (Boolean p: isPrivateStatuses) {
            persons.add(generatePerson(i++, p));
        }
        return persons;
    }

    /**
     * Generates a Person object with given name. Other fields will have some dummy values.
     */
    public Person generatePersonWithName(String name) throws Exception {
        return new Person(
                new Name(name),
                new Phone("1", false),
                new Email("1@email", false),
                new Address("House of 1", false),
                Collections.singleton(new Tag("tag"))
        );
    }

    /**
     * Creates a list of Exams based on the give Exam objects.
     */
    public List<Exam> generateExamList(Exam... exams) {
        return new ArrayList<>(Arrays.asList(exams));
    }

    /**
     * Generates a list of Exams based on the flags.
     * @param isPrivateStatuses flags to indicate if the exams should be set to
     *                          private.
     */
    public List<Exam> generateExamList(Boolean... isPrivateStatuses) throws Exception {
        List<Exam> exams = new ArrayList<>();
        int i = 1;
        for (Boolean p: isPrivateStatuses) {
            exams.add(generateExam(i++, p));
        }
        return exams;
    }

    /**
     * Generates a valid date using a seed
     */
    public String generateDate(int seed) {
        Long max = 0L;
        Long min = 100000000000L;
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");
        Random rnd = new Random(seed);
        Long randomLong = (rnd.nextLong() % (max - min)) + min;
        Date date = new Date(randomLong);
        return removeSpecialChar(spf.format(date));
    }

    /**
     * Generates a valid time using a seed
     */
    public String generateTime(int seed) {
        Long max = 0L;
        Long min = 100000000000L;
        SimpleDateFormat spf = new SimpleDateFormat("HH:mm");
        Random rnd = new Random(seed);
        Long randomLong = (rnd.nextLong() % (max - min)) + min;
        Date date = new Date(randomLong);
        return removeSpecialChar(spf.format(date));
    }

    /**
     * Generate the next time
     */
    public String addTimeInterval(String time) {
        String timeString = new StringBuilder(time).insert(2, ":").toString();
        SimpleDateFormat spf = new SimpleDateFormat("HH:mm");
        try {
            Date date = spf.parse(timeString);
            Long nextTime = date.getTime();
            nextTime += (2 * 60 * 60 * 1000);
            Date nextDate = new Date(nextTime);
            return removeSpecialChar(spf.format(nextDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * Class used to wrap the return arrays for generateThreePersons
     */
    public static class ThreePersons {
        private List<Person> expected;
        private List<Person> actual;

        public ThreePersons() {
            expected = new ArrayList<>();
            actual = new ArrayList<>();
        }
        public List<Person> getExpected() {
            return expected;
        }

        public void setExpected(List<Person> expected) {
            // copies the list
            for (Person person: expected) {
                this.expected.add(new Person(person));
            }
        }

        public List<Person> getActual() {
            return actual;
        }

        public void setActual(List<Person> actual) {
            // copies the list
            for (Person person: actual) {
                this.actual.add(new Person(person));
            }
        }

        public Person getActualPerson(int index) {
            return actual.get(index - DISPLAYED_INDEX_OFFSET);
        }

        public Person getExpectedPerson(int index) {
            return expected.get(index - DISPLAYED_INDEX_OFFSET);
        }

        public void setBothPersons(int index, Account account) {
            actual.get(index - DISPLAYED_INDEX_OFFSET).setAccount(account);
            expected.get(index - DISPLAYED_INDEX_OFFSET).setAccount(account);
        }
    }

    /**
     * Generates a 3 Person object with given name. Returns the Lists wrapped in ThreePerson
     */
    public ThreePersons generateThreePersons() throws Exception {
        ThreePersons threePersons = new ThreePersons();
        final List<Person> p = new ArrayList<>();
        p.add(generatePerson(1, true));
        p.add(generatePerson(2, false));
        p.add(generatePerson(3, false));
        threePersons.setActual(p);
        threePersons.setExpected(p);
        return threePersons;
    }
}
