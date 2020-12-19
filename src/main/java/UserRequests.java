import static spark.Spark.get;
import static spark.Spark.post;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class UserRequests {
	static String dbName = "Time";
	static String collectionName = "User";

	static void doStuff(Consumer<MongoCollection<Document>> con) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		con.accept(collection);
		mongoClient.close();
	}

	public static void initialize() {

		get("/user", (req, res) -> {
			String idString = req.params("id");
			Object firstName = null;
			doStuff((collection) -> {
				Bson filter = Filters.eq("lastName", "Platipus");
				FindIterable<Document> result = collection.find(filter);
				Document document = result.first();
				firstName = document.get("firstName");
			});
			res.status(200);
			return String.valueOf(firstName);
		});

		post("/user", (req, res) -> {
			Gson gson = new Gson();
			String dbName = "Time";
			String collectionName = "User";
			MongoClient mongoClient = new MongoClient();
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> collection = database.getCollection(collectionName);
			String body = req.body();
			RequestTemplate reqTemp = gson.fromJson(body, RequestTemplate.class);
			User user = reqTemp.content;
			String firstName = user.firstName,
					lastName = user.lastName,
					username = reqTemp.userName,
					password = reqTemp.password;
			boolean authentic = true;// isAuthentic(username, password);
			if (authentic) {
				Document person = new Document()
						.append("firstName", firstName)
						.append("lastName", lastName);
				collection.insertOne(person);
				res.status(200);
			} else {
				res.status(500);
			}
			mongoClient.close();
			return res;
		});
	}
}
