import org.sql2o.*;
import java.util.List;

public class Teacher {

  //TODO: method for getting all students?

  private String name;
  private int id;
  private int user_id;

  public Teacher (String name){
    this.name = name;
    this.save();
  }

  public int getId () {
    return id;
  }

  public String getName () {
    return name;
  }

  public void setName (String name) {
     this.name = name;
     try(Connection con = DB.sql2o.open()){
       String sql = "UPDATE users SET name=:name WHERE id=:user_id";
       con.createQuery(sql)
       .addParameter("user_id", user_id)
       .addParameter("name", name)
       .executeUpdate();
     }
  }

  public Course addCourse(String name, String description, String subject) {
    Course course = new Course(name, description, subject, this.id);
    course.save();
    return course;
  }

  public List<Course> getAllCourses() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT * FROM courses WHERE teacher_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Course.class);
    }
  }

  public static List<Teacher> all() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT users.name, teachers.id, teachers.user_id FROM users INNER JOIN teachers ON (teachers.user_id = users.id)";
      return con.createQuery(sql)
        .executeAndFetch(Teacher.class);
    }
  }

  public static Teacher find(int id) {
  String sql = "SELECT users.name, teachers.id, teachers.user_id FROM users INNER JOIN teachers ON (teachers.user_id = users.id) WHERE teachers.id = :id";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Teacher.class);
    }
  }

  public static Teacher findByName(String name) {
  String sql = "SELECT users.name, teachers.id, teachers.user_id FROM users INNER JOIN teachers ON (teachers.user_id = users.id) WHERE users.name = :name";
    try(Connection con = DB.sql2o.open()) {
      Teacher teacher =  con.createQuery(sql)
        .addParameter("name", name)
        .executeAndFetchFirst(Teacher.class);
        if(teacher == null){
          throw new UnsupportedOperationException("We're sorry, We could not find that username amongst our teachers!");
        }
        return teacher;
    }
  }

  public void delete() {
      String sql = "DELETE FROM teachers WHERE id=:id";
      String userSql = "DELETE FROM users WHERE id=:user_id";
      String coursesSql = "UPDATE courses SET teacher_id=:no_teacher WHERE teacher_id = :id";
      try(Connection con = DB.sql2o.open()){
        con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
        con.createQuery(userSql)
        .addParameter("user_id", user_id)
        .executeUpdate();
        con.createQuery(coursesSql)
        .addParameter("no_teacher", Course.NO_TEACHER)
        .addParameter("id", id)
        .executeUpdate();
      }
    }

  public void save() {
    String sql = "INSERT INTO users (name) VALUES (:name)";
    try(Connection con = DB.sql2o.open()) {
      this.user_id = (int) con.createQuery (sql, true)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
      sql = "INSERT INTO teachers (user_id) VALUES (:user_id)";
      this.id = (int) con.createQuery (sql, true)
      .addParameter("user_id", user_id)
      .executeUpdate()
      .getKey();
    }
  }

  @Override
  public boolean equals(Object otherTeacher) {
    if (!(otherTeacher instanceof Teacher)) {
      return false;
    } else {
      Teacher newTeacher = (Teacher) otherTeacher;
      return this.getId() == newTeacher.getId();
    }
  }
}
