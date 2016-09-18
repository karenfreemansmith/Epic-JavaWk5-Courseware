import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TaskTest {
  private Task task = new Task();

  // @Before
  // public void setUp() {
  //   //DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
  // }

  // @Test
  // public void Task_instantiates_true() {
  //   assertEquals(true, task instanceof Task);
  // }

  // @Test
  // public void equals_returnsTrueIfDescriptionsAretheSame() {
  //   Task firstTask = new Task("Mow the lawn");
  //   Task secondTask = new Task("Mow the lawn");
  //   assertTrue(firstTask.equals(secondTask));
  // }

  // @After
  // public void tearDown() {
  //   Task.clear();
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "DELETE FROM tasks *;";
  //     con.createQuery(sql).executeUpdate();
  //   }
  // }
}
