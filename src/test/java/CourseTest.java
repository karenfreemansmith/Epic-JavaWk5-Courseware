import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class CourseTest {
  Course course;

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp() {
    course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.SUBJECT_CRAFTS, 1);
  }

  @Test
  public void Course_instantiates_true() {
    assertEquals(true, course instanceof Course);
  }


}
