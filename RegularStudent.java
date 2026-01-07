/**
 * Concrete class representing a Regular Student.
 * Extends Student and overrides abstract methods for regular student behavior.
 */
public class RegularStudent extends Student {
    private static final int PASSING_GRADE = 50;

    /**
     * Constructs a RegularStudent with the given details.
     *
     * @param id    unique student identifier
     * @param name  student's full name
     * @param age   student's age
     * @param email student's email address
     * @param phone student's phone number
     */
    public RegularStudent(int id, String name, int age, String email, String phone) {
        super(id, name, age, email, phone);
    }

    @Override
    public String getType() {
        return "Regular";
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
}
