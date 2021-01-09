package mongoDb;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dbAdapter.TimeSegmentAdapter;
import resources.TimeSegment;

public class MongoTimeSegment implements TimeSegmentAdapter {

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<TimeSegment> timeSegmentCollection;

	public MongoTimeSegment() {

		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
				getDefaultCodecRegistry(),
				fromProviders(
						PojoCodecProvider
								.builder()
								.automatic(true)
								.build()));

		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
				.codecRegistry(pojoCodecRegistry)
				.build();

		mongoClient = MongoClients.create(settings);
		database = mongoClient.getDatabase("Time");
		timeSegmentCollection = database.getCollection("TimeSegment", TimeSegment.class);

	}

	private Bson withId(String idString) {
		return eq(new ObjectId(idString));
	}

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
