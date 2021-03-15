package routing;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import mongoDb.MongoTimeSegment;
import resources.TimeSegment;

public class TimeSegmentRouting {
	static String dbName = "time-logger";
	static String collectionName = "timeSegment";
	static String routeStr = "/time";

	public static void initialize() {
		
		MongoTimeSegment mTimeSegment = new MongoTimeSegment();
		
		get(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			TimeSegment m = mTimeSegment.get(idString);
			System.out.println("HELLO WORLD: " + m);
			return m;
		});
		
		get(routeStr, (req, res) -> {
			return mTimeSegment.get();
		});
		
		put(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			TimeSegment timeSegment = new TimeSegment();
			//set field properties here
			mTimeSegment.update(idString, timeSegment);
			return "Completed put";
		});
		
		post(routeStr, (req, res) -> {
			TimeSegment timeSegment = new TimeSegment();
			//set field properties
			mTimeSegment.add(timeSegment);
			return "Completed post";
		});
		
		delete(routeStr + "/:id", (req, res) -> {
			String idString = req.params(":id");
			mTimeSegment.remove(idString);
			return "Completed put";
		});
		
	}
}
