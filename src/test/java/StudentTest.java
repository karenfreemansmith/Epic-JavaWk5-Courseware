import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StudentTest {
  private Student student1;
  private Student student2;

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/courseware_test", null, null);
    student1 = new Student("Brian");
    student2 = new Student("Karen");
  }

  @Test
  public void Student_instantiates_true() {
    assertEquals(true, student1 instanceof Student);
  }

  @Test
  public void save_returnsId_true() {
    assertTrue(student1.getId()>=1);
  }

  @Test
  public void getName_returnsCorrectName_true() {
    assertEquals("Brian", student1.getName());
  }

  @Test
  public void setName_updatesNameInDatabase_true() {
    student1.setName("Todd");
    assertEquals("Todd", student1.getName());
  }

  @Test
  public void find_returnsCorrectStudent_true() {
    assertTrue(Student.find(student1.getId()).equals(student1));
  }

  @Test
  public void all_returnsAllInstances_true() {
    assertTrue(Student.all().size()>=1);
  }

  @Test
  public void enroll_returnsIdFromDatabase() {
    assertTrue(student1.enroll(1)>0);
  }

  @Test
  public void getCourses_returnsAllCourses_true() {
    Course testCourse1 = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.SUBJECT_CRAFTS, 1);
    testCourse1.save();
    Course testCourse2 = new Course("Intro to the Dark Arts", "Teaches you the Dark Arts", Course.SUBJECT_CRAFTS, 2);
    testCourse2.save();
    student1.enroll(testCourse1.getId());
    student1.enroll(testCourse2.getId());
    assertEquals (2, student1.getCourses().size());

  }

  @Test
  public void delete_deletesCorrectStudent_null() {
    int tempId = student2.getId();
    student2.delete();
    assertEquals(null, Student.find(tempId));
  }

  @Test public void getFinishedAssigments_returnsListOfSubmittedAssignments_ArrayList() {
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", 1, student1.getId());
    testAssignment2.turnIn();
    assertTrue(student1.getAssigments().contains(testAssignment2));
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM students *;";
      con.createQuery(sql).executeUpdate();
      sql = "DELETE FROM users *;";
      con.createQuery(sql).executeUpdate();
    }
  }
}
