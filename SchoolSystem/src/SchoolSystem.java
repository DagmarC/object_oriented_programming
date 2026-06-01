import java.util.*;

public class SchoolSystem {

    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();

    public void addCourse(Course course) {
        Objects.requireNonNull(course, "Course cannot be null");

        if(courses.containsKey(course.code())) {
            throw new IllegalStateException("Course with code " + course.code() + " already exists.");
        }
        courses.put(course.code(), course);
    }

    public void addStudent(Student student) {
        Objects.requireNonNull(student, "Student cannot be null");

        if(students.containsKey(student.getStudyNumber())) {
            throw new IllegalStateException(
                    "Student " + student.getStudyNumber() + " already exists.");
        }
        students.put(student.getStudyNumber(), student);
    }

    public void enrollStudentInCourse(String studentNumber, String courseCode) {
        Course course = getCourseOrThrow(courseCode);
        Student student = getStudentOrThrow(studentNumber);

        student.enrollCourse(course);
    }

    public void assignGrade(String studentNumber, String courseCode, int gradeValue) {
        Course course = getCourseOrThrow(courseCode);
        Student student = getStudentOrThrow(studentNumber);

        requireEnrollment(student, course);
        if (student.hasGradeInCourse(course)) {
            throw new IllegalStateException(
                    "Student already has a grade in course: " + course.code());
        }
        Grade grade = new Grade(gradeValue, course.lecturer());
        student.addGradeToCourse(course, grade);
    }

    public void updateGrade(String studentNumber, String courseCode, int gradeValue) {
        Course course = getCourseOrThrow(courseCode);
        Student student = getStudentOrThrow(studentNumber);

        requireEnrollment(student, course);
        if (!student.hasGradeInCourse(course)) {
            throw new IllegalStateException(
                    "Student " + studentNumber + " has no grade in course: " + course.code());
        }

        Grade grade = new Grade(gradeValue, course.lecturer());
        student.updateGrade(course, grade);
    }

    private void requireEnrollment(Student student, Course course) {

        if (!student.isEnrolledIn(course)) {
            throw new IllegalStateException("Student is not enrolled in this course: " + course.code());
        }
    }


    public Map<Course, Grade> getAllGradesPerStudent(String studentNumber) {
        Student student = getStudentOrThrow(studentNumber);
        return student.getCourseGrades();
    }

    public Optional<Grade> getGradeForCourse(String courseCode, String studentNumber) {
        Course course = getCourseOrThrow(courseCode);
        Student student = getStudentOrThrow(studentNumber);

        requireEnrollment(student, course);

        return student.getGradeFromCourse(course);
    }

    public Student getStudentOrThrow(String studentNumber) {
        Student student = students.get(studentNumber);
        if (student == null) {
            throw new IllegalArgumentException("Student " + studentNumber + " does not exist.");
        }
        return student;
    }

    public Course getCourseOrThrow(String courseNumber) {
        Course course = courses.get(courseNumber);
        if (course == null) {
            throw new IllegalArgumentException("Course " + courseNumber + " does not exist.");
        }
        return course;
    }

    public Map<String, Student> getStudents() {
        return Collections.unmodifiableMap(students);
    }

    public Map<String, Course> getCourses() {
        return Collections.unmodifiableMap(courses);
    }
}
