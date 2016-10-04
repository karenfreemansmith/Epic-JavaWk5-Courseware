// experimenting with routing...

get("/", (request, response) -> {}, new VelocityTemplateEngine()); //main home page
get("/students", (request, response) -> {}, new VelocityTemplateEngine()); //main student page - signup/register/signin
get("/students/:id", (request, response) -> {}, new VelocityTemplateEngine()); //individual sudent page, shows courses enrolled in and allows new enrollments
get("/students/:id/courses/:id", (request, response) -> {}, new VelocityTemplateEngine()); //page where students can view a course they are enrolled in and links to lessons
get("/students/:id/courses/:id/lessons/:id", (request, response) -> {}, new VelocityTemplateEngine()); //page where students can view lessons and links to submit assignments
get("/students/:id/courses/:id/lessons/:id/assignments/:id", (request, response) -> {}, new VelocityTemplateEngine()); // page to edit or submit an assignment

get("/teachers", (request, response) -> {}, new VelocityTemplateEngine()); //main teacher page - signup/apply/signin
get("/teachers/:id", (request, response) -> {}, new VelocityTemplateEngine()); //individual teacher page, shows courses teaching and allows creating new courses
get("/teachers/:id/courses/:id", (request, response) -> {}, new VelocityTemplateEngine()); //allows teacher to view/edit course, add lessons
get("/teachers/:id/courses/:id/lessons/:id", (request, response) -> {}, new VelocityTemplateEngine()); //allows teacher to view/edit lessons and add or access assignments for grading
get("/students/:id/courses/:id/lessons/:id/assignments/:id", (request, response) -> {}, new VelocityTemplateEngine()); // allows teachers to create assignments, lists student submissions, allows grading if in student submission

get("/courses", (request, response) -> {}, new VelocityTemplateEngine()); //main course page - view/search all courses
get("/courses/:id", (request, response) -> {}, new VelocityTemplateEngine()); //individual course page shows course details
