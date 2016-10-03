import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

//TODO: test for exceptions
//TODO: test the date_submitted somehow? 

public class AssignmentTest {
  Assignment testAssignment;
  Assignment turnedInAssignment;

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp() {
    testAssignment = new Assignment("Weave a Basket", "Step one, weave basket. Take a picture, and turn it in.", 1);
    turnedInAssignment = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", 1, 311);
  }

  @Test
  public void Assignment_instantiates_true() {
    assertEquals(true, testAssignment instanceof Assignment);
    assertEquals(true, turnedInAssignment instanceof Assignment);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreSame_true(){
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Step one, weave basket. Take a picture, and turn it in.", 1);
    assertTrue(testAssignment.equals(testAssignment2));
  }


  @Test
  public void save_insertsAssignmentIntoDatabase_Assignment() {
    testAssignment.save();
    Assignment testAssignment2 = null;
    try(Connection con = DB.sql2o.open()){
      testAssignment2 = con.createQuery("SELECT * FROM assignments WHERE name='Weave a Basket' AND student_id IS NULL")
                       .executeAndFetchFirst(Assignment.class);
    }
    assertTrue(testAssignment2.equals(testAssignment));
  }

  @Test
  public void turnIn_insertsAssignmentIntoDatabaseWithStudentId_Assignment() {
    turnedInAssignment.turnIn();
    Assignment turnedInAssignment2 = null;
    try(Connection con = DB.sql2o.open()){
      turnedInAssignment2 = con.createQuery("SELECT * FROM assignments WHERE name='Weave a Basket' AND student_id IS NOT NULL")
                       .executeAndFetchFirst(Assignment.class);
    }
    assertTrue(turnedInAssignment2.equals(turnedInAssignment));
  }

  @Test
  public void find_returnsAssignmentWithSameId_secondAssignment() {
    testAssignment.save();
    Assignment testAssignment2 = new Assignment("Weave With Palm Fronds", "Befriend Your Friends.", 1);
    testAssignment2.save();
    assertEquals(Assignment.find(testAssignment2.getId()), testAssignment2);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void find_throwsExceptionIfAssignmentNotFound() {
    Assignment.find(1);
  }

  @Test
  public void all_returnsAllInstancesOfAssignment_true() {
    testAssignment.save();
    Assignment testAssignment2 = new Assignment("Weave With Palm Fronds", "Befriend Your Friends.", 1);
    testAssignment2.save();
    assertEquals(true, Assignment.all().get(0).equals(testAssignment));
    assertEquals(true, Assignment.all().get(1).equals(testAssignment2));
  }

  @Test
  public void grade_updatesGradeOnTurnedInAssignment_float(){
    Teacher teacher = new Teacher("Steve");
    Course course = new Course("Intro to Basket Weaving", "Teaches you to weave baskets", Course.SUBJECT_CRAFTS, teacher.getId());
    course.save();
    Lesson lesson = new Lesson("Basket Weaving With Palm Fronds", "Fronds Are Your Friends, by Palm Palmerson chapter 7", "palms palms palms palmitty palms", course.getId());
    lesson.save();
    Assignment testAssignment2 = new Assignment("Weave a Basket", "Here's my basket, hope it's the best.", lesson.getId(), 311);
    testAssignment2.turnIn();
    testAssignment2.grade(teacher.getId(), 3.6);
    Assignment gradedAssignment = Assignment.find(testAssignment2.getId());
    assertEquals(3.6, gradedAssignment.getGrade(), .01);
  }
}
