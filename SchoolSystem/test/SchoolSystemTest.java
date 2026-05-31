import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SchoolSystemTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Before Class::Create Courses and Students");

        Student s1 = new Student("R123", "Alex");
        Student s2 = new Student("R124", "Beta");
        Student s3 = new Student("R125", "Cyril");
        Student s4 = new Student("R126", "Daniel");

        Course opr2 = new Course("OPR2", "Objektove Prog", new Lecturer("Vajgl"));
        Course mat2 = new Course("MAT2", "Diskretna Matematika", new Lecturer("Janosek"));
        Course andat = new Course("ANDAT", "Analyza Dat", new  Lecturer("Bujok"));
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("Before Method::Create SchoolSystem");
        SchoolSystem schoolSystem = new SchoolSystem();
    }

    @Test
    public void addCourseTest() {


    }
}