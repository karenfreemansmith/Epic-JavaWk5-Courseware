import java.util.Map;
import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import spark.Request;
import static spark.Spark.*;

//figure out how to upload images directly
//add edit paths for everything

public class App {
  private static final String FLASH_MESSAGE_KEY = "flash_message";
  private static final String USER_ID_KEY = "user_id";
  private static final String USER_TYPE_KEY = "user_type";

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    ProcessBuilder process = new ProcessBuilder();
    Integer port;
    if (process.environment().get("PORT") != null) {
       port = Integer.parseInt(process.environment().get("PORT"));
    } else {
       port = 4567;
    }

    setPort(port);
    //before filters
    before("/teachers/:id/*", (request, response) -> {
      String usertype = request.session().attribute(USER_TYPE_KEY);
      Integer userid = request.session().attribute(USER_ID_KEY);
      if(userid == null){
        setFlashMessage(request, "You need to log in to see this page!");
        response.redirect("/");
        halt();
      } else if(!usertype.equals("teacher")){
        setFlashMessage(request, "You need to be a teacher to see this page!");
        response.redirect("/");
        halt();
      } else if(userid != Integer.parseInt(request.params("id"))){
        setFlashMessage(request, "Sorry, you are not authorized to see this!");
        response.redirect("/teachers/" + userid);
        halt();
      }
    });

    before("/students/:id/*", (request, response) -> {
      String usertype = request.session().attribute(USER_TYPE_KEY);
      Integer userid = request.session().attribute(USER_ID_KEY);
      if(userid == null){
        setFlashMessage(request, "You need to log in to see this page!");
        response.redirect("/");
        halt();
      } else if(!usertype.equals("student")){
        setFlashMessage(request, "You need to be a student to see this page!");
        response.redirect("/");
        halt();
      } else if(userid != Integer.parseInt(request.params("id"))){
        setFlashMessage(request, "Sorry, you are not authorized to see this!");
        response.redirect("/students/" + userid);
        halt();
      }
    });

    //main home page
    get("/", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //post for login page
    post("/login", (request, response) -> {
      String username = request.queryParams("username");
      String type = request.queryParams("type");
      if(type.equals("student")){
        Student student = Student.findByName(username);
        request.session().attribute(USER_ID_KEY, student.getId());
        request.session().attribute(USER_TYPE_KEY, "student");
        setFlashMessage(request, "Hello, " + student.getName() + "! Thank you for logging in!");
        response.redirect("/students/" + student.getId());
      } else {
        Teacher teacher = Teacher.findByName(username);
        request.session().attribute(USER_ID_KEY, teacher.getId());
        request.session().attribute(USER_TYPE_KEY, "teacher");
        setFlashMessage(request, "Hello, " + teacher.getName() + "! Thank you for logging in!");
        response.redirect("/teachers/" + teacher.getId());
      }
      return null;
    });
    //Log out
    post("/logout", (request, response) -> {
      request.session().removeAttribute(USER_ID_KEY);
      request.session().removeAttribute(USER_TYPE_KEY);
      setFlashMessage(request, "Logged out");
      response.redirect("/");
      return null;
    });
    //main student page - signup/register/signin
    get("/students", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      model.put("students", Student.all());
      model.put("courses", Course.allWithTeachers());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/students", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      String url = "/students";
      String name = request.queryParams("name");
      Student.checkDuplicates(name);
      if(Student.checkDuplicates(name) == true) {
        response.redirect(url);
        halt();
      }
      Student student = new Student(request.queryParams("name"));
      Course course = Course.find(Integer.parseInt(request.queryParams("course")));
      student.enroll(course.getId());
      setFlashMessage(request, "That username has already been taken!");
      model.put("courses", Course.allWithTeachers());
      model.put("student", student);
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //individual sudent page, shows courses enrolled in and allows new enrollments
    get("/students/:id", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Student student = Student.find(Integer.parseInt(request.params("id")));
      model.put("courses", Course.allForStudent(student.getId()));
      model.put("student", student);
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //post from student page to enroll in a new course
    post("/students/:id", (request, response) -> {
      Course course = Course.find(Integer.parseInt(request.queryParams("course")));
      Student student = Student.find(Integer.parseInt(request.params("id")));
      student.enroll(course.getId());
      response.redirect("/students/" + student.getId());
      return null;
    });
    //page where students can view a course they are enrolled in and links to lessons
    get("/students/:studentId/courses/:courseId", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      Student student = Student.find(Integer.parseInt(request.params("studentId")));
      Teacher teacher = Teacher.find(course.getTeacherId());
      model.put("teacher", teacher);
      model.put("student", student);
      model.put("course", course);
      model.put("template", "templates/student-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //page where students can view lessons and links to submit assignments
    get("/students/:studentId/courses/:courseId/lessons/:lessonId", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      Student student = Student.find(Integer.parseInt(request.params("studentId")));
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      model.put("student", student);
      model.put("lesson", lesson);
      model.put("course", course);
      model.put("template", "templates/student-lessons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    // page to edit or submit an assignment
    get("/students/:studentId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      Student student = Student.find(Integer.parseInt(request.params("studentId")));
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      List<Assignment> turnedInAssignments  = student.getLessonAssignments(lesson.getId());
      Assignment studentAssignment = null;
      for(Assignment turnedInAssignment : turnedInAssignments){
        if(turnedInAssignment.getName().equals(assignment.getName())){
          studentAssignment = turnedInAssignment;
        }
      }
      model.put("student", student);
      model.put("lesson", lesson);
      model.put("course", course);
      model.put("assignment", assignment);
      model.put("studentAssignment", studentAssignment);
      model.put("template", "templates/student-assignments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/students/:studentId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId", (request, response) -> {
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      int lessonId = Integer.parseInt(request.params("lessonId"));
      int studentId = Integer.parseInt(request.params("studentId"));
      int courseId = Integer.parseInt(request.params("courseId"));
      Assignment studentAssignment = new Assignment(assignment.getName(), request.queryParams("content"), assignment.getLessonId(), studentId);
      studentAssignment.turnIn();
      setFlashMessage(request, "Assignment Submitted!");
      String urlString = "/students/" + studentId + "/courses/" + courseId + "/lessons/" + lessonId + "/assignments/" + assignment.getId();
      response.redirect(urlString);
      return null;
    });
    //update assignment if student wants to change it after submitting it
    post("/students/:studentId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId/edit", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      int course_id = Integer.parseInt(request.params("courseId"));
      int student_id = Integer.parseInt(request.params("studentId"));
      int lesson_id = Integer.parseInt(request.params("lessonId"));
      int assignment_id = Integer.parseInt(request.params("assignmentId"));
      Assignment studentAssignment = Assignment.find(Integer.parseInt(request.queryParams("submission_id")));
      studentAssignment.setContent(request.queryParams("content"));
      studentAssignment.setDateSubmitted();
      setFlashMessage(request, "Assignment updated!");
      String urlString = "/students/" + student_id + "/courses/" + course_id + "/lessons/" + lesson_id + "/assignments/" + assignment_id;
      response.redirect(urlString);
      return null;
    });
    //main teacher page - signup/apply/signin
    get("/teachers", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      model.put("teachers", Teacher.all());
      model.put("template", "templates/teachers.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/teachers", (request, response) -> {
      String url = "/teachers";
      String name = request.queryParams("name");
      if(Student.checkDuplicates(name) == true) {
        response.redirect(url);
        halt();
      }
      Teacher teacher = new Teacher(request.queryParams("name"));

      response.redirect("/teachers/" + teacher.getId());
      return null;
    });
    //individual teacher page, shows courses teaching and allows creating new courses
    get("/teachers/:id", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Teacher teacher = Teacher.find(Integer.parseInt(request.params("id")));
      model.put("teacher", teacher);
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/teacher.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/teachers/:id", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Course course = new Course(request.queryParams("title"),request.queryParams("description"),request.queryParams("subject"),Integer.parseInt(request.params("id")));
      course.save();
      Teacher teacher = Teacher.find(Integer.parseInt(request.params("id")));
      model.put("teacher", teacher);
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/teacher.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //allows teacher to view/edit course, add lessons
    get("/teachers/:teacherId/courses/:courseId", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      Teacher teacher = Teacher.find(Integer.parseInt(request.params("teacherId")));
      model.put("teacher", teacher);
      model.put("course", course);
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/teacher-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/teachers/:teacherId/courses/:courseId/edit", (request, response) -> {
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      course.setName(request.queryParams("name"));
      course.setDescription(request.queryParams("description"));
      course.setSubject(request.queryParams("subject"));
      setFlashMessage(request, "Course updated!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id;
      response.redirect(urlString);
      return null;
    });
    //delete lesson if teacher wants to delete it
    post("/teachers/:teacherId/courses/:courseId/delete", (request, response) -> {
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      course.delete();
      setFlashMessage(request, "Course deleted!");
      String urlString = "/teachers/" + teacher_id;
      response.redirect(urlString);
      return null;
    });
    //create new lesson (redirects to lesson page)
    post("/teachers/:teacherId/courses/:courseId/lessons/new", (request, response) -> {
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      Lesson lesson = new Lesson(request.queryParams("name"), request.queryParams("reading"), request.queryParams("lecture"), course_id);
      lesson.save();
      setFlashMessage(request, "Lesson added!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id + "/lessons/" + lesson.getId();
      response.redirect(urlString);
      return null;
    });

    //allows teacher to view/edit lessons and add or access assignments for grading
    get("/teachers/:teacherId/courses/:courseId/lessons/:lessonId", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      model.put("course_id", course_id);
      model.put("teacher_id", teacher_id);
      model.put("lesson", lesson);
      model.put("template", "templates/teacher-lessons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //post for edit lesson from individual lesson page
    post("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/edit", (request, response) -> {
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      lesson.setName(request.queryParams("name"));
      lesson.setLecture(request.queryParams("lecture"));
      lesson.setReading(request.queryParams("reading"));
      setFlashMessage(request, "Lesson updated!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id + "/lessons/" + lesson.getId();
      response.redirect(urlString);
      return null;
    });
    //delete lesson if teacher wants to delete it
    post("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/delete", (request, response) -> {
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      lesson.delete();
      setFlashMessage(request, "Lesson deleted!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id;
      response.redirect(urlString);
      return null;
    });
    // posts from assignments new to teacher/:id/courses/:id/lessons/getId()
    post("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/assignments/new", (request, response) -> {
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      String name = request.queryParams("name");
      String content = request.queryParams("content");
      Assignment assignment = new Assignment(name, content, lesson.getId());
      assignment.save();
      setFlashMessage(request, "Assignment added!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id + "/lessons/" + lesson.getId();
      response.redirect(urlString);
      return null;
    });
    // allows teachers to access student assignments, allows grading
    get("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      Student student = Student.find(assignment.getStudentId());
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      int lesson_id = Integer.parseInt(request.params("lessonId"));
      model.put("course_id", course_id);
      model.put("teacher_id", teacher_id);
      model.put("lesson_id", lesson_id);
      model.put("student", student);
      model.put("assignment", assignment);
      model.put("template", "templates/teacher-assignments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //adds grade to assignment
    post("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId", (request, response) -> {
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      double grade = Double.parseDouble(request.queryParams("grade"));
      assignment.grade(Integer.parseInt(request.queryParams("teacher_id")), grade);
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      int lesson_id = Integer.parseInt(request.params("lessonId"));
      setFlashMessage(request, "Grade Added!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id + "/lessons/" + lesson_id + "/assignments/" + assignment.getId();
      response.redirect(urlString);
      return null;
    });
    //post for edit assignment - assignments can be edited from their lesson page, so this redirects back to lesson page
    post("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId/edit", (request, response) -> {
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      int lesson_id = Integer.parseInt(request.params("lessonId"));
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      assignment.setName(request.queryParams("name"));
      assignment.setContent(request.queryParams("content"));
      setFlashMessage(request, "Assignment updated!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id + "/lessons/" + lesson_id;
      response.redirect(urlString);
      return null;
    });
    //delete assignment if teacher wants to delete it
    post("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId/delete", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      int lesson_id = Integer.parseInt(request.params("lessonId"));
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      assignment.delete();
      setFlashMessage(request, "Assignment deleted!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id + "/lessons/" + lesson_id;
      response.redirect(urlString);
      return null;
    });
    //main course page - view/search all courses
    //TODO: add some indicator if a course is not being taught
    get("/courses", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      model.put("courses", Course.all());
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //individual course page shows course details
    get("/courses/:id", (request, response) -> {
      Map<String, Object> model = modelWithLogin(request);
      Course course = Course.find(Integer.parseInt(request.params("id")));
      Teacher teacher = null;
      if(course.getTeacherId() != Course.NO_TEACHER){
        teacher = Teacher.find(course.getTeacherId());
      }
      model.put("teacher", teacher);
      model.put("ext", ".jpg");
      model.put("course", course);
      model.put("template", "templates/course.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    exception(NumberFormatException.class, (exc, req, res) -> {
      res.status(404);
      VelocityTemplateEngine engine = new VelocityTemplateEngine();
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", "looks like that page doesn't exist!");
      model.put("template", "templates/notfound.vtl");
      String html = engine.render(new ModelAndView(model, layout));
      res.body(html);
    });

    exception(IndexOutOfBoundsException.class, (exc, req, res) -> {
      res.status(404);
      VelocityTemplateEngine engine = new VelocityTemplateEngine();
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", exc.getMessage());
      model.put("template", "templates/notfound.vtl");
      String html = engine.render(new ModelAndView(model, layout));
      res.body(html);
    });

    exception(UnsupportedOperationException.class, (exc, req, res) -> {
      res.status(500);
      VelocityTemplateEngine engine = new VelocityTemplateEngine();
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", exc.getMessage());
      model.put("template", "templates/notfound.vtl");
      String html = engine.render(new ModelAndView(model, layout));
      res.body(html);
    });

    exception(NullPointerException.class, (exc, req, res) -> {
      res.status(500);
      VelocityTemplateEngine engine = new VelocityTemplateEngine();
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", "Congratulations! You have found the secret instant graduation page! Just send us $10,000,000 and you will get your diploma!");
      model.put("template", "templates/notfound.vtl");
      String html = engine.render(new ModelAndView(model, layout));
      res.body(html);
    });
  }
  //flash message
  private static void setFlashMessage(Request request, String message){
    request.session().attribute(FLASH_MESSAGE_KEY, message);
  }

  private static String getFlashMessage(Request request){
    if(request.session(false) == null){
      return null;
    }
    if(!request.session().attributes().contains(FLASH_MESSAGE_KEY)){
      return null;
    }
    return (String) request.session().attribute(FLASH_MESSAGE_KEY);
  }

  private static String captureFlashMessage(Request request){
    String message = getFlashMessage(request);
    if(message != null){
      request.session().removeAttribute(FLASH_MESSAGE_KEY);
    }
    return message;
  }

  private static Map<String, Object> modelWithLogin(Request request){
    Map<String, Object> model = new HashMap<String, Object>();
    if(request.session(false) != null){
      model.put("user_type", request.session().attribute(USER_TYPE_KEY));
      model.put("user_id", request.session().attribute(USER_ID_KEY));
      model.put("message", captureFlashMessage(request));
    }
    return model;
  }
}
