package resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.bson.types.ObjectId;

import com.google.gson.Gson;

public class TimeSegment {
	public ObjectId _id;
	public String author;
	public int startTime, endTime;

	@Override
	public String toString() {
		Gson gson = new Gson();
		
		return gson.toJson(this);
	}
	
	public String toPrettyString() {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		Date startDateTime = new Date(startTime);
        String formattedStartTime = format.format(startDateTime);
        Date endDateTime = new Date(endTime);
        String formattedEndTime = format.format(endDateTime);
		return formattedStartTime + " - " + formattedEndTime;
	}

}
