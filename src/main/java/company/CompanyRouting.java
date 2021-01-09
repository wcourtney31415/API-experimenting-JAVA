package company;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

interface LambdaInterface {
	String generateBody(MongoCollection<Document> collection);
}

public class CompanyRouting {
	static String dbName = "Time";
	static String collectionName = "Company";
	static String routeStr = "/company";

	public static void initialize() {
		MongoCompany dbCompany = new MongoCompany();

		get(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			return dbCompany.get(idString);
		});

		get("/companies", (req, res) -> {
			return dbCompany.get();
		});

		put(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			Company company = new Company();
			company.name = "was posted";
			dbCompany.update(idString, company);
			return "Completed put";
		});

		post(routeStr, (req, res) -> {
			Company company = new Company();
			company.name = "newCompany";
			dbCompany.add(company);
			return "Completed post";
		});

		delete(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			dbCompany.remove(idString);
			return "Completed put";
		});

	}
}
