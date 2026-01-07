import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Menu class handles all user interface interactions and menu operations.
 * Demonstrates separation of concerns - UI logic is separated from main entry point.
 */
public class Menu {
    private final Scanner scanner;
    private final StudentManager studentManager;
    private final GradeManager gradeManager;

    private static final int ADD_STUDENT_OPTION = 1;
    private static final int VIEW_STUDENTS_OPTION = 2;
    private static final int RECORD_GRADE_OPTION = 3;
    private static final int VIEW_GRADE_REPORT_OPTION = 4;
    private static final int EXIT_OPTION = 5;

    /**
     * Constructs a Menu with the given manager components.
     *
     * @param studentManager the StudentManager instance
     * @param gradeManager   the GradeManager instance
     */
    public Menu(StudentManager studentManager, GradeManager gradeManager) {
        this.scanner = new Scanner(System.in);
        this.studentManager = studentManager;
        this.gradeManager = gradeManager;
    }

    /**
     * Starts the main application loop.
     * Displays menu and processes user input until exit option is selected.
     */
    public void start() {
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case ADD_STUDENT_OPTION:
                    addStudent();
                    break;
                case VIEW_STUDENTS_OPTION:
                    viewStudents();
                    break;
                case RECORD_GRADE_OPTION:
                    recordGrade();
                    break;
                case VIEW_GRADE_REPORT_OPTION:
                    viewGradeReport();
                    break;
                case EXIT_OPTION:
                    running = false;
                    displayExitMessage();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.\n");
            }
        }
    }

    // ==================== Menu Display Methods ====================
    /**
     * Displays the main menu.
     */
    private void displayMainMenu() {
        System.out.println("\n============================================");
        System.out.println("||  STUDENT GRADE MANAGEMENT SYSTEM       ||");
        System.out.println("============================================");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Record Grade");
        System.out.println("4. View Grade Report");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    /**
     * Displays the exit message.
     */
    private void displayExitMessage() {
        System.out.println("\nThank you for using the Student Grade Management System!");
        System.out.println("Goodbye!");
    }

    // ==================== Feature Methods ====================
    /**
     * Handles adding a new student to the system.
     */
    private void addStudent() {
        System.out.println("\n-------------- ADD STUDENT ----------------");

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student age: ");
        int age = getIntInput();

        System.out.print("Enter student email: ");
        String email = scanner.nextLine();

        System.out.print("Enter student phone: ");
        String phone = scanner.nextLine();

        System.out.println("Select student type:");
        System.out.println("1. Regular Student");
        System.out.println("2. Honors Student");
        System.out.print("Enter choice: ");
        int studentType = getIntInput();

        Student student = null;
        if (studentType == 1) {
            student = new RegularStudent(studentManager.getNextStudentId(), name, age, email, phone);
            System.out.println("\n✓ Regular student added successfully!");
        } else if (studentType == 2) {
            student = new HonorsStudent(studentManager.getNextStudentId(), name, age, email, phone);
            System.out.println("\n✓ Honors student added successfully!");
        } else {
            System.out.println("Invalid choice!");
            return;
        }

        studentManager.addStudent(student);
        System.out.println("Student ID: " + student.getId());
        System.out.println("--------------------------------------------\n");
    }

    /**
     * Displays all students in the system.
     */
    private void viewStudents() {
        List<Student> students = studentManager.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("\nNo students have been added to the system.\n");
            return;
        }

        System.out.println("\n========== STUDENT LIST ==========");
        System.out.printf("%-6s %-20s %-10s %-12s %-12s %-8s\n",
                "ID", "NAME", "TYPE", "AVG GRADE", "PASSING GR.", "STATUS");
        System.out.println("-------------------------------------------------------------");

        double totalGrades = 0;
        int countGrades = 0;

        for (Student student : students) {
            double avgGrade = student.getAverageGrade();

            if (avgGrade > 0) {
                totalGrades += avgGrade;
                countGrades++;
            }

            System.out.printf("%-6d %-20s %-10s %-12.2f %-12d %-8s\n",
                    student.getId(),
                    student.getName(),
                    student.getType(),
                    avgGrade,
                    student.getPassingGrade(),
                    student.getStatus());

            // Check if honors student is eligible for honors recognition
            if (student instanceof HonorsStudent && ((HonorsStudent) student).isHonorsEligible()) {
                System.out.println("  ⭐ Honors Eligible!");
            }
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println("Total Students: " + students.size());

        if (countGrades > 0) {
            System.out.printf("Class Average Grade: %.2f\n", (totalGrades / countGrades));
        } else {
            System.out.println("Class Average Grade: N/A");
        }
        System.out.println();
    }

    /**
     * Handles recording a grade for a student.
     */
    private void recordGrade() {
        System.out.println("\n------------- RECORD GRADE ----------------");

        System.out.print("Enter student ID: ");
        int studentId = getIntInput();

        Student student = studentManager.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("Select subject category:");
        System.out.println("1. Core Subject");
        System.out.println("2. Elective Subject");
        System.out.print("Enter choice: ");
        int subjectCategory = getIntInput();

        Subject subject = null;

        if (subjectCategory == 1) {
            subject = selectCoreSubject();
        } else if (subjectCategory == 2) {
            subject = selectElectiveSubject();
        } else {
            System.out.println("Invalid choice!");
            return;
        }

        if (subject == null) {
            return;
        }

        System.out.print("Enter grade (0 - 100): ");
        double gradeValue = getDoubleInput();

        try {
            Grade grade = new Grade(studentId, subject, gradeValue);
            gradeManager.addGrade(grade);
            System.out.println("\n✓ Grade recorded successfully!");
            grade.displayGradeDetails();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays core subject options and returns the selected subject.
     *
     * @return the selected CoreSubject or null if invalid selection
     */
    private Subject selectCoreSubject() {
        System.out.println("Select Core Subject:");
        System.out.println("1. Mathematics");
        System.out.println("2. English");
        System.out.println("3. Science");
        System.out.print("Enter choice: ");
        int choice = getIntInput();

        switch (choice) {
            case 1:
                return new CoreSubject("Mathematics", "C-MATH");
            case 2:
                return new CoreSubject("English", "C-ENG");
            case 3:
                return new CoreSubject("Science", "C-SCI");
            default:
                System.out.println("Invalid subject!");
                return null;
        }
    }

    /**
     * Displays elective subject options and returns the selected subject.
     *
     * @return the selected ElectiveSubject or null if invalid selection
     */
    private Subject selectElectiveSubject() {
        System.out.println("Select Elective Subject:");
        System.out.println("1. Music");
        System.out.println("2. Art");
        System.out.println("3. Physical Education");
        System.out.print("Enter choice: ");
        int choice = getIntInput();

        switch (choice) {
            case 1:
                return new ElectiveSubject("Music", "E-MUS");
            case 2:
                return new ElectiveSubject("Art", "E-ART");
            case 3:
                return new ElectiveSubject("Physical Education", "E-PE");
            default:
                System.out.println("Invalid subject!");
                return null;
        }
    }

    /**
     * Handles displaying the grade report for a student.
     */
    private void viewGradeReport() {
        System.out.print("\nEnter student ID: ");
        int studentId = getIntInput();

        Student student = studentManager.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        gradeManager.viewGradeByStudent(studentId);
    }

    // ==================== Input Helper Methods ====================
    /**
     * Reads and returns an integer input from the user.
     * Handles newline character from previous input.
     *
     * @return the integer input
     */
    private int getIntInput() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return value;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
    }

    /**
     * Reads and returns a double input from the user.
     * Handles newline character from previous input.
     *
     * @return the double input
     */
    private double getDoubleInput() {
        try {
            double value = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            return value;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
    }

    /**
     * Closes the scanner resource.
     */
    public void close() {
        scanner.close();
    }
}
