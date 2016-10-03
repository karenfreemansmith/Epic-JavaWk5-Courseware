import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/courseware_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteCoursesQuery = "DELETE FROM courses *;";
      con.createQuery(deleteCoursesQuery).executeUpdate();
      String deleteLessonsQuery = "DELETE FROM lessons *;";
      con.createQuery(deleteLessonsQuery).executeUpdate();
      String deleteAssignmentsQuery = "DELETE FROM assignments *;";
      con.createQuery(deleteAssignmentsQuery).executeUpdate();
    }
  }

}
