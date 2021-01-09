package routing;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import org.bson.Document;

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
			user.firstName = "this";
			user.lastName = "was posted";
			dbuser.update(idString, user);
			return "Completed put";
		});

		post(routeStr, (req, res) -> {
			Userr user = new Userr();
			user.firstName = "newUser";
			user.lastName = "newuserrrrrr";
			dbuser.add(user);
			return "Completed post";
		});

		delete(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			dbuser.remove(idString);
			return "Completed put";
		});

	}
}
