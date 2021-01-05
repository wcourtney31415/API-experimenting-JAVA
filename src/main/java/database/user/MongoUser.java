package database.user;
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

public class MongoUser implements UserAdapter {

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Userr> userCollection;

	public MongoUser() {

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
		userCollection = database.getCollection("User", Userr.class);

	}

	private Bson withId(String idString) {
		return eq(new ObjectId(idString));
	}

	@Override
	public Userr get(String idString) {
		return userCollection
				.find()
				.filter(withId(idString))
				.first();
	}

	@Override
	public List<Userr> get() {
		return userCollection
				.find()
				.into(new ArrayList<Userr>());
	}

	@Override
	public void add(Userr user) {
		userCollection.insertOne(user);
	}

	@Override
	public void update(String idString, Userr user) {
		userCollection.replaceOne(withId(idString), user);
	}

	@Override
	public void remove(String idString) {
		userCollection.deleteOne(withId(idString));
	}

}
