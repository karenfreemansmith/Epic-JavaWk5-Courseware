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
    testCourse = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), 1);
  }

  @Test
  public void Course_instantiates_true() {
    assertEquals(true, testCourse instanceof Course);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreSame_true(){
    Course testCourse2 = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), 1);
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
  public void setName_updatesNameInDatabase_true() {
    testCourse.save();
    testCourse.setName("Todd");
    Course newCourse = Course.find(testCourse.getId());
    assertEquals("Todd", newCourse.getName());
  }

  @Test
  public void setDescription_updatesDescriptionInDatabase_true() {
    testCourse.save();
    testCourse.setDescription("Todd");
    Course newCourse = Course.find(testCourse.getId());
    assertEquals("Todd", newCourse.getDescription());
  }

  @Test
  public void setSubject_updatesSubjectInDatabase_true() {
    testCourse.save();
    testCourse.setSubject("Todd");
    Course newCourse = Course.find(testCourse.getId());
    assertEquals("Todd", newCourse.getSubject());
  }

  @Test
  public void find_returnsCourseWithSameId_secondCourse() {
    testCourse.save();
    Course testCourse2 = new Course("Intro to the Dark Arts", "Teaches you the Dark Arts", Course.Subjects.SUBJECT_CRAFTS.toString(), 2);
    testCourse2.save();
    assertEquals(Course.find(testCourse2.getId()), testCourse2);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void find_throwsExceptionIfCourseNotFound() {
    Course.find(1);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void delete_removesFromDatabase() {
    testCourse.save();
    testCourse.delete();
    Course.find(testCourse.getId());
  }

  @Test
  public void delete_removesCourseLessonsFromDatabase() {
    testCourse.save();
    Lesson testLesson2 = new Lesson("Basket Weaving With Reeds", "I <3 reeds by Reed Reedchards, chapters 1-5", "lorem reedsum", testCourse.getId());
    testCourse.delete();
    assertEquals(0, testCourse.getLessons().size());
  }

  @Test
  public void all_returnsAllInstancesOfCourse_true() {
    testCourse.save();
    Course testCourse2 = new Course("Intro to the Dark Arts", "Teaches you the Dark Arts", Course.Subjects.SUBJECT_CRAFTS.toString(), 2);
    testCourse2.save();
    assertTrue(Course.all().contains(testCourse));
    assertTrue(Course.all().contains(testCourse2));
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

  @Test
  public void getStudents_returnsAllStudents_true() {
    testCourse.save();
    Student student1 = new Student("Brian");
    Student student2 = new Student("Karen");
    student1.enroll(testCourse.getId());
    student2.enroll(testCourse.getId());
    assertEquals (2, testCourse.getStudents().size());
    assertTrue(testCourse.getStudents().contains(student1));
  }

  @Test
  public void allWithTeachers_returnsAllCoursesWithTeachersAssigned_true() {
    Teacher teacher1 = new Teacher("Lenore");
    Course testCourse1 = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher1.getId());
    testCourse1.save();
    Teacher teacher2 = new Teacher("Brian");
    Course testCourse2 = new Course("Intro to the Dark Arts", "Teaches you the Dark Arts", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher2.getId());
    testCourse2.save();
    teacher2.delete();
    assertEquals(1, Course.allWithTeachers().size());
    assertFalse(Course.allWithTeachers().contains(testCourse2));
    assertTrue(Course.allWithTeachers().contains(testCourse1));
  }

  @Test public void hazUngraded_returnsFalseIfAllAssignmentsAreGraded_true() {
    Teacher teacher = new Teacher("Steve");
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher.getId());
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", lesson.getId(), 311);
    testAssignment2.turnIn();
    testAssignment2.grade(teacher.getId(), 3.6);
    assertFalse(course.hazUngraded());
  }

  @Test public void hazUngraded_returnsFalseNoAssignmentsTurnedIn_true() {
    Teacher teacher = new Teacher("Steve");
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher.getId());
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Weave a basket based on the reading.", lesson.getId());
    testAssignment2.save();
    assertFalse(course.hazUngraded());
  }
}
