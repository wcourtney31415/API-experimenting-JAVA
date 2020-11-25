import static spark.Spark.get;
import static spark.Spark.port;

import java.util.Date;

import com.google.gson.Gson;

public class App {

	public static void main(String[] args) {

		port(80);

		get("/", (req, res) -> "Welcome home.");

		get("/time-segments", (req, res) -> {

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

		get("/something", (req, res) -> {
			String username = req.headers("username");
			String password = req.headers("password");
			boolean authentic = Methods.isAuthentic(username, password);
			if (authentic) {
				String body = req.body();
				Gson gson = new Gson();
				TimeSegment timeSegment = gson.fromJson(body, TimeSegment.class);
				Date startTime = Methods.epochToDate(timeSegment.startTime);
				Date endTime = Methods.epochToDate(timeSegment.endTime);
				long diff = Methods.deltaInSeconds(startTime, endTime);
				String strDiff = Methods.secondsToTimeString(diff);
				System.out.println(strDiff);
				return strDiff;
			} else {
				res.redirect("http://www.google.com");
				return res;
			}

		});

	}

}
