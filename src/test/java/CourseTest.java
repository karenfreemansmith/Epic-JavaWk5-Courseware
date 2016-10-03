import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CourseTest {
  Course testCourse;

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp() {
    testCourse = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.SUBJECT_CRAFTS, 1);
  }

  @Test
  public void Course_instantiates_true() {
    assertEquals(true, testCourse instanceof Course);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreSame_true(){
    Course testCourse2 = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.SUBJECT_CRAFTS, 1);
    assertTrue(testCourse.equals(testCourse2));
  }


  @Test
  public void save_insertsCourseIntoDatabase_Course() {
    testCourse.save();
    Course testCourse2 = null;
    try(Connection con = DB.sql2o.open()){
      testCourse2 = con.createQuery("SELECT * FROM courses WHERE name='Intro to Basket Weaving'").throwOnMappingFailure(false)
      .executeAndFetchFirst(Course.class);
    }
    assertTrue(testCourse2.equals(testCourse));
  }

  @Test
  public void find_returnsCourseWithSameId_secondCourse() {
    testCourse.save();
    Course testCourse2 = new Course("Intro to the Dark Arts", "Teaches you the Dark Arts", Course.SUBJECT_CRAFTS, 2);
    testCourse2.save();
    assertEquals(Course.find(testCourse2.getId()), testCourse2);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void find_throwsExceptionIfCourseNotFound() {
    Course.find(1);
  }

  @Test
  public void all_returnsAllInstancesOfCourse_true() {
    testCourse.save();
    Course testCourse2 = new Course("Intro to the Dark Arts", "Teaches you the Dark Arts", Course.SUBJECT_CRAFTS, 2);
    testCourse2.save();
    assertEquals(true, Course.all().get(0).equals(testCourse));
    assertEquals(true, Course.all().get(1).equals(testCourse2));
  }

  @Test
  public void getLessons_returnsAllLessonsOfCourse_true(){
    testCourse.save();
    Lesson lesson = new Lesson("Basket Weaving With Reeds", "I <3 reeds by Reed Reedchards, chapters 1-5", "lorem reedsum", testCourse.getId());
    lesson.save();
    Lesson testLesson2 = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", testCourse.getId());
    testLesson2.save();
    Lesson[] allLessons = {lesson, testLesson2};
    assertTrue(testCourse.getLessons().containsAll(Arrays.asList(allLessons)));
  }

}
