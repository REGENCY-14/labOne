# Git Workflow Guide

## 📋 Complete Git Workflow for this Project

### **Initial Setup** (Already Done)

#### Branch Structure
```
main (production)
  ↓
dev (development)
  ↓
feature/* (feature branches)
```

### **Standard Development Workflow**

#### **Step 1: Create a New Feature Branch**
```bash
# Switch to dev branch
git checkout dev

# Pull latest changes
git pull origin dev

# Create and switch to new feature branch
git checkout -b feature/feature-name dev
```

**Naming Convention**: 
- `feature/student-validation` for new features
- `fix/grade-calculation-bug` for bug fixes
- `refactor/menu-restructure` for refactoring

---

#### **Step 2: Develop the Feature**

**Make regular commits**:
```bash
# Make changes to your files
# Stage changes
git add *.java

# Commit with descriptive message
git commit -m "feat: implement student validation

- Add email format validation
- Add phone number validation
- Add age range validation
- Add error messages for invalid input"
```

**Commit Message Format**:
```
type(scope): brief description

- Detailed point 1
- Detailed point 2
- Detailed point 3
```

**Types**:
- `feat`: New feature
- `fix`: Bug fix
- `refactor`: Code restructuring
- `docs`: Documentation
- `test`: Test additions
- `chore`: Maintenance
- `perf`: Performance improvements

**Example Commits**:
```bash
git commit -m "feat(grade): add GPA calculation feature

- Calculate weighted GPA for students
- Add GPA display in grade report
- Support different GPA scales"

git commit -m "fix(menu): resolve grade validation bug

- Grade validation now correctly rejects values > 100
- Display proper error message for invalid input"

git commit -m "refactor(student): improve encapsulation

- Change protected fields to private
- Add comprehensive getters/setters
- Update subclasses accordingly"
```

---

#### **Step 3: Merge to Dev**

**After testing locally**:
```bash
# Switch to dev
git checkout dev

# Pull latest dev changes
git pull origin dev

# Merge feature branch
git merge feature/feature-name

# Delete the feature branch
git branch -d feature/feature-name

# Push to dev
git push origin dev
```

**Merge Commit Message**:
```bash
git merge feature/feature-name -m "chore: merge feature-name to dev

- Implementation of new feature
- All tests passing
- Ready for testing"
```

---

#### **Step 4: Test on Dev Branch**

1. Compile all files: `javac *.java`
2. Run application: `java Main`
3. Test all features related to the change
4. Verify no regressions in existing features

---

#### **Step 5: Merge to Main**

**When feature is tested and ready for production**:
```bash
# Switch to main
git checkout main

# Pull latest main
git pull origin main

# Merge dev
git merge dev

# Push to main
git push origin main
```

**Release Merge Message**:
```bash
git merge dev -m "chore: release version 1.x.x

Features included:
- Feature 1
- Feature 2
- Bug fixes
- Improvements

All tests passing. Ready for production."
```

---

### **Common Git Commands**

#### **View Status**
```bash
# Check current branch and status
git status

# View commit history
git log --oneline -n 10

# View commit history with graph
git log --oneline --decorate --all --graph

# View changes in current branch
git diff main dev
```

#### **Branching Operations**
```bash
# List all branches
git branch -a

# Switch branch
git checkout branch-name

# Create new branch from specific branch
git checkout -b feature/name source-branch

# Delete local branch
git branch -d branch-name

# Delete remote branch
git push origin --delete branch-name
```

#### **Undoing Changes**
```bash
# Undo changes in working directory
git checkout -- filename

# Unstage files
git reset HEAD filename

# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes)
git reset --hard HEAD~1

# Revert a specific commit
git revert commit-hash
```

---

### **Workflow Example: Adding Student Validation Feature**

**1. Create Feature Branch**
```bash
git checkout -b feature/student-validation dev
```

**2. Implement Changes**
```bash
# Modify Student.java, add validation methods
git add Student.java
git commit -m "feat(student): add email validation

- Add isValidEmail() method
- Add validation in setEmail()
- Throw IllegalArgumentException for invalid emails"

# Modify Menu.java, use validation
git add Menu.java
git commit -m "feat(menu): integrate student validation

- Call Student validation methods before creation
- Display validation error messages
- Prevent invalid student creation"
```

**3. Test Locally**
```bash
javac *.java
java Main
# Test add student with invalid emails - should show error
# Test add student with valid emails - should succeed
```

**4. Merge to Dev**
```bash
git checkout dev
git pull origin dev
git merge feature/student-validation
git branch -d feature/student-validation
git push origin dev
```

**5. Test on Dev**
```bash
# Comprehensive testing
java Main
# Test all menu options
# Verify no regressions
```

**6. Merge to Main**
```bash
git checkout main
git pull origin main
git merge dev
git push origin main
```

---

### **Workflow Example: Fixing Grade Calculation Bug**

**1. Create Fix Branch**
```bash
git checkout -b fix/grade-average-bug dev
```

**2. Fix the Issue**
```bash
# Identify and fix bug in GradeManager.java
git add GradeManager.java
git commit -m "fix(grade): correct average calculation

- Fix: Division by zero when no grades exist
- Return -1 instead of 0 when no grades
- Add null checks in calculateOverallAverage()"
```

**3. Test the Fix**
```bash
javac *.java
java Main
# Test grade report for student with no grades
# Verify average displays correctly
# Test with various grade counts
```

**4. Merge to Dev**
```bash
git checkout dev
git pull origin dev
git merge fix/grade-average-bug
git branch -d fix/grade-average-bug
git push origin dev
```

**5. Release to Main**
```bash
git checkout main
git merge dev -m "chore: release bug fix for grade calculation"
git push origin main
```

---

### **Troubleshooting Guide**

#### **Merge Conflicts**
```bash
# If merge conflict occurs
git status  # Shows conflicted files

# Edit conflicted files manually
# Look for markers: <<<<<<<, =======, >>>>>>>
# Keep desired changes, remove markers

git add resolved-file.java
git commit -m "chore: resolve merge conflict"
```

#### **Accidental Commits**
```bash
# Undo last commit, keep changes staged
git reset --soft HEAD~1

# Undo last commit, keep changes unstaged
git reset --mixed HEAD~1

# Undo last commit, discard all changes
git reset --hard HEAD~1
```

#### **Wrong Branch Commit**
```bash
# Stash changes
git stash

# Switch to correct branch
git checkout correct-branch

# Apply stashed changes
git stash pop
```

---

### **Branch Protection Rules** (Recommended)

For production use:
- Require pull requests before merging to `main`
- Require code reviews (2+ reviewers)
- Require status checks (tests must pass)
- Require branches to be up to date
- Dismiss stale pull request approvals when new commits pushed
- Require signed commits

---

### **Current Repository Status**

**Branches**:
```
* main (production, tested)
  dev (development, integrating features)
  feature/refactor-oop-principles (completed)
```

**Latest Commits**:
```
- docs: Add comprehensive documentation (main)
- feat: Initial commit - refactored code with OOP principles
- first commit (initial code)
```

---

**Last Updated**: January 7, 2026  
**Version**: 1.0
