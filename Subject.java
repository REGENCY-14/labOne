/**
 * Abstract base class for Subject.
 * Demonstrates abstraction and polymorphism for different subject types.
 */
public abstract class Subject {
    private String subjectName;
    private String subjectCode;

    /**
     * Constructs a Subject with given name and code.
     *
     * @param subjectName the name of the subject
     * @param subjectCode the code identifier for the subject
     */
    public Subject(String subjectName, String subjectCode) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    // ==================== Getters ====================
    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    // ==================== Abstract Methods (Polymorphism) ====================
    /**
     * Displays the details of this subject.
     */
    public abstract void displaySubjectDetails();

    /**
     * Returns the type of subject (e.g., "Core", "Elective").
     *
     * @return the subject type
     */
    public abstract String getSubjectType();

    /**
     * Determines if this subject is mandatory.
     *
     * @return true if mandatory, false otherwise
     */
    public abstract boolean isMandatory();

    @Override
    public String toString() {
        return String.format("%s (%s)", subjectName, subjectCode);
    }
}
