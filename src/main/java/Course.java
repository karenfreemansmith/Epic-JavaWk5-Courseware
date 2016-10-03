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


}
