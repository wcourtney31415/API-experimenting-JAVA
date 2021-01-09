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

import dbAdapter.CompanyAdapter;
import resources.Company;

public class MongoCompany implements CompanyAdapter {

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Company> companyCollection;

	public MongoCompany() {

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
		companyCollection = database.getCollection("Company", Company.class);

	}

	private Bson withId(String idString) {
		return eq(new ObjectId(idString));
	}

	@Override
	public Company get(String idString) {
		return companyCollection
				.find()
				.filter(withId(idString))
				.first();
	}

	@Override
	public List<Company> get() {
		return companyCollection
				.find()
				.into(new ArrayList<Company>());
	}

	@Override
	public void add(Company comapny) {
		companyCollection.insertOne(comapny);
	}

	@Override
	public void update(String idString, Company company) {
		companyCollection.replaceOne(withId(idString), company);
	}

	@Override
	public void remove(String idString) {
		companyCollection.deleteOne(withId(idString));
	}

}
