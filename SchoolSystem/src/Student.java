import java.util.*;

public class Student {

    private final String studyNumber;
    private final String name;

    private final Set<Course> enrolledCourses = new HashSet<>();
    private final Map<Course, Grade> courseGrades = new HashMap<>();

    public Student(String studyNumber, String name) {
        Objects.requireNonNull(studyNumber, "Student number name cannot be null");
        Objects.requireNonNull(name, "Student name cannot be null");

        if (studyNumber.isBlank()) {throw new IllegalArgumentException("Student number cannot be blank");}
        if (name.isBlank()) {throw new IllegalArgumentException("Student name cannot be blank");}

        this.studyNumber = studyNumber;
        this.name = name;
    }

    public void enrollCourse(Course course) {
        Objects.requireNonNull(course, "Course cannot be null");
        enrolledCourses.add(course);
    }

    public boolean isEnrolledIn(Course course) {
        Objects.requireNonNull(course, "Course cannot be null");
        return enrolledCourses.contains(course);
    }

    public boolean hasGradeInCourse(Course course) {
        Objects.requireNonNull(course, "Course cannot be null.");
        return courseGrades.containsKey(course);
    }

    public void addGradeToCourse(Course course, Grade grade) {
        courseGrades.put(course, grade);
    }

    public void updateGrade(Course course, Grade grade) {
        courseGrades.put(course, grade);
    }

    public Optional<Grade> getGradeFromCourse(Course course) {
        Objects.requireNonNull(course, "Course cannot be null.");
        return Optional.ofNullable(courseGrades.get(course));
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
