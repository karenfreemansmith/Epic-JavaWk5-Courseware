public class Task {

  // public static List<Task> all() {
  //   String sql = "SELECT id, description FROM tasks";
  //   try(Connection con = DB.sql2o.open()) {
  //     return con.createQuery(sql).executeAndFetch(Task.class);
  //   }
  // }

  // @Override
  // public boolean equals(Object otherTask) {
  //   if (!(otherTask instanceof Task)) {
  //     return false;
  //   } else {
  //     Task newTask = (Task) otherTask;
  //     return this.getDescription().equals(newTask.getDescription());
  //   }
  // }
}
