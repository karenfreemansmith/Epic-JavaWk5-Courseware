import org.sql2o.*;

public class DBhome {
  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do", "postgres", "change-password");
}
