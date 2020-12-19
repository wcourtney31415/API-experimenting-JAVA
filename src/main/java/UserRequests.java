import static spark.Spark.get;
import static spark.Spark.post;

import java.util.Collection;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class UserRequests {

	public static void initialize() {

		get("/user", (req, res) -> {
			String idString = req.params("id");
			String dbName = "Time";
			String collectionName = "User";
			MongoClient mongoClient = new MongoClient();
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> collection = database.getCollection(collectionName);
			Bson filter = Filters.eq("lastName", "Platipus");
			FindIterable<Document> result = collection.find(filter);
			Document document = result.first();
			Object firstName = document.get("firstName");
			mongoClient.close();
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
