import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Grade record for a student in a subject.
 * Demonstrates proper encapsulation with private fields and public accessors.
 */
public class Grade {
    private static int gradeIdCounter = 5000;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private int gradeId;
    private int studentId;
    private Subject subject;
    private double numericGrade;
    private LocalDate recordDate;

    /**
     * Constructs a Grade with student ID, subject, and numeric grade.
     * Automatically assigns a unique grade ID and records the current date.
     *
     * @param studentId    the ID of the student
     * @param subject      the subject for which the grade is recorded
     * @param numericGrade the numeric grade value (0-100)
     * @throws IllegalArgumentException if grade is outside valid range
     */
    public Grade(int studentId, Subject subject, double numericGrade) {
        if (numericGrade < 0 || numericGrade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        this.gradeId = gradeIdCounter++;
        this.studentId = studentId;
        this.subject = subject;
        this.numericGrade = numericGrade;
        this.recordDate = LocalDate.now();
    }

    // ==================== Getters ====================
    public int getGradeId() {
        return gradeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public Subject getSubject() {
        return subject;
    }

    public double getNumericGrade() {
        return numericGrade;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    // ==================== Grade Conversion Methods ====================
    /**
     * Converts the numeric grade to a letter grade.
     *
     * @return the letter grade (A, B, C, D, or F)
     */
    public String getLetterGrade() {
        if (numericGrade >= 80) return "A";
        if (numericGrade >= 70) return "B";
        if (numericGrade >= 60) return "C";
        if (numericGrade >= 50) return "D";
        return "F";
    }

    /**
     * Checks if the grade is passing based on the given passing threshold.
     *
     * @param passingGrade the minimum passing grade
     * @return true if this grade is passing, false otherwise
     */
    public boolean isPassing(int passingGrade) {
        return numericGrade >= passingGrade;
    }

    /**
     * Displays detailed information about this grade.
     */
    public void displayGradeDetails() {
        System.out.println("========== GRADE DETAILS ==========");
        System.out.println("Grade ID: " + gradeId);
        System.out.println("Student ID: " + studentId);
        System.out.println("Subject: " + subject.getSubjectName() + " (" + subject.getSubjectCode() + ")");
        System.out.println("Subject Type: " + subject.getSubjectType());
        System.out.println("Numeric Grade: " + String.format("%.2f", numericGrade));
        System.out.println("Letter Grade: " + getLetterGrade());
        System.out.println("Date: " + recordDate.format(DATE_FORMATTER));
        System.out.println("===================================");
    }

    @Override
    public String toString() {
        return String.format("Grade[ID:%d, StudentID:%d, Subject:%s, Grade:%.2f, Date:%s]",
                gradeId, studentId, subject.getSubjectName(), numericGrade, recordDate);
    }
}
