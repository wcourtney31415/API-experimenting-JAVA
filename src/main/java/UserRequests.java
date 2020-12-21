import static spark.Spark.get;
import static spark.Spark.post;

import org.bson.Document;
import org.bson.conversions.Bson;

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

	static String generateBody(LambdaInterface lambdaInterface) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		String body = lambdaInterface.generateBody(collection);
		mongoClient.close();
		return body;
	}

	public static void initialize() {

		get("/user", (req, res) -> {
			String idString = req.params("id");
			LambdaInterface lambdaFunction = (collection) -> {
				Bson filter = Filters.eq("lastName", "Platipus");
				FindIterable<Document> result = collection.find(filter);
				Document document = result.first();
				String firstName = document.get("firstName").toString();
				return firstName;
			};
			String generatedBody = generateBody(lambdaFunction);
			res.status(200);
			return generatedBody;
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
