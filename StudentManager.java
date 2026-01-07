import java.util.ArrayList;
import java.util.List;

/**
 * StudentManager manages the collection of students.
 * Demonstrates encapsulation and single responsibility principle.
 * Provides all student-related operations in one centralized manager.
 */
public class StudentManager {
    private static final int INITIAL_STUDENT_ID = 1000;

    private List<Student> students;
    private int studentIdCounter;

    /**
     * Constructs a StudentManager with an empty student list.
     */
    public StudentManager() {
        this.students = new ArrayList<>();
        this.studentIdCounter = INITIAL_STUDENT_ID;
    }

    // ==================== Add Student ====================
    /**
     * Adds a new student to the system.
     *
     * @param student the student to add
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    // ==================== Retrieve Students ====================
    /**
     * Gets all students in the system.
     *
     * @return a list of all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Finds a student by their ID.
     *
     * @param studentId the student's ID
     * @return the Student if found, null otherwise
     */
    public Student getStudentById(int studentId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }

    /**
     * Gets the count of all students.
     *
     * @return the number of students in the system
     */
    public int getStudentCount() {
        return students.size();
    }

    /**
     * Gets the next student ID to be assigned.
     * Increments the counter and returns the new ID.
     *
     * @return the next available student ID
     */
    public int getNextStudentId() {
        return studentIdCounter++;
    }

    // ==================== Statistics ====================
    /**
     * Calculates the class average grade.
     *
     * @return the average grade of all students, or 0 if no grades
     */
    public double getClassAverage() {
        if (students.isEmpty()) {
            return 0;
        }

        double total = 0;
        int count = 0;

        for (Student student : students) {
            double avgGrade = student.getAverageGrade();
            if (avgGrade > 0) {
                total += avgGrade;
                count++;
            }
        }

        return count == 0 ? 0 : total / count;
    }

    /**
     * Gets the number of passing students.
     *
     * @return the count of students with "Passing" status
     */
    public int getPassingStudentCount() {
        int count = 0;
        for (Student student : students) {
            if (student.getStatus().equals("Passing")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the number of failing students.
     *
     * @return the count of students with "Failing" status
     */
    public int getFailingStudentCount() {
        int count = 0;
        for (Student student : students) {
            if (student.getStatus().equals("Failing")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the number of honors-eligible students.
     *
     * @return the count of honors students with passing status
     */
    public int getHonorsEligibleCount() {
        int count = 0;
        for (Student student : students) {
            if (student instanceof HonorsStudent && ((HonorsStudent) student).isHonorsEligible()) {
                count++;
            }
        }
        return count;
    }
}
