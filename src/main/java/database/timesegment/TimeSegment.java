package database.timesegment;
import org.bson.types.ObjectId;

public class TimeSegment {
	public ObjectId _id;
	public String user;
	public String startTime;
	public String endTime;
	
	@Override
	public String toString() {
		return startTime + " " + endTime;
	}
	
}
