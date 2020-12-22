import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

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
			System.out.println(0);
			String idString = req.params("id");
			System.out.println(1);
			System.out.println(idString);
			System.out.println(2);
			LambdaInterface lambdaFunction = (collection) -> {
				System.out.println(3);
				Bson filter = Filters.eq("_id", new ObjectId("5fd2e3a4fa4be336e4c0001f"));
				System.out.println(4);
				FindIterable<Document> result = collection.find(filter);
				System.out.println(5);
				Document document = result.first();
				System.out.println(6);
				String firstName = document.get("firstName").toString();
				System.out.println(7);
				return firstName;
			};
			String generatedBody = generateBody(lambdaFunction);
			System.out.println(8);
			res.status(200);
			System.out.println(9);
			return generatedBody;
		});

		put("/user", (req, res) -> {
			System.out.println("0");
			String idString = req.params("id");
			LambdaInterface lambdaFunction = (collection) -> {
				Bson filter = Filters.eq("lastName", "Platipus");
				collection.updateOne(filter, new Document("$set", new Document("firstName", "Jerry")));
				return "Updated";
			};
			System.out.println("7");
			String generatedBody = generateBody(lambdaFunction);
			System.out.println("8");
			res.status(200);
			System.out.println("9");
			return generatedBody;
		});

		post("/user", (req, res) -> {
			Gson gson = new Gson();
			String dbName = "Time";
			String collectionName = "User";
			LambdaInterface lambdaFunction = (collection) -> {
				String body = req.body();
				RequestTemplate reqTemp = gson.fromJson(body, RequestTemplate.class);
				User user = reqTemp.content;
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
			};
			String generatedBody = generateBody(lambdaFunction);
			return generatedBody;
		});

		delete("/user", (req, res) -> {
			String idString = req.params("id");
			LambdaInterface lambdaFunction = (collection) -> {
				Bson filter = Filters.eq("lastName", "Stewart");
				collection.deleteOne(filter);
				return "Deleted";
			};
			String generatedBody = generateBody(lambdaFunction);
			res.status(200);
			return generatedBody;
		});

	}
}
