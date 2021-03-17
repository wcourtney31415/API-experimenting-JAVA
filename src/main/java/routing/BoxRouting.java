package routing;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import mongoDb.MongoBox;
import resources.Box;

public class BoxRouting {

	static String routeStr = "/box";

	public static void initialize() {
		MongoBox dbCompany = new MongoBox();

		get(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			return dbCompany.get(idString);
		});

		get(routeStr, (req, res) -> {
			return dbCompany.get();
		});

		put(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			Box company = new Box();
			company.boxName = "was posted";
			dbCompany.update(idString, company);
			return "Completed put";
		});

		post(routeStr, (req, res) -> {
			Box company = new Box();
			company.boxName = "newCompany";
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
