import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.sun.net.httpserver.Filter;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

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

		UserRequests.initialize();

	}

	static boolean isAuthentic(String username, String password) {
		String actualUsername = "john@gmail.com";
		String actualPassword = "passwordy";
		boolean isAuthentic = username.equals(actualUsername) && password.equals(actualPassword);
		return isAuthentic;
	}

	public static void print(String str) {
		System.out.println(str);
	}

}
