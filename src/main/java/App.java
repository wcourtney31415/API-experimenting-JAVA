import static spark.Spark.get;
import static spark.Spark.port;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

public class App {
	
	private static Date epochToDate (Long epoch) {
		Date expiry = new Date(epoch * 1000);
		return expiry;
	}
	
	private static long deltaInSeconds(Date startTime, Date endTime) {
		long diffInMillies = Math.abs(endTime.getTime() - startTime.getTime());
	    long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}
	
	private static String secondsToTimeString(long seconds) {
        long hours = seconds / 60 / 60;
        long minutes = (seconds / 60) % 60;
        String ret =  hours + " hours and " + minutes + " minutes";
        return ret;
	}
	
	private static String actualUsername = "bob";
	private static String actualPassword = "passwordy";
	
	public static void main(String[] args) {
		port(80);
		get("/", (req, res) -> "Welcome home.");

		get("/something", (req, res) -> {
			String body = req.body();
			Gson gson = new Gson();
			TimeSegment timeSegment = gson.fromJson(body, TimeSegment.class);
			Date startTime = epochToDate(timeSegment.startTime);
			Date endTime = epochToDate(timeSegment.endTime);
			long diff = deltaInSeconds(startTime, endTime);
			String strDiff = secondsToTimeString(diff);
			System.out.println(strDiff);
			return strDiff;
		});

	}

}
