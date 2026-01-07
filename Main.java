/**
 * Main class serves as the entry point for the Student Grade Management System.
 * Demonstrates proper separation of concerns - Main only initializes and starts the application.
 * All business logic and UI handling is delegated to appropriate manager and menu classes.
 *
 * This class demonstrates:
 * - Single Responsibility Principle: Only creates and initializes components
 * - Dependency Injection: Passes dependencies to Menu and GradeManager
 * - Clean code practices: Simple and focused responsibility
 */
public class Main {

    /**
     * Application entry point.
     * Initializes the necessary components and starts the menu system.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize manager components
        StudentManager studentManager = new StudentManager();
        GradeManager gradeManager = new GradeManager(studentManager.getAllStudents());

        // Initialize and start the menu system
        Menu menu = new Menu(studentManager, gradeManager);
        menu.start();
        menu.close();
    }
}
