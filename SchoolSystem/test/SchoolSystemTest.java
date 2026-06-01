import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SchoolSystemTest {

    private SchoolSystem schoolSystem;

    @BeforeEach
    void setUp() {
        schoolSystem = new SchoolSystem();
    }

    // HELPER METHODS
    private Course createCourse() {
        return new Course("ANDAT","Data Analysis", new Lecturer("Bujok"));
    }

    private Student createStudent() {
        return new Student("R123", "Anezka Rat");
    }

    @Nested
    @DisplayName("Method::addCourse()")
    class addCourseTests {
        @Test
        @DisplayName("Course can be successfully added.")
        void addValidCourse() {
            // ARRANGE
            Lecturer lecturer = new Lecturer("Vajgl");
            Course course = new Course("OPR2", "Objective programming", lecturer);

            // ACT
            schoolSystem.addCourse(course);

            // ASSERT
            assertAll(
                    () -> assertEquals(1, schoolSystem.getCourses().size(), "There should be exactly one course."),
                    () -> assertEquals(course, schoolSystem.getCourses().get(course.code())),
                    () -> assertEquals(course.title(), schoolSystem.getCourses().get(course.code()).title())
            );
        }

        @Test
        @DisplayName("Add duplicated course -> throw IllegalArgumentException.")
        void addDuplicateCourse() {
            Course course = createCourse();
            schoolSystem.addCourse(course);

            IllegalStateException e = assertThrows(IllegalStateException.class, () -> schoolSystem.addCourse(course));

            assertEquals("Course with code " + course.code() + " already exists.", e.getMessage());
        }

        @Test
        @DisplayName("Add null course -> throw IllegalArgumentException.")
        void addNullCourse() {
            assertThrows(NullPointerException.class, () -> schoolSystem.addCourse(null));
        }

        @Test
        @DisplayName("Add null student -> throw IllegalArgumentException.")
        void addNullStudent() {
            assertThrows(NullPointerException.class, () -> schoolSystem.addStudent(null));
        }
    }

    @Test
    void addStudent() {
        Student student = new Student("R123","Anezka Rat");
        schoolSystem.addStudent(student);
        assertAll(
                () -> assertEquals(1, schoolSystem.getStudents().size()),
                () -> assertEquals(student, schoolSystem.getStudents().get(student.getStudyNumber())),
                () -> assertEquals(student.getName(), schoolSystem.getStudents().get(student.getStudyNumber()).getName())
        );
    }


    @Test
    void addStudentDuplicity() {
        String studentCode = "R123";
        Student student = new Student(studentCode,"Anezka Rat");
        schoolSystem.addStudent(student);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> schoolSystem.addStudent(student));

        assertEquals("Student " + studentCode + " already exists.", e.getMessage());

    }

    @Nested
    @DisplayName("Enrollment and Grading Process")
    class EnrollmentAndGradingTests {

        private Course course;
        private Student student;

        @BeforeEach
        void prepareData() {
            course = createCourse();
            student = createStudent();
            schoolSystem.addCourse(course);
            schoolSystem.addStudent(student);
        }

        @Test
        @DisplayName("Student successfully enrolled to Course.")
        void enrollStudentInCourse() {
            schoolSystem.enrollStudentInCourse(student.getStudyNumber(), course.code());

            assertAll("Course & Student enrollment.",
                    () -> assertTrue(student.getEnrolledCourses().contains(course)),
                    () -> assertEquals(1, student.getEnrolledCourses().size())
            );
        }

        @Test
        @DisplayName("Successfully assign Grade to Course.")
        void assignGrade() {
            schoolSystem.enrollStudentInCourse(student.getStudyNumber(), course.code());

            schoolSystem.assignGrade(student.getStudyNumber(), course.code(), 1);

            assertEquals(1, student.getGradeFromCourse(course).orElseThrow().value());
        }

        @Test
        @DisplayName("Try to assign Grade to a non-existing Student.")
        void assignGradeInvalidStudent() {
            String studyNumber = "R111";

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> schoolSystem.assignGrade(studyNumber, course.code(), 1));

            assertEquals("Student " + studyNumber + " does not exist.", e.getMessage());
        }

        @Test
        @DisplayName("Try to assign Grade to a not enrolled student.")
        void assignGradeNotEnrolledStudent() {
            // student is already created in setup, but he is not enrolled

            IllegalStateException e = assertThrows(IllegalStateException.class,
                    () -> schoolSystem.assignGrade(student.getStudyNumber(), course.code(), 1));

            assertEquals("Student is not enrolled in this course: " + course.code(), e.getMessage());
        }

        @Test
        @DisplayName("Try to assign Grade to a Student that already has been assigned.")
        void assignGradeAlreadyHasBeenAssigned() {
            // ARRANGE
            schoolSystem.enrollStudentInCourse(student.getStudyNumber(), course.code());
            schoolSystem.assignGrade(student.getStudyNumber(), course.code(), 1);

            //ACT
            IllegalStateException e = assertThrows(IllegalStateException.class,
                    () -> schoolSystem.assignGrade(student.getStudyNumber(), course.code(), 1));

            int courseGrade =  student.getCourseGrades().get(course).value();
            assertEquals("Student already has a grade in course: " + course.code(), e.getMessage());
        }

        @Test
        @DisplayName("Update Grade.")
        void updateGrade() {
            schoolSystem.enrollStudentInCourse(student.getStudyNumber(), course.code());
            schoolSystem.assignGrade(student.getStudyNumber(), course.code(), 3);

            schoolSystem.updateGrade(student.getStudyNumber(), course.code(), 1);

            assertEquals(Optional.of(1), student.getGradeFromCourse(course).map(Grade::value));
        }

        @Test
        @DisplayName("Update Grade - Invalid Student.")
        void updateGradeInvalidStudent() {
            String studyNumber = "R111";

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> schoolSystem.updateGrade(studyNumber, course.code(), 1));

            assertEquals("Student " + studyNumber + " does not exist.", e.getMessage());
        }

        @Test
        @DisplayName("Try to update Grade to a not enrolled student.")
        void updateGradeNotEnrolledStudent() {
            // student is already created in setup, but he is not enrolled
            IllegalStateException e = assertThrows(IllegalStateException.class,
                    () -> schoolSystem.updateGrade(student.getStudyNumber(), course.code(), 1));

            assertEquals("Student is not enrolled in this course: " + course.code(), e.getMessage());
        }

        @Test
        @DisplayName("Try to update Grade to a Student that hasn't been assigned a grade, yet.")
        void updateGradeNotAssigned() {
            // ARRANGE
            schoolSystem.enrollStudentInCourse(student.getStudyNumber(), course.code());

            //ACT
            IllegalStateException e = assertThrows(IllegalStateException.class,
                    () -> schoolSystem.updateGrade(student.getStudyNumber(), course.code(), 1));

            // ASSERT
            assertEquals("Student " + student.getStudyNumber() +
                    " has no grade in course: " + course.code(), e.getMessage());
        }
    }

    @Nested
    @DisplayName("Student is already enrolled adn graded.")
    class WithGradedStudent{

        private Course course;
        private Student student; // enrolled and graded
        private Student student2; // enrolled, not graded
        private Student student3; // not enrolled, not graded

        LocalDateTime beforeAssignment;

        @BeforeEach
        void prepareData() {
            course = createCourse();
            student = createStudent();

            String s2 = "R2";
            student2 = new Student(s2, "John");

            String s3 = "R3";
            student3 = new Student(s3, "Joanna");


            schoolSystem.addCourse(course);

            schoolSystem.addStudent(student);
            schoolSystem.addStudent(student2);
            schoolSystem.addStudent(student3);

            schoolSystem.enrollStudentInCourse(student.getStudyNumber(), course.code());
            schoolSystem.enrollStudentInCourse(s2, course.code());

            beforeAssignment = LocalDateTime.now();
            schoolSystem.assignGrade(student.getStudyNumber(), course.code(), 1);
        }

        @Test
        @DisplayName("Get Grade for a Course")
        void getGradeForCourse() {
            Grade grade = schoolSystem.getGradeForCourse(course.code(), student.getStudyNumber())
                    .orElseThrow(() -> new AssertionError("Setup failed: grade should be present.")) ;

            assertAll("Grade should have following parameters: ",
                    () -> assertEquals(1, grade.value()),
                    () -> assertEquals(course.lecturer(), grade.lecturer()),
                    () -> assertNotNull(grade.dateInserted())
            );
        }

        @Test
        @DisplayName( "Get grade from course - not graded yet")
        void getGradeFromCourseNotGraded() {
            // S2 is not graded, yet
            Optional<Grade> grade = schoolSystem.getGradeForCourse(course.code(), student2.getStudyNumber());
            assertTrue(grade.isEmpty());
        }

        @Test
        @DisplayName( "Get grade from course - not enrolled yet")
        void getGradeFromCourseNotEnrolled() {
            // S3 is not neither enrolled nor graded, yet
            assertThrows(IllegalStateException.class,
                    () -> schoolSystem.getGradeForCourse(course.code(), student3.getStudyNumber()));
        }

        @Test
        @DisplayName("Test that grade date is after beforeAssignment date.")
        void getGradeDateAfterConcreteDate() {
            Grade grade = schoolSystem.getGradeForCourse(course.code(), student.getStudyNumber())
                    .orElseThrow();
            assertTrue(beforeAssignment.isBefore(grade.dateInserted()));
        }

    }
}