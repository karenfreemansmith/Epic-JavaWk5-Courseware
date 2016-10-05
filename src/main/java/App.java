import java.util.Map;
import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import spark.Request;
import static spark.Spark.*;

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
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/students", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Student student = new Student(request.queryParams("name"));
      model.put("students", Student.all());
      model.put("student", student);
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //individual sudent page, shows courses enrolled in and allows new enrollments
    get("/students/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Student student = Student.find(Integer.parseInt(request.params("id")));
      model.put("student", student);
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //page where students can view a course they are enrolled in and links to lessons
    get("/students/:studentId/courses/:courseId/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("courseId")));
      model.put("course", course);
      model.put("template", "templates/student-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //page where students can view lessons and links to submit assignments
    get("/students/:studentId/courses/:courseId/lessons/:lessonId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      model.put("lesson", lesson);
      model.put("template", "templates/student-lessons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    // page to edit or submit an assignment
    get("/students/:studentId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      model.put("assignment", assignment);
      model.put("template", "templates/student-assignments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //main teacher page - signup/apply/signin
    get("/teachers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("teachers", Teacher.all());
      model.put("template", "templates/teachers.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/teachers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Teacher teacher = new Teacher(request.queryParams("name"));
      response.redirect("/teachers/" + teacher.getId());
      model.put("template", "templates/teacher.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
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
      model.put("course", course);
      model.put("template", "templates/teacher-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //allows teacher to view/edit lessons and add or access assignments for grading
    get("/teachers/:teacherId/courses/:courseId/lessons/:lessonId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Lesson lesson = Lesson.find(Integer.parseInt(request.params("lessonId")));
      model.put("lesson", lesson);
      model.put("template", "templates/teacher-lessons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    // allows teachers to create assignments, lists student submissions, allows grading if in student submission
    get("/teachers/:teacherId/courses/:courseId/lessons/:lessonId/assignments/:assignmentId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Assignment assignment = Assignment.find(Integer.parseInt(request.params("assignmentId")));
      model.put("assignment", assignment);
      model.put("template", "templates/teacher-assignments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //main course page - view/search all courses
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
