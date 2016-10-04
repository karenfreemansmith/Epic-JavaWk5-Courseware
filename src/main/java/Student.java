import org.sql2o.*;
import java.util.List;

//cumulative grade for course, cumulate gpa (or whatevs), get all grades for course

public class Student {

  private String name;
  private int id;
  private int user_id;

  public Student (String name){
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
  }

  public int enroll(int courseId) {
    String sql = "INSERT INTO enrollment (student_id, course_id) VALUES (:student_id, :course_id)";
    try(Connection con = DB.sql2o.open()) {
      return (int) con.createQuery (sql, true)
      .addParameter("student_id", this.id)
      .addParameter("course_id", courseId)
      .executeUpdate()
      .getKey();
    }
  }

  public List<Course> getCourses() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT courses.* FROM courses INNER JOIN enrollment ON (enrollment.course_id=courses.id) WHERE enrollment.student_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Course.class);
    }
  }

  public double getGradeAvgForCourse(int course_id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT grade FROM assignments JOIN lessons ON (lessons.id = assignments.lesson_id) WHERE lessons.course_id = :course_id AND assignments.student_id = :id";
      List<Integer> grades = con.createQuery(sql)
        .addParameter("course_id", course_id)
        .addParameter("id", id)
        .executeAndFetch(Integer.class);
      double average = (double) Math.round(grades.stream().mapToDouble(grade -> grade)
      .average().getAsDouble()*10)/10;
      return average;
    }
  }

  public Double getGradeAvg(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT grade FROM assignments WHERE assignments.student_id = :id";
      List<Integer> grades = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Integer.class);
      double average = (double) Math.round(grades.stream().mapToDouble(grade -> grade)
      .average().getAsDouble()*10)/10;
      return average;
    }
  }

  public static List<Student> all() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT * FROM users INNER JOIN students ON (students.user_id = users.id)";
      return con.createQuery(sql)
        .executeAndFetch(Student.class);
    }
  }

  public static Student find(int id) {
  String sql = "SELECT * FROM users INNER JOIN students ON (students.user_id = users.id) WHERE students.id = :id";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Student.class);
    }
  }

  public List<Assignment> getAssigments(){
    String sql = "SELECT * FROM assignments WHERE student_id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Assignment.class);
    }
  }



  public void delete() {
      String sql = "DELETE FROM students WHERE id=:id";
      try(Connection con = DB.sql2o.open()){
        con.createQuery(sql)
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
      sql = "INSERT INTO students (user_id) VALUES (:user_id)";
      this.id = (int) con.createQuery (sql, true)
      .addParameter("user_id", user_id)
      .executeUpdate()
      .getKey();
    }
  }

  @Override
  public boolean equals(Object otherStudent) {
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getId() == newStudent.getId();
    }
  }

  public List<Assignment> getCourseAssignments(int course_id){
    String sql = "SELECT assignments.* FROM assignments JOIN lessons ON (lessons.id = assignments.lesson_id) WHERE lessons.course_id = :course_id AND assignments.student_id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("course_id", course_id)
        .executeAndFetch(Assignment.class);
    }
  }
}
