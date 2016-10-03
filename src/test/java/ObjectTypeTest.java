import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ObjectTypeTest {
  private ObjectType obj1 = new ObjectType();
  private ObjectType obj2 = new ObjectType();

  @Before
  public void setUp() {
    //DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
  }

  @Test
  public void ObjectType_instantiates_true() {
    assertEquals(true, obj1 instanceof ObjectType);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM objectType *;";
      con.createQuery(sql).executeUpdate();
    }
  }
}
