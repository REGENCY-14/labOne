/**
 * Abstract base class representing a Student in the grade management system.
 * Demonstrates encapsulation and inheritance principles.
 */
public abstract class Student {
    private int id;
    private String name;
    private int age;
    private String email;
    private String phone;
    private String status;
    private double averageGrade;

    /**
     * Constructs a Student with the given details.
     *
     * @param id    unique student identifier
     * @param name  student's full name
     * @param age   student's age
     * @param email student's email address
     * @param phone student's phone number
     */
    public Student(int id, String name, int age, String email, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.status = "Active";
        this.averageGrade = 0.0;
    }

    // ==================== Getters ====================
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getStatus() {
        return status;
    }

    // ==================== Setters ====================
    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
        updateStatus();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ==================== Abstract Methods (Polymorphism) ====================
    /**
     * Returns the type of student (e.g., "Regular", "Honors").
     * Demonstrates polymorphic behavior.
     *
     * @return student type as a string
     */
    public abstract String getType();

    /**
     * Returns the minimum passing grade for this student type.
     * Different student types may have different passing grades.
     *
     * @return the passing grade threshold
     */
    public abstract int getPassingGrade();

    /**
     * Updates the student's status based on average grade and passing grade.
     */
    protected abstract void updateStatus();

    /**
     * Displays detailed information about the student.
     */
    public void displayStudentInfo() {
        System.out.printf("ID: %d | Name: %s | Type: %s | Age: %d | Email: %s | Phone: %s | Avg Grade: %.2f | Status: %s%n",
                id, name, getType(), age, email, phone, averageGrade, status);
    }
}
