import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Lesson {
  private String name;
  private String reading;
  private int course_id;
  private String lecture;
  private int id;

  public Lesson (String name, String reading, String lecture, int course_id) {
    this.name = name;
    this.reading = reading;
    this.course_id = course_id;
    this.lecture = lecture;
  }

  public String getName() {
    return name;
  }

  public String getReading() {
    return reading;
  }

  public int getCourseId() {
    return course_id;
  }

  public String getLecture() {
    return lecture;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherLesson){
    if (!(otherLesson instanceof Lesson)) {
      return false;
    } else {
      Lesson newLesson = (Lesson) otherLesson;
      return this.id == newLesson.id && this.name.equals(newLesson.name) &&
        this.reading.equals(newLesson.reading) && this.lecture.equals(newLesson.lecture);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO lessons (name, reading, lecture, course_id) VALUES (:name, :reading, :lecture, :course_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("reading", this.reading)
        .addParameter("lecture", this.lecture)
        .addParameter("course_id", this.course_id)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
      String sql = "DELETE FROM lessons WHERE id=:id";
      try(Connection con = DB.sql2o.open()){
        con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    }

  public List<Assignment> getTeacherAssigments(){
    String sql = "SELECT * FROM assignments WHERE lesson_id=:id AND student_id IS NULL";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Assignment.class);
    }
  }

  public List<Assignment> getStudentAssigments(){
    String sql = "SELECT * FROM assignments WHERE lesson_id=:id AND student_id IS NOT NULL";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Assignment.class);
    }
  }

  public boolean hasUngraded(){
    String sql = "SELECT * FROM assignments WHERE lesson_id=:id AND student_id IS NOT NULL AND grade IS NULL";
    try(Connection con = DB.sql2o.open()) {
      Assignment ungraded =  con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Assignment.class);
      return ungraded != null;
    }
  }

  public static Lesson find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM lessons where id=:id";
      Lesson lesson = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Lesson.class);
      if(lesson == null){
        throw new IndexOutOfBoundsException("I'm sorry, I think this lesson does not exist");
      }
      return lesson;
    }
  }

  public static List<Lesson> all() {
    String sql = "SELECT * FROM lessons";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Lesson.class);
    }
  }
}
