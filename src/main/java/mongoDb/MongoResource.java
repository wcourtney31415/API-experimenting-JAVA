package mongoDb;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

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

import dbAdapter.DBAdapter;

public abstract class MongoResource<T> implements DBAdapter<T> {

	public MongoCollection<T> getCollection(String collectionName, Class<T> myClass ) {

		MongoClient mongoClient;
		MongoDatabase database;
		MongoCollection<T> collection;
		
		
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
		database = mongoClient.getDatabase("time-logger");
		collection = database.getCollection(collectionName, myClass);
		return collection;

	}
	
	public Bson withId(String idString) {
		return eq(new ObjectId(idString));
	}

}
