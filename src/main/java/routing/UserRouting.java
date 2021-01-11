package routing;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;

import mongoDb.MongoUser;
import resources.Userr;

interface LambdaInterface {
	String generateBody(MongoCollection<Document> collection);
}

public class UserRouting {
	static String dbName = "Time";
	static String collectionName = "User";
	static String routeStr = "/user";

	public static void initialize() {
		MongoUser dbuser = new MongoUser();

		get(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			return dbuser.get(idString);
		});

		get("/users", (req, res) -> {
			return dbuser.get();
		});

		put(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			Userr user = new Userr();
			user.email = "testuser@gmail.com";
			dbuser.update(idString, user);
			return "Completed put";
		});

		post(routeStr, (req, res) -> {
			String body = req.body();
			Gson gson = new Gson();
			// don't forget filtering
			Userr user = null;
			try {
				user = gson.fromJson(body, Userr.class);
				System.out.println("Here: ");
				for (String str : user.timeSegments) {
					System.out.println(str);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				dbuser.add(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Added it.");
			return "Completed post";
		});

		delete(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			dbuser.remove(idString);
			return "Completed put";
		});

	}
}
