package resources;

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

}
