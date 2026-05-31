import java.util.*;

public class Student {

    private String studyNumber;
    private String name;

    private Set<Course> enrolledCourses = new HashSet<>();
    private Map<Course, Grade> courseGrades = new HashMap<>();

    public Student(String studyNumber, String name) {
        if (studyNumber == null || studyNumber.isBlank()) {
            throw new IllegalArgumentException("StudyNumber cannot be null.");
        }
        this.studyNumber = studyNumber;
    }

    public void enrollCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        enrolledCourses.add(course);
    }

    public void addGradeToCourse(Course course, Grade grade) {
        if (!enrolledCourses.contains(course)) {
            throw new IllegalArgumentException("Student is not enrolled in this course: " + course);
        }
        if (courseGrades.containsKey(course)) {
            throw new IllegalArgumentException("Student has got grade already: " + course + ": " + courseGrades.get(course).value());
        }
        courseGrades.put(course, grade);
    }

    public Set<Course> getEnrolledCourses() {
        return Collections.unmodifiableSet(enrolledCourses);
    }

    public Map<Course, Grade> getCourseGrades() {
        return Collections.unmodifiableMap(courseGrades);
    }

    public String getStudyNumber() {
        return studyNumber;
    }
    public String getName() {
        return name;
    }
}
