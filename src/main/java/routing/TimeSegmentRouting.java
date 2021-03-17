package routing;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import mongoDb.MongoTimeSegment;
import resources.TimeSegment;

public class TimeSegmentRouting {
	static String routeStr = "/timeSegment";

	public static void initialize() {
		MongoTimeSegment list = new MongoTimeSegment();

		get(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			return list.get(idString);
		});

		get(routeStr, (req, res) -> {
			return list.get();
		});

		put(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			TimeSegment timeSegment = new TimeSegment();
			// set field properties here
			list.update(idString, timeSegment);
			return "Completed put";
		});

		post(routeStr, (req, res) -> {
			TimeSegment timeSegment = new TimeSegment();
			// set field properties
			list.add(timeSegment);
			return "Completed post";
		});

		delete(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			list.remove(idString);
			return "Completed put";
		});

	}
}
