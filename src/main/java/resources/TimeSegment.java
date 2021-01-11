package resources;

import org.bson.types.ObjectId;

public class TimeSegment {
	public ObjectId _id;
	public String author,
			company,
			startTime,
			endTime;

	@Override
	public String toString() {
		return startTime + " " + endTime;
	}

}
