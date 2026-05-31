import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SchoolSystem {

    Map<String, Student> students = new HashMap<>();
    Map<String, Course> courses = new HashMap<>();

    public void addCourse(Course course) {
        if (course == null) {
            throw new NullPointerException("Course cannot be null.");
        }
        if(courses.containsKey(course.code())) {
            throw new IllegalArgumentException("Course with code " + course.code() + " already exists.");
        }
        courses.put(course.code(), course);
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new NullPointerException("Student cannot be null.");
        }
        if(students.containsKey(student)) {
            throw new IllegalArgumentException("Student already exists.");
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

        Grade grade = new Grade(gradeValue, course.lecturer());
        student.addGradeToCourse(course, grade);
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
}
