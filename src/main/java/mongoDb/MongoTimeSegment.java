package mongoDb;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;

import resources.TimeSegment;

public class MongoTimeSegment extends MongoResource<TimeSegment> {

	MongoCollection<TimeSegment> timeSegmentCollection = getCollection("timeSegment", TimeSegment.class);

	@Override
	public TimeSegment get(String idString) {
		return timeSegmentCollection
				.find()
				.filter(withId(idString))
				.first();
	}

	@Override
	public List<TimeSegment> get() {
		return timeSegmentCollection
				.find()
				.into(new ArrayList<TimeSegment>());
	}

	@Override
	public void add(TimeSegment timeSegment) {
		timeSegmentCollection.insertOne(timeSegment);
	}

	@Override
	public void update(String idString, TimeSegment timeSegment) {
		timeSegmentCollection.replaceOne(withId(idString), timeSegment);
	}

	@Override
	public void remove(String idString) {
		timeSegmentCollection.deleteOne(withId(idString));
	}

}
