package mongoDb;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;

import resources.TimeSegment;

public class MongoTimeSegment extends MongoResource<TimeSegment> {

	MongoCollection<TimeSegment> timeSegmentCollection = getCollection("TimeSegment", TimeSegment.class);

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
	public void add(TimeSegment obj) {
		timeSegmentCollection.insertOne(obj);
	}

	@Override
	public void update(String idString, TimeSegment obj) {
		timeSegmentCollection.replaceOne(withId(idString), obj);
	}

	@Override
	public void remove(String idString) {
		timeSegmentCollection.deleteOne(withId(idString));
	}

}
