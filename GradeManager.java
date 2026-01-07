import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Manages student grade records and grade-related operations.
 * Demonstrates proper encapsulation and single responsibility principle.
 */
public class GradeManager {
    private static final int INITIAL_CAPACITY = 200;

    private Grade[] grades;
    private int gradeCount;
    private List<Student> studentRepository;

    /**
     * Constructs a GradeManager with a reference to the student repository.
     *
     * @param studentRepository the list of students managed by the system
     */
    public GradeManager(List<Student> studentRepository) {
        this.grades = new Grade[INITIAL_CAPACITY];
        this.gradeCount = 0;
        this.studentRepository = studentRepository;
    }

    // ==================== Add Grade ====================
    /**
     * Adds a new grade record to the system and updates the student's average.
     *
     * @param grade the grade to add
     * @throws IllegalStateException if grade storage is full
     */
    public void addGrade(Grade grade) {
        if (gradeCount >= grades.length) {
            throw new IllegalStateException("Grade storage capacity reached!");
        }
        grades[gradeCount++] = grade;
        updateStudentAverage(grade.getStudentId());
    }

    /**
     * Updates the average grade for a student based on all their recorded grades.
     *
     * @param studentId the ID of the student
     */
    private void updateStudentAverage(int studentId) {
        double total = 0;
        int count = 0;

        for (int i = 0; i < gradeCount; i++) {
            if (grades[i].getStudentId() == studentId) {
                total += grades[i].getNumericGrade();
                count++;
            }
        }

        Student student = findStudentById(studentId);
        if (student != null && count > 0) {
            student.setAverageGrade(total / count);
        }
    }

    // ==================== Find Operations ====================
    /**
     * Finds a student by their ID from the repository.
     *
     * @param studentId the student's ID
     * @return the Student object if found, null otherwise
     */
    private Student findStudentById(int studentId) {
        for (Student student : studentRepository) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }

    /**
     * Retrieves all grades for a specific student.
     *
     * @param studentId the student's ID
     * @return a list of Grade objects for the student
     */
    public List<Grade> getGradesByStudent(int studentId) {
        List<Grade> studentGrades = new ArrayList<>();
        for (int i = 0; i < gradeCount; i++) {
            if (grades[i].getStudentId() == studentId) {
                studentGrades.add(grades[i]);
            }
        }
        return studentGrades;
    }

    // ==================== View Grade Report ====================
    /**
     * Displays a comprehensive grade report for a student.
     *
     * @param studentId the student's ID
     */
    public void viewGradeByStudent(int studentId) {
        List<Grade> studentGrades = getGradesByStudent(studentId);

        if (studentGrades.isEmpty()) {
            System.out.println("No grades recorded for student ID: " + studentId);
            return;
        }

        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        // Sort grades by date in descending order
        studentGrades.sort(Comparator.comparing(Grade::getRecordDate).reversed());

        System.out.println("\n========== GRADE REPORT FOR STUDENT " + studentId + " ==========");
        System.out.println("Student Name: " + student.getName() + " | Type: " + student.getType());
        System.out.printf("%-8s %-12s %-20s %-10s %-8s\n",
                "GradeID", "Date", "Subject", "Type", "Grade");
        System.out.println("-------------------------------------------------------------");

        for (Grade grade : studentGrades) {
            System.out.printf("%-8d %-12s %-20s %-10s %-8.2f\n",
                    grade.getGradeId(),
                    grade.getRecordDate(),
                    grade.getSubject().getSubjectName(),
                    grade.getSubject().getSubjectType(),
                    grade.getNumericGrade());
        }

        System.out.println("-------------------------------------------------------------");

        // Calculate and display averages
        double coreAverage = calculateCoreAverage(studentId);
        double electiveAverage = calculateElectiveAverage(studentId);
        double overallAverage = calculateOverallAverage(studentId);

        System.out.printf("Core Subjects Average: %s%n",
                coreAverage == -1 ? "N/A" : String.format("%.2f", coreAverage));
        System.out.printf("Elective Subjects Average: %s%n",
                electiveAverage == -1 ? "N/A" : String.format("%.2f", electiveAverage));
        System.out.printf("Overall Average: %s%n",
                overallAverage == -1 ? "N/A" : String.format("%.2f", overallAverage));
        System.out.println("Status: " + student.getStatus());
        System.out.println("================================================================\n");
    }

    // ==================== Average Calculations ====================
    /**
     * Calculates the average grade for core subjects for a student.
     *
     * @param studentId the student's ID
     * @return the average grade, or -1 if no core subjects found
     */
    public double calculateCoreAverage(int studentId) {
        double total = 0;
        int count = 0;

        for (int i = 0; i < gradeCount; i++) {
            Grade grade = grades[i];
            if (grade.getStudentId() == studentId && 
                grade.getSubject().getSubjectType().equals("Core")) {
                total += grade.getNumericGrade();
                count++;
            }
        }

        return count == 0 ? -1 : total / count;
    }

    /**
     * Calculates the average grade for elective subjects for a student.
     *
     * @param studentId the student's ID
     * @return the average grade, or -1 if no elective subjects found
     */
    public double calculateElectiveAverage(int studentId) {
        double total = 0;
        int count = 0;

        for (int i = 0; i < gradeCount; i++) {
            Grade grade = grades[i];
            if (grade.getStudentId() == studentId && 
                grade.getSubject().getSubjectType().equals("Elective")) {
                total += grade.getNumericGrade();
                count++;
            }
        }

        return count == 0 ? -1 : total / count;
    }

    /**
     * Calculates the overall average grade for a student.
     *
     * @param studentId the student's ID
     * @return the average grade, or -1 if no grades found
     */
    public double calculateOverallAverage(int studentId) {
        double total = 0;
        int count = 0;

        for (int i = 0; i < gradeCount; i++) {
            Grade grade = grades[i];
            if (grade.getStudentId() == studentId) {
                total += grade.getNumericGrade();
                count++;
            }
        }

        return count == 0 ? -1 : total / count;
    }

    // ==================== Utility Methods ====================
    /**
     * Gets the total number of grades recorded.
     *
     * @return the grade count
     */
    public int getGradeCount() {
        return gradeCount;
    }

    /**
     * Gets the number of grades recorded for a specific student.
     *
     * @param studentId the student's ID
     * @return the number of grades for the student
     */
    public int getGradeCountForStudent(int studentId) {
        int count = 0;
        for (int i = 0; i < gradeCount; i++) {
            if (grades[i].getStudentId() == studentId) {
                count++;
            }
        }
        return count;
    }
}
