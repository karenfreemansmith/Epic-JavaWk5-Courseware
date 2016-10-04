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
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //page where students can view a course they are enrolled in and links to lessons
    get("/students/:id/courses/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/student-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //page where students can view lessons and links to submit assignments
    get("/students/:id/courses/:id/lessons/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/student-lessons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    // page to edit or submit an assignment
    get("/students/:id/courses/:id/lessons/:id/assignments/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
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
      model.put("teachers", Teacher.all());
      model.put("teacher", teacher);
      model.put("template", "templates/teacher.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //individual teacher page, shows courses teaching and allows creating new courses
    get("/teachers/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/teacher.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //allows teacher to view/edit course, add lessons
    get("/teachers/:id/courses/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/teacher-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //allows teacher to view/edit lessons and add or access assignments for grading
    get("/teachers/:id/courses/:id/lessons/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/teacher-lessons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    // allows teachers to create assignments, lists student submissions, allows grading if in student submission
    get("/teachers/:id/courses/:id/lessons/:id/assignments/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/teacher-assignments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //main course page - view/search all courses
    get("/courses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //individual course page shows course details
    get("/courses/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/course.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

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
