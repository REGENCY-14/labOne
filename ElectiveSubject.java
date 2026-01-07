/**
 * Concrete class representing an Elective Subject.
 * Elective subjects are optional for students.
 */
public class ElectiveSubject extends Subject {
    private static final boolean MANDATORY = false;

    /**
     * Constructs an ElectiveSubject with given name and code.
     *
     * @param name the name of the elective subject
     * @param code the code identifier for the subject
     */
    public ElectiveSubject(String name, String code) {
        super(name, code);
    }

    @Override
    public void displaySubjectDetails() {
        System.out.println("Elective Subject: " + getSubjectName() + " (" + getSubjectCode() + ")");
    }

    @Override
    public String getSubjectType() {
        return "Elective";
    }

    @Override
    public boolean isMandatory() {
        return MANDATORY;
    }
}
