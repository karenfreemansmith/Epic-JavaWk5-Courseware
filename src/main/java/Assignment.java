import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.sql.Timestamp;

public class Assignment {
  private String name;
  private double grade;
  private String content;
  private int student_id;
  private int lesson_id;
  private Timestamp date_submitted;
  private int id;

  public Assignment (String name, String content, int lesson_id) {
    this.name = name;
    this.content = content;
    this.lesson_id = lesson_id;
  }

  public Assignment (String name, String content, int lesson_id, int student_id) {
    this.name = name;
    this.content = content;
    this.lesson_id = lesson_id;
    this.student_id = student_id;
  }

  public String getName() {
    return name;
  }

  public String getContent() {
    return content;
  }

  public int getLessonId() {
    return lesson_id;
  }

  public int getStudentId() {
    return student_id;
  }

  public double getGrade() {
    return grade;
  }

  public Timestamp getDateSubmitted() {
    return date_submitted;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherAssignment){
    if (!(otherAssignment instanceof Assignment)) {
      return false;
    } else {
      Assignment newAssignment = (Assignment) otherAssignment;
      return this.id == newAssignment.id && this.name.equals(newAssignment.name) && this.content.equals(newAssignment.content) && this.lesson_id == newAssignment.lesson_id;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO assignments (name, content, lesson_id) VALUES (:name, :content, :lesson_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("content", this.content)
        .addParameter("lesson_id", this.lesson_id)
        .executeUpdate()
        .getKey();
    }
  }

  public void turnIn() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO assignments (name, content, student_id, lesson_id, date_submitted) VALUES (:name, :content, :student_id, :lesson_id, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("content", this.content)
        .addParameter("student_id", this.student_id)
        .addParameter("lesson_id", this.lesson_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static Assignment find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM assignments where id=:id";
      Assignment assignment = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Assignment.class);
      if(assignment == null){
        throw new IndexOutOfBoundsException("I'm sorry, I think this assignment does not exist");
      }
      return assignment;
    }
  }

  public static List<Assignment> all() {
    String sql = "SELECT * FROM assignments";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Assignment.class);
    }
  }

  public void grade(int teacher_id, double grade) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT teachers.* FROM teachers JOIN courses ON (teachers.id = courses.teacher_id) JOIN lessons ON (courses.id = lessons.course_id) WHERE lessons.id = :lesson_id";
      Teacher teacher = con.createQuery(sql).addParameter("lesson_id", this.lesson_id)
                           .executeAndFetchFirst(Teacher.class);
      if(teacher != null && teacher.getId()== teacher_id){
        if(this.student_id != 0){
          this.grade = grade;
          String gradeSql = "UPDATE assignments SET grade=:grade WHERE id=:id";
          con.createQuery(gradeSql).addParameter("grade", grade).addParameter("id", this.id).executeUpdate();
        } else {
          throw new UnsupportedOperationException("This assignment does not need to be graded");
        }
      } else {
        throw new IllegalArgumentException("I'm sorry, you do not have permission to grade this assignment PS: wtf leave");
      }
    }
  }
}
