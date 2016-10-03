import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Course {
  private String name;
  private String description;
  private int teacher_id;
  private String subject;
  private int id;

  public static final String SUBJECT_LITERATURE = "Literature";
  public static final String SUBJECT_MATH = "Math";
  public static final String SUBJECT_HISTORY = "History";
  public static final String SUBJECT_LANGUAGES = "Languages";
  public static final String SUBJECT_SOCIETY = "Society";
  public static final String SUBJECT_CRAFTS = "Crafts";
  public static final String SUBJECT_ART = "Art";
  public static final String SUBJECT_SCIENCE = "Science";
  public static final String SUBJECT_SPORTS = "Sports";
  public static final String SUBJECT_BUSINESS = "Business";

  public Course (String name, String description, String subject, int teacher_id) {
    this.name = name;
    this.description = description;
    this.teacher_id = teacher_id;
    this.subject = subject;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getTeacherId() {
    return teacher_id;
  }

  public String getSubject() {
    return subject;
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


}
