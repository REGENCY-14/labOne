/**
 * Concrete class representing an Honors Student.
 * Extends Student and overrides abstract methods for honors student behavior.
 */
public class HonorsStudent extends Student {
    private static final int PASSING_GRADE = 60;

    /**
     * Constructs an HonorsStudent with the given details.
     *
     * @param id    unique student identifier
     * @param name  student's full name
     * @param age   student's age
     * @param email student's email address
     * @param phone student's phone number
     */
    public HonorsStudent(int id, String name, int age, String email, String phone) {
        super(id, name, age, email, phone);
    }

    @Override
    public String getType() {
        return "Honors";
    }

    @Override
    public int getPassingGrade() {
        return PASSING_GRADE;
    }

    @Override
    protected void updateStatus() {
        if (getAverageGrade() >= PASSING_GRADE) {
            setStatus("Passing");
        } else {
            setStatus("Failing");
        }
    }

    /**
     * Checks if the student is eligible for honors recognition.
     *
     * @return true if student's average grade meets or exceeds the passing grade
     */
    public boolean isHonorsEligible() {
        return getAverageGrade() >= PASSING_GRADE;
    }
}
