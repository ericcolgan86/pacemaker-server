package controllers;

import io.javalin.Context;
import models.Activity;
import models.Location;
import models.User;

import static models.Fixtures.users;

public class PacemakerRestService {

	PacemakerAPI pacemaker = new PacemakerAPI();

	PacemakerRestService() {
		users.forEach(user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
	}

	public void listUsers(Context ctx) {
		ctx.json(pacemaker.getUsers());
		System.out.println("list users requested");
	}

	public void createUser(Context ctx) {
		User user = ctx.bodyAsClass(User.class);
		User newUser = pacemaker.createUser(user.firstname, user.lastname, user.email, user.password);
		ctx.json(newUser);
		System.out.println("createUser requested");
	}

	public void listUser(Context ctx) {
		String id = ctx.pathParam("id");
		ctx.json(pacemaker.getUser(id));
		System.out.println("listUser requested");
	}

	public void getActivities(Context ctx) {
		String id = ctx.pathParam("id");
		User user = pacemaker.getUser(id);
		if (user != null) {
			ctx.json(user.activities.values());
			System.out.println("getActivities requested");
		} else {
			ctx.status(404);
		}
	}

	public void createActivity(Context ctx) {
		String id = ctx.pathParam("id");
		User user = pacemaker.getUser(id);
		if (user != null) {
			Activity activity = ctx.bodyAsClass(Activity.class);
			Activity newActivity = pacemaker.createActivity(id, activity.type, activity.location, activity.distance);
			ctx.json(newActivity);
			System.out.println("createActivity requested");
		} else {
			ctx.status(404);
		}
	}

	public void getActivity(Context ctx) {
		String id = ctx.pathParam("activityid");
		Activity activity = pacemaker.getActivity(id);
		if (activity != null) {
			ctx.json(activity);
			System.out.println("getActivity requested");
		} else {
			ctx.status(404);
		}
	}

	public void getActivityLocations(Context ctx) {
		String id = ctx.pathParam("activityid");
		Activity activity = pacemaker.getActivity(id);
		if (activity != null) {
			ctx.json(activity.route);
			System.out.println("getActivityLocations requested");
		} else {
			ctx.status(404);
		}
	}

	public void addLocation(Context ctx) {
		String id = ctx.pathParam("activityid");
		Activity activity = pacemaker.getActivity(id);
		if (activity != null) {
			Location location = ctx.bodyAsClass(Location.class);
			activity.route.add(location);
			ctx.json(location);
			System.out.println("addLocation requested");
		} else {
			ctx.status(404);
		}
	}

	public void deleteUser(Context ctx) {
		String id = ctx.pathParam("id");
		ctx.json(pacemaker.deleteUser(id));
	}

	public void deleteUsers(Context ctx) {
		pacemaker.deleteUsers();
		ctx.json(204);
	}
	
	public void deleteActivities(Context ctx) {
	    String id = ctx.pathParam("id");
	    pacemaker.deleteActivities(id);
	    ctx.json(204);
	}
}