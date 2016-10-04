import java.util.Map;
import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
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

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("result", request.queryParams("item1"));
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("template", "templates/student.vtl");
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

    get("/teachers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("teachers", Teacher.all());
      model.put("template", "templates/teacher.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/teachers/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Teacher teacher = Teacher.find(Integer.parseInt(request.params(":id")));
      model.put("teacher", teacher);
      model.put("courses", teacher.getAllCourses());
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/course.vtl");
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

    get("/courses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("subjects", Course.Subjects.values());
      model.put("template", "templates/course.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Course course = new Course(request.queryParams("name"),request.queryParams("description"),request.queryParams("subject"),Integer.parseInt(request.queryParams("teacherid")));
      course.save();
      model.put("courses", Course.all());
      model.put("course", course);
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
