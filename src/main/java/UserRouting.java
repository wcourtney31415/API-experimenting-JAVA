import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

interface LambdaInterface {
	String generateBody(MongoCollection<Document> collection);
}

public class UserRouting {
	static String dbName = "Time";
	static String collectionName = "User";
	static String routeStr = "/user";

	static String generateBody(LambdaInterface lambdaInterface) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		String body = lambdaInterface.generateBody(collection);
		mongoClient.close();
		return body;
	}

	public static void initialize() {
		DBUser dbuser = new DBUser();

		get(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			Userr user = dbuser.get(idString);
			return user;
		});
		
		get("/users", (req, res) -> {
			List<Userr> users = dbuser.get();
			return users;
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
