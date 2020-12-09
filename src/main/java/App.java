import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {

	public static void main(String[] args) {

		SampleData.initialize();

		port(80);

		get("/time-segments", (req, res) -> {
			System.out.println("Get: time-segments");
			String username = req.headers("username");
			String password = req.headers("password");
			boolean authentic = Methods.isAuthentic(username, password);
			if (authentic) {
				Gson gson = new Gson();
				String json = gson.toJson(SampleData.getCalcSegments());
				return json;
			} else {
				res.redirect("http://www.google.com");
				return res;
			}
		});

		get("/time-segment", (req, res) -> {
			System.out.println("Get: time-segment");
			String username = req.headers("username");
			String password = req.headers("password");
			boolean authentic = Methods.isAuthentic(username, password);
			if (authentic) {
				Gson gson = new Gson();
				GetIndexed indexed = gson.fromJson(req.body(), GetIndexed.class);
				String json = gson.toJson(SampleData.getCalcSegments().get(indexed.index));
				return json;
			} else {
				res.redirect("http://www.google.com");
				return res;
			}
		});

		post("/time-segment", (req, res) -> {
			System.out.println("Post: time-segment");
			String username = req.headers("username");
			String password = req.headers("password");
			boolean authentic = Methods.isAuthentic(username, password);
			if (authentic) {
				Gson gson = new Gson();
				String body = req.body();
				TimeSegment timeSegment = gson.fromJson(body, TimeSegment.class);
				SampleData.save(timeSegment);
				res.status(200);
				return res;
			} else {
				res.redirect("http://www.google.com");
				return res;
			}
		});

		post("/user", (req, res) -> {
			MongoClient mongoClient = new MongoClient();
			MongoDatabase database = mongoClient.getDatabase("Time");
			MongoCollection<Document> collection = database.getCollection("User");
			String body = req.body();
			Gson gson = new Gson();
			RequestTemplate reqTemp = gson.fromJson(body, RequestTemplate.class);
			String content = reqTemp.content;
			User user = gson.fromJson(content, User.class);
			if (reqTemp.userName.equals("john@gmail.com") && reqTemp.password.equals("passwordy")) {
				Document person = new Document()
						.append("firstName", user.firstName)
						.append("lastName", user.lastName);
				collection.insertOne(person);
				mongoClient.close();
				res.status(200);
			}
			res.status(500);
			return res;
		});

	}

}
