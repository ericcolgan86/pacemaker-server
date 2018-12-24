package controllers;

import io.javalin.Javalin;

public class RestMain {

  public static void main(String[] args) throws Exception {
    Javalin app = Javalin.create().start(7000);
    PacemakerRestService service = new PacemakerRestService();
    configRoutes(app, service);
  }
  
  static void configRoutes(Javalin app, PacemakerRestService service) {

    app.get("/users", ctx -> {
      service.listUsers(ctx);
    });
    
    app.post("/users", ctx -> {
      service.createUser(ctx);
    });
    
    app.get("/users/:id", ctx -> {
      service.listUser(ctx);
    });
    
    app.get("/users/:id/activities", ctx -> {
      service.getActivities(ctx);
    });

    app.post("/users/:id/activities", ctx -> {
      service.createActivity(ctx);
    });
    
    app.get("/users/:id/activities/:activityid", ctx -> {
      service.getActivity(ctx);
    });

    app.get("/users/:id/activities/:activityid/locations", ctx -> {
      service.getActivityLocations(ctx);
    });
    
    app.post("/users/:id/activities/:activityid/locations", ctx -> {
      service.addLocation(ctx);
    });
    
    app.delete("/users", ctx -> {
        service.deleteUsers(ctx);
      });

    app.delete("/users/:id", ctx -> {
        service.deleteUser(ctx);
      });
    
    app.delete("/users/:id/activities", ctx -> {
        service.deleteActivities(ctx);
      });
  }
}
