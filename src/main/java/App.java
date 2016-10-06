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

    //main home page
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //main student page - signup/register/signin
    get("/students", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("students", Student.all());
      model.put("courses", Course.allWithTeachers());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/students", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Student student = new Student(request.queryParams("name"));
      Course course = Course.find(Integer.parseInt(request.queryParams("course")));
      student.enroll(course.getId());
      model.put("courses", Course.allWithTeachers());
      model.put("student", student);
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //individual sudent page, shows courses enrolled in and allows new enrollments
    get("/students/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Student student = Student.find(Integer.parseInt(request.params("id")));
      model.put("courses", Course.allWithTeachers());
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
      Map<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      Student student = Student.find(Integer.parseInt(request.params("studentId")));
      model.put("student", student);
      model.put("course", course);
      model.put("template", "templates/student-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //page where students can view lessons and links to submit assignments
    get("/students/:studentId/courses/:courseId/lessons/:lessonId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
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
      Map<String, Object> model = new HashMap<String, Object>();
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
      model.put("message", captureFlashMessage(request));
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
    //main teacher page - signup/apply/signin
    get("/teachers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("teachers", Teacher.all());
      model.put("template", "templates/teachers.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/teachers", (request, response) -> {
      Teacher teacher = new Teacher(request.queryParams("name"));
      response.redirect("/teachers/" + teacher.getId());
      return null;
    });
    //individual teacher page, shows courses teaching and allows creating new courses
    get("/teachers/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Teacher teacher = Teacher.find(Integer.parseInt(request.params("id")));
      model.put("teacher", teacher);
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/teacher.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/teachers/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
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
      Map<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      Teacher teacher = Teacher.find(Integer.parseInt(request.params("teacherId")));
      // Student student = Student.find(course.get());
      // model.put("student", student);
      model.put("teacher", teacher);
      model.put("course", course);
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/teacher-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/teachers/:teacherId/courses/:courseId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      course.setName(request.queryParams("name"));
      course.setDescription(request.queryParams("description"));
      course.setSubject(request.queryParams("subject"));
      setFlashMessage(request, "Lesson updated!");
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id;
      response.redirect(urlString);
      return null;
    });
    post("/teachers/:teacherId/courses/:courseId/lessons/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
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
      Map<String, Object> model = new HashMap<String, Object>();
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      model.put("course_id", course_id);
      model.put("teacher_id", teacher_id);
      model.put("message", captureFlashMessage(request));
      model.put("lesson", lesson);
      model.put("template", "templates/teacher-lessons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    // posts from assignments new to teacher/:id/courses/:id/lessons/getId()
    post("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/assignments/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      String name = request.queryParams("name");
      String content = request.queryParams("content");
      Assignment assignment = new Assignment(name, content, lesson.getId());
      assignment.save();
      String urlString = "/teachers/" + teacher_id + "/courses/" + course_id + "/lessons/" + lesson.getId();
      response.redirect(urlString);
      return null;
    });
    // allows teachers to create assignments, lists student submissions, allows grading if in student submission
    get("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      Student student = Student.find(assignment.getStudentId());
      int course_id = Integer.parseInt(request.params("courseId"));
      int teacher_id = Integer.parseInt(request.params("teacherId"));
      int lesson_id = Integer.parseInt(request.params("lessonId"));
      model.put("course_id", course_id);
      model.put("teacher_id", teacher_id);
      model.put("lesson_id", lesson_id);
      model.put("student", student);
      model.put("message", captureFlashMessage(request));
      model.put("assignment", assignment);
      model.put("template", "templates/teacher-assignments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
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
    //main course page - view/search all courses
    //TODO: add some indicator if a course is not being taught
    get("/courses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //individual course page shows course details
    get("/courses/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("id")));
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
}
