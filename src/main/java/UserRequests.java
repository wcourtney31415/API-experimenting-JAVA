import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

interface LambdaInterface {
	String generateBody(MongoCollection<Document> collection);
}

public class UserRequests {
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
			String generatedBody = generateBody((collection) -> {
				Bson filter = Filters.eq("_id", new ObjectId(idString));
				collection.updateOne(filter, new Document("$set", new Document("firstName", "Jerry")));
				return "Updated";
			});
			res.status(200);
			return generatedBody;
		});

		post(routeStr, (req, res) -> {
			Gson gson = new Gson();
			String generatedBody = generateBody((collection) -> {
				String body = req.body();
				RequestTemplate reqTemp = gson.fromJson(body, RequestTemplate.class);
				Userr user = reqTemp.content;
				String firstName = user.firstName,
						lastName = user.lastName,
						username = reqTemp.userName,
						password = reqTemp.password;
				boolean authentic = true;// isAuthentic(username, password);
				String resBody;
				Document person = new Document()
						.append("firstName", firstName)
						.append("lastName", lastName);
				if (authentic) {
					collection.insertOne(person);
					res.status(200);
					resBody = "Successfully added " + person.toJson();
				} else {
					res.status(500);
					resBody = "Failed to add " + person.toJson();
				}
				return resBody;
			});
			return generatedBody;
		});

		delete(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			String generatedBody = generateBody((collection) -> {
				Bson filter = Filters.eq("_id", new ObjectId(idString));
				collection.deleteOne(filter);
				return "Deleted";
			});
			res.status(200);
			return generatedBody;
		});

	}
}
