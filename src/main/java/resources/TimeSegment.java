package resources;

import org.bson.types.ObjectId;

public class TimeSegment {
	public ObjectId _id;
	public String author;
	public int startTime, endTime;

	@Override
	public String toString() {
		return "<From " + startTime + " to " + endTime + ">";
	}

}
