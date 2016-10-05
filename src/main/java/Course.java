import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

//TODO: add a method to retrieve only courses with active teacher

public class Course {
  private String name;
  private String description;
  private int teacher_id;
  private String subject;
  private int id;

  public static final int NO_TEACHER = 0;

  public enum Subjects {
    SUBJECT_LITERATURE("Literature"),
    SUBJECT_MATH("Math"),
    SUBJECT_HISTORY("History"),
    SUBJECT_LANGUAGES("Languages"),
    SUBJECT_SOCIETY("Society"),
    SUBJECT_POLITICS("Law & Government"),
    SUBJECT_CRAFTS("Crafts"),
    SUBJECT_ART("Art"),
    SUBJECT_SCIENCE("Science"),
    SUBJECT_SPORTS("Sports"),
    SUBJECT_TECHNOLOGY("Technology"),
    SUBJECT_BUSINESS("Business");

    private final String subjectName;

    private Subjects(String subjectName){
      this.subjectName = subjectName;
    }

    @Override
    public String toString(){
      return subjectName;
    }
  }

  public Course (String name, String description, String subject, int teacher_id) {
    this.name = name;
    this.description = description;
    this.teacher_id = teacher_id;
    this.subject = subject;
  }

  public String getName() {
    return name;
  }

<<<<<<< HEAD
  public void setName(String name){
    this.name = name;
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE courses SET name=:name WHERE id=:id";
      con.createQuery(sql)
      .addParameter("id", id)
      .addParameter("name", name)
=======
  public void setName(String name) {
    this.name = name;
    String sql = "UPDATE courses SET name=:name WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
      .addParameter("id", this.id)
      .addParameter("name", this.name)
>>>>>>> afternoon2
      .executeUpdate();
    }
  }

  public String getDescription() {
    return description;
  }

<<<<<<< HEAD
  public void setDescription(String description){
    this.description = description;
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE courses SET description=:description WHERE id=:id";
      con.createQuery(sql)
      .addParameter("id", id)
      .addParameter("description", description)
=======
  public void setDescription(String description) {
    this.description = description;
    String sql = "UPDATE courses SET description=:description WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
      .addParameter("id", this.id)
      .addParameter("description", this.description)
>>>>>>> afternoon2
      .executeUpdate();
    }
  }

  public int getTeacherId() {
    return teacher_id;
  }

  public String getSubject() {
    return subject;
  }

<<<<<<< HEAD
  public void setSubject(String subject){
    this.subject = subject;
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE courses SET subject=:subject WHERE id=:id";
      con.createQuery(sql)
      .addParameter("id", id)
      .addParameter("subject", subject)
=======
  public void setSubject(String subject) {
    this.subject= subject;
    String sql = "UPDATE courses SET subject=:subject WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
      .addParameter("id", this.id)
      .addParameter("subject", this.subject)
>>>>>>> afternoon2
      .executeUpdate();
    }
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherCourse){
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.id == newCourse.id && this.name.equals(newCourse.name) &&
        this.description.equals(newCourse.description) && this.subject.equals(newCourse.subject);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses (name, description, subject, teacher_id) VALUES (:name, :description, :subject, :teacher_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("description", this.description)
        .addParameter("subject", this.subject)
        .addParameter("teacher_id", this.teacher_id)
        .executeUpdate()
        .getKey();
    }
  }

  //delete lessons as well
  public void delete() {
      String sql = "DELETE FROM courses WHERE id=:id";
      String lessonsSql = "DELETE FROM lessons WHERE course_id=:id";
      try(Connection con = DB.sql2o.open()){
        con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
        con.createQuery(lessonsSql)
        .addParameter("id", id)
        .executeUpdate();
      }
    }

  public List<Lesson> getLessons(){
    String sql = "SELECT * FROM lessons WHERE course_id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Lesson.class);
    }
  }

  public boolean hazUngraded(){
    String sql = "SELECT assignments.id FROM assignments JOIN lessons ON (assignments.lesson_id = lessons.id) WHERE lessons.course_id=:id AND assignments.student_id IS NOT NULL AND grade IS NULL";
    try(Connection con = DB.sql2o.open()) {
      Integer ungraded =  con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Integer.class);
      return ungraded != null;
    }
  }

  public List<Student> getStudents() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT students.* FROM students INNER JOIN enrollment ON (enrollment.student_id=students.id) WHERE enrollment.course_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Student.class);
    }
  }


  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course course = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Course.class);
      if(course == null){
        throw new IndexOutOfBoundsException("I'm sorry, I think this course does not exist");
      }
      return course;
    }
  }

  public static List<Course> all() {
    String sql = "SELECT * FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Course.class);
    }
  }

  public static List<Course> allWithTeachers() {
    String sql = "SELECT * FROM courses WHERE teacher_id != :no_teacher";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("no_teacher", NO_TEACHER)
        .executeAndFetch(Course.class);
    }
  }

}
