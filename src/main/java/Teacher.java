import org.sql2o.*;
import java.util.List;

public class Teacher {

  //TODO: method for getting all students?

  private String name;
  private String surname;
  private String bio;
  private String email;
  private int id;
  private int user_id;

  public Teacher (String name, String surname, String email, String bio){
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.bio = bio;
    this.save();
  }

  public int getId () {
    return id;
  }

  public String getName () {
    return name;
  }

  public String getSurname () {
    return surname;
  }

  public String getEmail () {
    return email;
  }

  public String getBio () {
    return bio;
  }

  public void setSurname (String surname) {
     this.surname = surname;
     try(Connection con = DB.sql2o.open()){
       String sql = "UPDATE users SET surname=:surname WHERE id=:user_id";
       con.createQuery(sql)
       .addParameter("user_id", user_id)
       .addParameter("surname", surname)
       .executeUpdate();
     }
  }

  public boolean setBio (String bio) {
      this.bio = bio;
      try(Connection con = DB.sql2o.open()){
        String sql = "UPDATE users SET bio=:bio WHERE id=:user_id";
        con.createQuery(sql)
        .addParameter("user_id", user_id)
        .addParameter("bio", bio)
        .executeUpdate();
        return true;
      }
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

  public List<Student> getAllStudents() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT students.id, students.user_id, users.email, users.surname, users.name FROM courses JOIN enrollment ON (enrollment.course_id = courses.id) JOIN students ON (enrollment.student_id = students.id) JOIN users ON (students.user_id = users.id) WHERE courses.teacher_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Student.class);
    }
  }

  public static List<Teacher> all() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT users.name, teachers.id, users.email, users.surname, users.bio, teachers.user_id FROM users INNER JOIN teachers ON (teachers.user_id = users.id)";
      return con.createQuery(sql)
        .executeAndFetch(Teacher.class);
    }
  }

  public static Teacher find(int id) {
  String sql = "SELECT users.name, users.email, users.surname, users.bio, teachers.id, teachers.user_id FROM users INNER JOIN teachers ON (teachers.user_id = users.id) WHERE teachers.id = :id";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Teacher.class);
    }
  }

  public static Teacher findByEmail(String email) {
  String sql = "SELECT users.name, users.email, users.surname, users.bio, teachers.id, teachers.user_id FROM users INNER JOIN teachers ON (teachers.user_id = users.id) WHERE users.email = :email";
    try(Connection con = DB.sql2o.open()) {
      Teacher teacher =  con.createQuery(sql)
        .addParameter("email", email)
        .executeAndFetchFirst(Teacher.class);
        if(teacher == null){
          throw new UnsupportedOperationException("We're sorry, We could not find that email amongst our teachers!");
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
    String sql = "INSERT INTO users (name, surname, email, bio) VALUES (:name, :surname, :email, :bio)";
    try(Connection con = DB.sql2o.open()) {
      this.user_id = (int) con.createQuery (sql, true)
      .addParameter("name", name)
      .addParameter("surname", surname)
      .addParameter("email", email)
      .addParameter("bio", bio)
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
