package routing;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;

import mongoDb.MongoUser;
import resources.Userr;

public class UserRouting {
	static String routeStr = "/user";

	public static void initialize() {
		MongoUser list = new MongoUser();

		get(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			return list.get(idString);
		});

		get(routeStr, (req, res) -> {
			return list.get();
		});

		put(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			Userr user = new Userr();
			user.email = "testuser@gmail.com";
			list.update(idString, user);
			return "Completed put";
		});

		post(routeStr, (req, res) -> {
			String body = req.body();
			Gson gson = new Gson();
			// don't forget filtering
			Userr user;
			String result = "Something went wrong! :(";
			try {
				user = gson.fromJson(body, Userr.class);
				try {
					list.add(user);
					result = "Successfully added new user! :)";
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;
		});

		delete(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			list.remove(idString);
			return "Completed put";
		});

	}
}
