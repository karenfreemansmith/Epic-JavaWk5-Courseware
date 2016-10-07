import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class LessonTest {
  Lesson testLesson;

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp() {
    testLesson = new Lesson("Basket Weaving With Reeds", "I <3 reeds by Reed Reedchards, chapters 1-5", "lorem reedsum", 1);
  }

  @Test
  public void Lesson_instantiates_true() {
    assertEquals(true, testLesson instanceof Lesson);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreSame_true(){
    Lesson testLesson2 = new Lesson("Basket Weaving With Reeds", "I <3 reeds by Reed Reedchards, chapters 1-5", "lorem reedsum", 1);
    assertTrue(testLesson.equals(testLesson2));
  }


  @Test
  public void save_insertsLessonIntoDatabase_Lesson() {
    testLesson.save();
    Lesson testLesson2 = null;
    try(Connection con = DB.sql2o.open()){
      testLesson2 = con.createQuery("SELECT * FROM lessons WHERE name='Basket Weaving With Reeds'")
                       .executeAndFetchFirst(Lesson.class);
    }
    assertTrue(testLesson2.equals(testLesson));
  }

  @Test
  public void setName_updatesNameInDatabase_true() {
    testLesson.save();
    testLesson.setName("Todd");
    Lesson newLesson = Lesson.find(testLesson.getId());
    assertEquals("Todd", newLesson.getName());
  }

  @Test
  public void setLecture_updatesLectureInDatabase_true() {
    testLesson.save();
    testLesson.setLecture("How not to fall asleep during a lecture, part 1 of 101");
    Lesson newLesson = Lesson.find(testLesson.getId());
    assertEquals("How not to fall asleep during a lecture, part 1 of 101", newLesson.getLecture());
  }

  @Test
  public void setReading_updatesReadingInDatabase_true() {
    testLesson.save();
    testLesson.setReading("A Story of Sleeping");
    Lesson newLesson = Lesson.find(testLesson.getId());
    assertEquals("A Story of Sleeping", newLesson.getReading());
  }

  @Test
  public void find_returnsLessonWithSameId_secondLesson() {
    testLesson.save();
    Lesson testLesson2 = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", 1);
    testLesson2.save();
    assertEquals(Lesson.find(testLesson2.getId()), testLesson2);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void find_throwsExceptionIfLessonNotFound() {
    Lesson.find(1);
  }

  @Test
  public void all_returnsAllInstancesOfLesson_true() {
    testLesson.save();
    Lesson testLesson2 = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", 1);
    testLesson2.save();
    assertTrue(Lesson.all().contains(testLesson));
    assertTrue(Lesson.all().contains(testLesson2));
  }

  @Test public void getTeacherAssigments_returnsListOfUnsubmittedAssignments_ArrayList() {
    testLesson.save();
    Student student = new Student("Karen", "MacSurname", "email@something.com");
    Assignment testAssignment = new Assignment("Weave a Basket", "Step one, weave basket. Take a picture, and turn it in.", testLesson.getId());
    testAssignment.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", testLesson.getId(), student.getId());
    testAssignment2.turnIn();
    assertTrue(testLesson.getTeacherAssigments().get(0).equals(testAssignment));
    assertFalse(testLesson.getTeacherAssigments().contains(testAssignment2));
  }

  @Test public void getStudentAssigments_returnsListOfSubmittedAssignments_ArrayList() {
    testLesson.save();
    Student student = new Student("Karen", "MacSurname", "email@something.com");
    Assignment testAssignment = new Assignment("Weave a Basket", "Step one, weave basket. Take a picture, and turn it in.", testLesson.getId());
    testAssignment.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", testLesson.getId(), student.getId());
    testAssignment2.turnIn();
    assertFalse(testLesson.getStudentAssigments().get(0).equals(testAssignment));
    assertTrue(testLesson.getStudentAssigments().contains(testAssignment2));
  }

  @Test public void hasUngraded_returnsTrueIfThereAreUngradedAssignments_true() {
    Teacher teacher = new Teacher("Karen", "MacSurname", "email@moreemail.com", "Seventy three Ph.Ds from ITT Technical Institute");
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher.getId());
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", lesson.getId(), 311);
    testAssignment2.turnIn();
    assertTrue(lesson.hasUngraded());
  }

  @Test public void hasUngraded_returnsFalseIfAllAssignmentsAreGraded_true() {
    Teacher teacher = new Teacher("Karen", "MacSurname", "email@moreemail.com", "Seventy three Ph.Ds from ITT Technical Institute");
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher.getId());
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", lesson.getId(), 311);
    testAssignment2.turnIn();
    testAssignment2.grade(teacher.getId(), 3.6);
    assertFalse(lesson.hasUngraded());
  }

  @Test public void hasUngraded_returnsFalseNoAssignmentsTurnedIn_true() {
    Teacher teacher = new Teacher("Karen", "MacSurname", "email@moreemail.com", "Seventy three Ph.Ds from ITT Technical Institute");
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher.getId());
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Weave a basket based on the reading.", lesson.getId());
    testAssignment2.save();
    assertFalse(lesson.hasUngraded());
  }
}
