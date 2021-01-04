import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBUser implements DBInterface<Userr> {
	MongoClient mongoClient;
	public DBUser() {
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
				.codecRegistry(pojoCodecRegistry)
				.build();
		mongoClient = MongoClients.create(settings);
	}

	@Override
	public Userr get(String id) {
		return mongoClient
				.getDatabase("Time")
				.getCollection("User", Userr.class)
				.find()
				.filter(eq(new ObjectId(id)))
				.first();
	}

	@Override
	public List<Userr> get() {
		ArrayList<Userr> users = new ArrayList<Userr>();
		try {
			MongoDatabase database = mongoClient.getDatabase("Time");
			MongoCollection<Userr> collection = database.getCollection("User", Userr.class);
			users = collection.find().into(new ArrayList<Userr>());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void add(Userr user) {
		MongoDatabase database = mongoClient.getDatabase("Time");
		MongoCollection<Userr> collection = database.getCollection("User", Userr.class);
		collection.insertOne(user);
	}

	@Override
	public void update(String id, Userr user) {
		MongoDatabase database = mongoClient.getDatabase("Time");
		MongoCollection<Userr> collection = database.getCollection("User", Userr.class);
		collection.replaceOne(eq(new ObjectId(id)), user);
	}

	@Override
	public void remove(String id) {
		MongoDatabase database = mongoClient.getDatabase("Time");
		MongoCollection<Userr> collection = database.getCollection("User", Userr.class);
		collection.deleteOne(eq(new ObjectId(id)));
	}

}
