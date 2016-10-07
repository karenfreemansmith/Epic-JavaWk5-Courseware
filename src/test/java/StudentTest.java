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
  public void setName_updatesName_true() {
    student1.setName("Todd");
    assertEquals("Todd", student1.getName());
  }

  @Test
  public void setName_updatesNameInDatabase_true() {
    student1.setName("Todd");
    Student newStudent = Student.find(student1.getId());
    assertEquals("Todd", newStudent.getName());
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
    Course testCourse1 = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), 1);
    testCourse1.save();
    Course testCourse2 = new Course("Intro to the Dark Arts", "Teaches you the Dark Arts", Course.Subjects.SUBJECT_CRAFTS.toString(), 2);
    testCourse2.save();
    student1.enroll(testCourse1.getId());
    student1.enroll(testCourse2.getId());
    assertEquals (2, student1.getCourses().size());
  }

  @Test
  public void delete_deletesCorrectStudent_null() {
    int tempId = student2.getId();
    Course testCourse = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), 1);
    student2.enroll(tempId);
    student2.delete();
    assertEquals(null, Student.find(tempId));
    assertFalse(testCourse.getStudents().contains(student2));
  }

  @Test public void getAssigments_returnsListOfSubmittedAssignments_ArrayList() {
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", 1, student1.getId());
    testAssignment2.turnIn();
    assertTrue(student1.getAssigments().contains(testAssignment2));
  }

  @Test public void getCourseAssignments_returnsListOfSubmittedAssignmentsByCourseId_int() {
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), 1);
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", lesson.getId(), student1.getId());
    testAssignment2.turnIn();
    Assignment testAssignment3 = new Assignment("Weave another Basket", "Here's my basket, it was ok.", lesson.getId()+1, student1.getId());
    testAssignment3.turnIn();
    assertEquals(1, student1.getCourseAssignments(course.getId()).size());
    assertTrue(student1.getCourseAssignments(course.getId()).contains(testAssignment2));
  }

  @Test public void getGradeAvgByCourse_returnsAvgOfAllGrades_double() {
    Teacher teacher = new Teacher("Steve");
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher.getId());
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", lesson.getId(), student1.getId());
    testAssignment2.turnIn();
    testAssignment2.grade(teacher.getId(), 81);
    Assignment testAssignment3 = new Assignment("Weave another Basket", "Here's my basket, it was ok.", lesson.getId(), student1.getId());
    testAssignment2.turnIn();
    testAssignment2.grade(teacher.getId(), 75);
    assertEquals(78, student1.getGradeAvgForCourse(course.getId()), .01);
  }

  @Test public void getGradeAvg_returnsListOfSubmittedAssignmentsByCourseId_int() {
    Teacher teacher = new Teacher("Steve");
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), teacher.getId());
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Course course2 = new Course("Classical coctails", "All about mixed drinks from The Olden Days", Course.Subjects.SUBJECT_HISTORY.toString(), teacher.getId());
    course2.save();
    Lesson lesson2 = new Lesson("Vodka", "Hard Liquor Through The Ages, by Booze McBoozeFace, ch 9", "Vodka - who invented it? Was it the Swedes? Was it some slavic country? Who knows!", course2.getId());
    lesson2.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", lesson.getId(), student1.getId());
    testAssignment2.turnIn();
    testAssignment2.grade(teacher.getId(), 81);
    Assignment testAssignment3 = new Assignment("Write a well-reasoned argument stating your position on the who invented vodka debate", "It was some slavic country bc lol @ swedes.", lesson2.getId(), student1.getId());
    testAssignment2.turnIn();
    testAssignment2.grade(teacher.getId(), 95);
    assertEquals(88, student1.getGradeAvgForCourse(course.getId()), .01);
  }

  @Test public void getLessonAssignments_returnsListOfSubmittedAssignmentsByLessonId_int() {
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.Subjects.SUBJECT_CRAFTS.toString(), 1);
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", lesson.getId(), student1.getId());
    testAssignment2.turnIn();
    Lesson lesson2 = new Lesson("Writing Blueprints in Pencil", "Baskets of Blueprints, Ch 2", "It's like writing, but it's drawing", course.getId());
    lesson2.save();
    Assignment testAssignment3 = new Assignment("Weave another Basket", "Here's my basket, it was ok.", lesson2.getId(), student1.getId());
    testAssignment3.turnIn();
    assertEquals(1, student1.getLessonAssignments(lesson.getId()).size());
    assertFalse(student1.getLessonAssignments(lesson.getId()).contains(testAssignment3));
  }

  @Test
  public void checkDuplicates_checksForDuplicateNamesInUserDatabase() {
    Student student3 = new Student("Karen");
    student2.save();
    student3.save();
    assertEquals(true, Student.checkDuplicates("Karen"));
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
