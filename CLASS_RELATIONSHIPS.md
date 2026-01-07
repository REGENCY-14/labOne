# Class Relationships and Design Analysis

## 📊 Complete Class Structure

### **Inheritance Hierarchy**

#### **Student Hierarchy**
```
        ┌─────────────────────┐
        │   Student           │
        │   (Abstract)        │
        ├─────────────────────┤
        │ - id: int           │
        │ - name: String      │
        │ - age: int          │
        │ - email: String     │
        │ - phone: String     │
        │ - status: String    │
        │ - averageGrade: double
        ├─────────────────────┤
        │ + getId()           │
        │ + getName()         │
        │ + getStatus()       │
        │ + getAverageGrade() │
        │ + setAverageGrade() │
        │ # getType()* [abs]  │
        │ # getPassingGrade()* [abs]
        │ # updateStatus()* [abs]
        └──────────┬──────────┘
                   │
           ┌───────┴────────┐
           │                │
    ┌──────▼──────┐   ┌─────▼──────┐
    │Regular      │   │Honors      │
    │Student      │   │Student     │
    ├─────────────┤   ├────────────┤
    │ PASSING     │   │ PASSING    │
    │ GRADE = 50  │   │ GRADE = 60 │
    ├─────────────┤   ├────────────┤
    │ + getType() │   │ + getType()│
    │ + getPassing│   │ + getPassing
    │   Grade()   │   │   Grade()  │
    │ # updateSta│   │ # updateSta│
    │   tus()     │   │   tus()    │
    │             │   │ + isHonors │
    │             │   │   Eligible()
    └─────────────┘   └────────────┘
```

#### **Subject Hierarchy**
```
        ┌─────────────────────┐
        │   Subject           │
        │   (Abstract)        │
        ├─────────────────────┤
        │ - subjectName: Str  │
        │ - subjectCode: Str  │
        ├─────────────────────┤
        │ + getSubjectName()  │
        │ + getSubjectCode()  │
        │ # displayDetails()* │
        │ # getSubjectType()* │
        │ # isMandatory()*    │
        └──────────┬──────────┘
                   │
           ┌───────┴────────┐
           │                │
    ┌──────▼──────┐   ┌─────▼──────┐
    │Core         │   │Elective    │
    │Subject      │   │Subject     │
    ├─────────────┤   ├────────────┤
    │ MANDATORY   │   │ MANDATORY  │
    │ = true      │   │ = false    │
    ├─────────────┤   ├────────────┤
    │Subjects:    │   │Subjects:   │
    │- Mathematics│   │- Music     │
    │- English    │   │- Art       │
    │- Science    │   │- PE        │
    └─────────────┘   └────────────┘
```

## 🔗 Class Dependencies and Relationships

### **Main Class** (Entry Point)
```
Main
  │
  ├─→ StudentManager (creates and manages)
  │     │
  │     └─→ List<Student>
  │           ├─→ RegularStudent
  │           └─→ HonorsStudent
  │
  ├─→ GradeManager (creates and passes student list)
  │     │
  │     └─→ Grade[]
  │           └─→ Subject
  │                 ├─→ CoreSubject
  │                 └─→ ElectiveSubject
  │
  └─→ Menu (creates with managers)
        │
        ├─→ StudentManager (receives)
        │
        └─→ GradeManager (receives)
```

## 📋 Detailed Class Specifications

### **1. Student (Abstract Class)**
**Purpose**: Base class for all student types  
**Encapsulation**: All fields private  
**Inheritance**: Extended by RegularStudent, HonorsStudent  
**Key Methods**:
- `getType()` [Abstract]: Returns student type classification
- `getPassingGrade()` [Abstract]: Returns type-specific passing threshold
- `updateStatus()` [Abstract]: Updates status based on grades
- `setAverageGrade()`: Triggers status update when grades change

**Polymorphic Behavior**:
```java
// Each subclass defines its own passing grade logic
RegularStudent.getPassingGrade() → 50
HonorsStudent.getPassingGrade() → 60

// Status is updated based on average grade vs passing grade
RegularStudent: (avg >= 50) ? "Passing" : "Failing"
HonorsStudent: (avg >= 60) ? "Passing" : "Failing"
```

---

### **2. RegularStudent (Concrete Class)**
**Extends**: Student  
**Purpose**: Represents standard students with normal passing grade  
**Passing Grade**: 50  
**Unique Features**: None (standard implementation)

---

### **3. HonorsStudent (Concrete Class)**
**Extends**: Student  
**Purpose**: Represents high-achieving students with higher standards  
**Passing Grade**: 60  
**Unique Features**: 
- `isHonorsEligible()`: Returns true if average >= 60

---

### **4. Subject (Abstract Class)**
**Purpose**: Base class for different subject types  
**Encapsulation**: Name and code are private  
**Key Methods**:
- `displaySubjectDetails()` [Abstract]: Shows subject information
- `getSubjectType()` [Abstract]: Returns "Core" or "Elective"
- `isMandatory()` [Abstract]: Indicates requirement status

**Polymorphic Behavior**:
```java
CoreSubject.isMandatory() → true
CoreSubject.getSubjectType() → "Core"
CoreSubject.displaySubjectDetails() → "Core Subject: Mathematics (C-MATH)"

ElectiveSubject.isMandatory() → false
ElectiveSubject.getSubjectType() → "Elective"
ElectiveSubject.displaySubjectDetails() → "Elective Subject: Music (E-MUS)"
```

---

### **5. CoreSubject (Concrete Class)**
**Extends**: Subject  
**Mandatory**: true  
**Available Subjects**:
- Mathematics (C-MATH)
- English (C-ENG)
- Science (C-SCI)

---

### **6. ElectiveSubject (Concrete Class)**
**Extends**: Subject  
**Mandatory**: false  
**Available Subjects**:
- Music (E-MUS)
- Art (E-ART)
- Physical Education (E-PE)

---

### **7. Grade (Concrete Class)**
**Purpose**: Encapsulates individual grade records  
**Relationships**:
- References a Student (via studentId)
- References a Subject (composition)

**Validation**:
- Grade value must be between 0-100 (constructor validation)
- Throws IllegalArgumentException for invalid grades

**Key Features**:
- Auto-generated gradeId (static counter)
- Automatic date recording (LocalDate.now())
- Letter grade conversion: A(80+), B(70+), C(60+), D(50+), F(<50)
- `isPassing(int passingGrade)`: Checks if grade meets threshold

**Immutable Fields**:
- gradeId
- recordDate
- studentId
- subject

---

### **8. StudentManager (Concrete Class)**
**Purpose**: Centralize student collection management  
**Dependencies**: None (uses composition with List<Student>)  
**Responsibilities**:
- Add students to the system
- Retrieve students (by ID or all)
- Generate unique student IDs
- Calculate class statistics

**Key Methods**:
- `addStudent(Student)`: Adds a student to the list
- `getStudentById(int)`: Finds student by ID
- `getAllStudents()`: Returns copy of student list
- `getNextStudentId()`: Generates unique ID
- `getClassAverage()`: Calculates average of all students
- `getPassingStudentCount()`: Counts passing students
- `getFailingStudentCount()`: Counts failing students
- `getHonorsEligibleCount()`: Counts honors-eligible students

**Single Responsibility**: Only manages student collection

---

### **9. GradeManager (Concrete Class)**
**Purpose**: Manage all grade-related operations  
**Dependencies**: StudentManager (via constructor injection)  
**Responsibilities**:
- Add grades and update averages
- Retrieve grades by student
- Calculate various averages
- Generate grade reports

**Key Methods**:
- `addGrade(Grade)`: Adds grade and updates student average
- `getGradesByStudent(int)`: Returns all grades for a student
- `viewGradeByStudent(int)`: Displays formatted grade report
- `calculateCoreAverage(int)`: Calculates core subject average
- `calculateElectiveAverage(int)`: Calculates elective average
- `calculateOverallAverage(int)`: Calculates total average

**Dependency Injection Pattern**:
```java
public GradeManager(List<Student> studentRepository) {
    this.studentRepository = studentRepository;
}
```
**Benefits**:
- Loose coupling (not tightly bound to StudentManager)
- Easy to test (can inject mock lists)
- Flexible (works with any List<Student>)

---

### **10. Menu (Concrete Class)**
**Purpose**: Handle all user interface interactions  
**Dependencies**:
- StudentManager
- GradeManager
- Scanner

**Responsibilities**:
- Display menus
- Handle user input
- Delegate to managers
- Input validation

**Key Methods**:
- `start()`: Main application loop
- `displayMainMenu()`: Shows main menu
- `addStudent()`: UI for adding students
- `viewStudents()`: UI for viewing student list
- `recordGrade()`: UI for recording grades
- `viewGradeReport()`: UI for grade reports
- `selectCoreSubject()`: Subject selection UI
- `selectElectiveSubject()`: Subject selection UI
- `getIntInput()`: Safe integer input
- `getDoubleInput()`: Safe double input

**Separation of Concerns**:
- All UI logic in Menu
- Business logic delegated to managers
- Clean interaction points

---

### **11. Main (Entry Point)**
**Purpose**: Application entry point  
**Responsibilities**:
- Create component instances
- Initialize system
- Start menu

**Implementation**:
```java
public static void main(String[] args) {
    StudentManager studentManager = new StudentManager();
    GradeManager gradeManager = new GradeManager(studentManager.getAllStudents());
    Menu menu = new Menu(studentManager, gradeManager);
    menu.start();
    menu.close();
}
```

**Design Principles**:
- Single Responsibility (only initialization)
- Clean entry point
- Component injection pattern

## 🔄 Data Flow Diagrams

### **Adding a Student**
```
Menu.addStudent()
    │
    ├─→ Collect input (name, age, email, phone, type)
    │
    ├─→ Call StudentManager.getNextStudentId()
    │
    ├─→ Create Student (RegularStudent or HonorsStudent)
    │
    └─→ Call StudentManager.addStudent(student)
         │
         └─→ Add to List<Student>
```

### **Recording a Grade**
```
Menu.recordGrade()
    │
    ├─→ Get student ID
    │
    ├─→ Call StudentManager.getStudentById()
    │
    ├─→ Select subject (Core or Elective)
    │
    ├─→ Create Subject (CoreSubject or ElectiveSubject)
    │
    ├─→ Create Grade(studentId, subject, gradeValue)
    │
    └─→ Call GradeManager.addGrade(grade)
         │
         ├─→ Store in grades array
         │
         └─→ Call updateStudentAverage(studentId)
              │
              ├─→ Calculate average from all grades
              │
              └─→ Call Student.setAverageGrade(avg)
                   │
                   └─→ Calls Student.updateStatus()
                        │
                        └─→ Status changes to "Passing" or "Failing"
```

### **Viewing Grade Report**
```
Menu.viewGradeReport()
    │
    ├─→ Get student ID
    │
    ├─→ Call StudentManager.getStudentById()
    │
    └─→ Call GradeManager.viewGradeByStudent(studentId)
         │
         ├─→ Get all grades for student
         │
         ├─→ Sort by date
         │
         ├─→ Calculate averages:
         │   ├─→ calculateCoreAverage()
         │   ├─→ calculateElectiveAverage()
         │   └─→ calculateOverallAverage()
         │
         └─→ Display formatted report
```

## 🎯 SOLID Principles Implementation

### **S - Single Responsibility Principle**
| Class | Responsibility |
|-------|-----------------|
| Main | Initialize components |
| StudentManager | Manage student collection |
| GradeManager | Manage grades |
| Menu | Handle UI |
| Student | Define student properties |
| Grade | Encapsulate grade data |
| Subject | Define subject interface |

### **O - Open/Closed Principle**
- ✓ Abstract classes open for extension
- ✓ Concrete classes closed for modification
- ✓ New student types can be added by extending Student
- ✓ New subject types can be added by extending Subject

### **L - Liskov Substitution Principle**
- ✓ RegularStudent and HonorsStudent can be used anywhere Student is expected
- ✓ CoreSubject and ElectiveSubject can be used anywhere Subject is expected
- ✓ Polymorphic collections work correctly

### **I - Interface Segregation Principle**
- ✓ Abstract classes define only necessary methods
- ✓ Each class implements only relevant methods
- ✓ No forced implementation of unnecessary methods

### **D - Dependency Inversion Principle**
- ✓ GradeManager depends on List<Student> abstraction, not StudentManager
- ✓ Menu depends on manager abstractions
- ✓ Loose coupling enables flexibility

## 📈 Class Metrics

| Class | Lines | Methods | Fields |
|-------|-------|---------|--------|
| Student | 101 | 10 | 8 |
| RegularStudent | 39 | 3 | 1 |
| HonorsStudent | 48 | 4 | 1 |
| Subject | 53 | 6 | 2 |
| CoreSubject | 32 | 3 | 1 |
| ElectiveSubject | 32 | 3 | 1 |
| Grade | 103 | 11 | 5 |
| StudentManager | 146 | 11 | 2 |
| GradeManager | 242 | 15 | 3 |
| Menu | 342 | 20 | 3 |
| Main | 29 | 1 | 0 |

---

**Documentation Version**: 1.0  
**Last Updated**: January 7, 2026
