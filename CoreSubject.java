/**
 * Concrete class representing a Core Subject.
 * Core subjects are mandatory for all students.
 */
public class CoreSubject extends Subject {
    private static final boolean MANDATORY = true;

    /**
     * Constructs a CoreSubject with given name and code.
     *
     * @param name the name of the core subject
     * @param code the code identifier for the subject
     */
    public CoreSubject(String name, String code) {
        super(name, code);
    }

    @Override
    public void displaySubjectDetails() {
        System.out.println("Core Subject: " + getSubjectName() + " (" + getSubjectCode() + ")");
    }

    @Override
    public String getSubjectType() {
        return "Core";
    }

    @Override
    public boolean isMandatory() {
        return MANDATORY;
    }
}
